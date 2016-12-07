package edu.wright.crowningkings.android;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import edu.wright.crowningkings.base.BaseClient;
import edu.wright.crowningkings.base.UserInterface.AbstractUserInterface;

/**
 * Created by csmith on 11/24/16.
 */

public class AndroidUIService extends Service implements AbstractUserInterface {
    private static final String TAG = "AndroidUIService";
    private BaseClient client = null;
    private String username = null;
    //    private IBinder mBinder = new AndroidUIBinder();
    private HashMap<String, ArrayList<Message>> allMessages = null;
    private boolean uiIsReady = true;
    private ArrayList<Intent> messageQueue = new ArrayList<>();


    private IntentFilter androidUIIntentFilter = new IntentFilter();
    private BroadcastReceiver androidUIBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            switch (intent.getAction()) {
                case Constants.SEND_USERNAME_REPLY_INTENT:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            username = intent.getStringExtra(Constants.USERNAME_EXTRA);
                            client.setUsername(username);
                        }
                    }).start();
                    break;
                case Constants.MESSAGE_ALL_INTENT:
                    final String message = intent.getStringExtra(Constants.MESSAGE_EXTRA);
                    ArrayList<Message> msgs = getMessages(getResources().getString(R.string.public_message_group_name));
                    msgs.add(new Message(message, MessageType.SENT, MessageStatus.SUCCESSFUL, username));
                    setMessages(getResources().getString(R.string.public_message_group_name), msgs);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            client.messageAll(message);
                        }
                    }).start();
                    break;
                case Constants.MESSAGE_CLIENT_INTENT:
                    final String from = intent.getStringExtra(Constants.USERNAME_EXTRA);
                    final String privateMessage = intent.getStringExtra(Constants.MESSAGE_EXTRA);

                    ArrayList<Message> pmsgs = getMessages(from);
                    pmsgs.add(new Message(privateMessage, MessageType.SENT, MessageStatus.SUCCESSFUL, from));
                    setMessages(from, pmsgs);


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            client.messageClient(from, privateMessage);
                        }
                    }).start();
                    break;
                case Constants.MAKE_TABLE_INTNENT:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            client.makeTable();
                        }
                    }).start();
                    break;
                case Constants.JOIN_TABLE_INTNENT:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            client.joinTable(intent.getStringExtra(Constants.TABLE_ID_EXTRA));
                        }
                    }).start();
                    break;
                case Constants.READY_INTENT:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            client.ready();
                        }
                    }).start();
                    break;
                case Constants.MOVE_INTENT:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            client.move(
                                    intent.getStringExtra(Constants.FROM_X_EXTRA),
                                    intent.getStringExtra(Constants.FROM_Y_EXTRA),
                                    intent.getStringExtra(Constants.TO_X_EXTRA),
                                    intent.getStringExtra(Constants.TO_Y_EXTRA));
                        }
                    }).start();
                    break;
                case Constants.LEAVE_TABLE_INTNENT:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            client.leaveTable();
                        }
                    }).start();
                    break;
                case Constants.QUIT_INTENT:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            client.quit();
                            stopSelf();
                        }
                    }).start();
                    break;
                case Constants.ASK_TABLE_STATUS_INTENT:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            client.askTableStatus(intent.getStringExtra(Constants.TABLE_ID_EXTRA));
                        }
                    }).start();
                    break;
                case Constants.OBSERVE_TABLE_INTENT:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            client.observeTable(intent.getStringExtra(Constants.TABLE_ID_EXTRA));
                        }
                    }).start();
                    break;
                case Constants.REQUEST_CONVERSATION_MESSAGES_INTENT:
                    String fromtarget = intent.getStringExtra(Constants.USERNAME_EXTRA);
                    sendBroadcast(new Intent(Constants.CONVERSATION_MESSAGES_INTENT)
                            .putExtra(Constants.USERNAME_EXTRA, fromtarget)
                            .putExtra(Constants.MESSAGES_ARRAY_EXTRA, getMessages(fromtarget)));
                    break;
                case Constants.READY_FOR_SERVER_MSGS_INTENT:
                    Log.d(TAG, "READY_FOR_SERVER_MSGS_INTENT");
                    uiIsReady = true;
                    for (Intent i : messageQueue) {
                        sendBroadcast(i);
                    }
            }
        }
    };

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");
        super.onCreate();
        allMessages = new HashMap<>();
        new AsyncTask<AbstractUserInterface, Void, Void>() {
            protected Void doInBackground(AbstractUserInterface... voids) {
                client = new BaseClient(voids[0]);
                return null;
            }
        }.execute(this);

        androidUIIntentFilter.addAction(Constants.SEND_USERNAME_REPLY_INTENT);
        androidUIIntentFilter.addAction(Constants.MESSAGE_ALL_INTENT);
        androidUIIntentFilter.addAction(Constants.MESSAGE_CLIENT_INTENT);
        androidUIIntentFilter.addAction(Constants.MAKE_TABLE_INTNENT);
        androidUIIntentFilter.addAction(Constants.JOIN_TABLE_INTNENT);
        androidUIIntentFilter.addAction(Constants.READY_INTENT);
        androidUIIntentFilter.addAction(Constants.MOVE_INTENT);
        androidUIIntentFilter.addAction(Constants.LEAVE_TABLE_INTNENT);
        androidUIIntentFilter.addAction(Constants.QUIT_INTENT);
        androidUIIntentFilter.addAction(Constants.ASK_TABLE_STATUS_INTENT);
        androidUIIntentFilter.addAction(Constants.OBSERVE_TABLE_INTENT);
        androidUIIntentFilter.addAction(Constants.REQUEST_CONVERSATION_MESSAGES_INTENT);
        androidUIIntentFilter.addAction(Constants.READY_FOR_SERVER_MSGS_INTENT);
        registerReceiver(androidUIBroadcastReceiver, androidUIIntentFilter);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
        unregisterReceiver(androidUIBroadcastReceiver);
        new Thread(new Runnable() {
            @Override
            public void run() {
                client.quit();
            }
        }).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void sendBroadcast(Intent i) {
        Log.d(TAG, "sendBroadcast");
        String action = i.getAction();
        if (uiIsReady && (action.equals(Constants.TABLE_JOINED_INTENT)
                        || action.equals(Constants.IN_LOBBY_INTENT)
                        || action.equals(Constants.NOW_OBSERVING_INTENT))) {
            Log.d(TAG, "if");
            uiIsReady = false;

            super.sendBroadcast(i);
        } else if (uiIsReady) {
            Log.d(TAG, "else if");
            super.sendBroadcast(i);
        } else {
            Log.d(TAG, "else");
            messageQueue.add(i);
        }
    }

    @Override
    public void sendUsernameRequest() {
        Log.d(TAG, "sendUsernameRequest()");
        sendBroadcast(new Intent(Constants.USERNAME_REQUEST_INTENT));
    }

    @Override
    public void connectionOK() {
        sendBroadcast(new Intent(Constants.CONNECTION_OK_INTENT));
    }

    @Override
    public void message(String message, String from, boolean privateMessage) {
        if (!from.equals(username)) {
            System.out.println("good...");
            ArrayList<Message> msgs;
            if (privateMessage) {
                msgs = getMessages(from);
            } else {
                msgs = getMessages(getResources().getString(R.string.public_message_group_name));
            }
            msgs.add(new Message(message, MessageType.RECEIVED, MessageStatus.UNSUCCESSFUL, from));
            if (privateMessage) {
                setMessages(from, msgs);
            } else {
                setMessages(getResources().getString(R.string.public_message_group_name), msgs);
            }
            System.out.println("sending broadcast from auiservice");
            sendBroadcast(new Intent(Constants.NEW_MESSAGE_INTENT)
                    .putExtra(Constants.MESSAGE_EXTRA, message)
                    .putExtra(Constants.USERNAME_EXTRA, from)
                    .putExtra(Constants.PRIVATE_MESSAGE_EXTRA, privateMessage)
                    .putExtra(Constants.DATE_EXTRA, (new Date()).getTime())
                    .putExtra(Constants.MESSAGES_ARRAY_EXTRA, msgs));
        }
    }

    @Override
    public void newtable(String tableID) {
        sendBroadcast(new Intent(Constants.NEW_TABLE_INTENT)
                .putExtra(Constants.TABLE_ID_ARRAY_EXTRA, new String[]{tableID}));
    }

    @Override
    public void gameStart() {
        sendBroadcast(new Intent(Constants.GAME_START_INTENT));
    }

    @Override
    public void colorBlack() {
        sendBroadcast(new Intent(Constants.COLOR_BLACK_INTENT));
    }

    @Override
    public void colorRed() {
        sendBroadcast(new Intent(Constants.COLOR_RED_INTENT));
    }

    @Override
    public void opponentMove(String[] from, String[] to) {
        sendBroadcast(new Intent(Constants.OPPONENT_MOVE_INTENT)
                .putExtra(Constants.FROM_LOCATION_EXTRA, from)
                .putExtra(Constants.TO_LOCATION_EXTRA, to));
    }

    @Override
    public void boardState(String boardState) {
//        String boardString = "";
//        for (String[] row : boardState) {
//            for (String item : row) {
//                boardString = boardString + " " + item;
//            }
//        }
//
        System.out.println("boardState=" + boardState);
        sendBroadcast(new Intent(Constants.BOARD_STATE_INTENT)
                .putExtra(Constants.BOARD_STATE_EXTRA, boardState));
    }

    @Override
    public void gameWon() {
        sendBroadcast(new Intent(Constants.GAME_WIN_INTENT));
    }

    @Override
    public void gameLose() {
        sendBroadcast(new Intent(Constants.GAME_LOSE_INTENT));
    }

    @Override
    public void tableJoined(String tableID) {
        sendBroadcast(new Intent(Constants.TABLE_JOINED_INTENT)
                .putExtra(Constants.TABLE_ID_EXTRA, tableID));
    }

    @Override
    public void whoInLobby(String[] users) {
        sendBroadcast(new Intent(Constants.WHO_IN_LOBBY_INTENT)
                .putExtra(Constants.USERS_ARRAY_EXTRA, users));
    }

    @Override
    public void outLobby() {
        sendBroadcast(new Intent(Constants.OUT_LOBBY_INTENT));
    }

    @Override
    public void nowInLobby(String user) {
        sendBroadcast(new Intent(Constants.NOW_IN_LOBBY_INTENT)
                .putExtra(Constants.USERNAME_EXTRA, user));
    }

    @Override
    public void tableList(String[] tableIDs) {
        sendBroadcast(new Intent(Constants.TABLE_LIST_INTENT)
                .putExtra(Constants.TABLE_ID_ARRAY_EXTRA, tableIDs));
    }

    @Override
    public void nowLeftLobby(String user) {
        sendBroadcast(new Intent(Constants.NOW_LEFT_LOBBY_INTENT));
    }

    @Override
    public void inLobby() {
        sendBroadcast(new Intent(Constants.IN_LOBBY_INTENT));
    }

    @Override
    public void whoOnTable(String userOne, String userTwo, String tableID, String userOneColor, String userTwoColor) {
        sendBroadcast(new Intent(Constants.WHO_ON_TABLE)
                .putExtra(Constants.USER_ONE_EXTRA, userOne)
                .putExtra(Constants.USER_TWO_EXTRA, userTwo)
                .putExtra(Constants.TABLE_ID_EXTRA, tableID));
    }

    @Override
    public void opponentLeftTable() {
        sendBroadcast(new Intent(Constants.OPPONENT_LEFT_TABLE_INTENT));
    }

    @Override
    public void yourTurn() {
        sendBroadcast(new Intent(Constants.YOUR_TURN_INTENT));
    }

    @Override
    public void tableLeft(String tableID) {
        sendBroadcast(new Intent(Constants.TABLE_LEFT_INTENT));
    }

    @Override
    public void nowObserving(String tableID) {
        sendBroadcast(new Intent(Constants.NOW_OBSERVING_INTENT)
                .putExtra(Constants.TABLE_ID_EXTRA, tableID));
    }

    @Override
    public void stoppedObserving(String tableID) {
        sendBroadcast(new Intent(Constants.STOPPED_OBSERVING_INTENT));
    }

    public ArrayList<Message> getMessages(String username) {
        ArrayList<Message> ret = allMessages.get(username);
        if (ret == null) {
            ret = new ArrayList<>();
            allMessages.put(username, ret);
        }
        return ret;
    }

    public void setMessages(String username, ArrayList<Message> messages) {
        allMessages.put(username, messages);
    }

    @Override
    public void netException() {
        sendBroadcast(new Intent(Constants.ERROR_NET_EXCEPTION_INTENT));
    }

    @Override
    public void nameInUse() {
        sendBroadcast(new Intent(Constants.ERROR_NAME_IN_USE_INTENT));
    }

    @Override
    public void illegalMove() {
        sendBroadcast(new Intent(Constants.ERROR_ILLEGAL_MOVE_INTENT));
    }

    @Override
    public void tblFull() {
        sendBroadcast(new Intent(Constants.ERROR_TABLE_FULL_INTENT));
    }

    @Override
    public void notInLobby() {
        sendBroadcast(new Intent(Constants.ERROR_NOT_IN_LOBBY_INTENT));
    }

    @Override
    public void badMessage() {
        sendBroadcast(new Intent(Constants.ERROR_BAD_MESSAGE_INTENT));
    }

    @Override
    public void errorLobby() {
        sendBroadcast(new Intent(Constants.ERROR_IN_LOBBY_INTENT));
    }

    @Override
    public void badName() {
        sendBroadcast(new Intent(Constants.ERROR_BAD_USERNAME_INTENT));
    }

    @Override
    public void playerNotReady() {
        sendBroadcast(new Intent(Constants.ERROR_PLAYERS_NOT_READY_INTENT));
    }

    @Override
    public void notYourTurn() {
        sendBroadcast(new Intent(Constants.ERROR_NOT_YOUR_TURN_INTENT));
    }

    @Override
    public void tableNotExist() {
        sendBroadcast(new Intent(Constants.ERROR_TABLE_NOT_EXIST_INTENT));
    }

    @Override
    public void gameNotCreated() {
        sendBroadcast(new Intent(Constants.ERROR_GAME_NOT_CREATED_INTENT));
    }

    @Override
    public void notObserving() {
        sendBroadcast(new Intent(Constants.ERROR_NOT_OBSERVING_INTENT));
    }

}
