package edu.wright.crowningkings.android;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import java.util.Date;

import edu.wright.crowningkings.base.BaseClient;
import edu.wright.crowningkings.base.UserInterface.AbstractUserInterface;

/**
 * Created by csmith on 11/24/16.
 */

public class AndroidUIService extends Service implements AbstractUserInterface {
    private static final String TAG = "AndroidUIService";
    private BaseClient client = null;
    private String username = null;

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
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            client.messageAll(intent.getStringExtra(Constants.MESSAGE_EXTRA));
                        }
                    }).start();
                    break;
                case Constants.MESSAGE_CLIENT_INTENT:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            client.messageClient(
                                    intent.getStringExtra(Constants.USERNAME_EXTRA),
                                    intent.getStringExtra(Constants.MESSAGE_EXTRA));
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
            }
        }
    };

    private IntentFilter androidUIIntentFilter = new IntentFilter();


    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");
        super.onCreate();

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
        registerReceiver(androidUIBroadcastReceiver, androidUIIntentFilter);
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
            sendBroadcast(new Intent(Constants.NEW_MESSAGE_INTENT)
                    .putExtra(Constants.MESSAGE_EXTRA, message)
                    .putExtra(Constants.USERNAME_EXTRA, from)
                    .putExtra(Constants.PRIVATE_MESSAGE_EXTRA, privateMessage)
                    .putExtra(Constants.DATE_EXTRA, (new Date()).getTime()));

            postNotification(from, message, from.hashCode(), privateMessage);
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
    public void boardState(String[][] boardState) {
        sendBroadcast(new Intent(Constants.OPPONENT_MOVE_INTENT)
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
        sendBroadcast(new Intent(Constants.NOW_OBSERVING_INTENT));
    }

    @Override
    public void stoppedObserving(String tableID) {
        sendBroadcast(new Intent(Constants.STOPPED_OBSERVING_INTENT));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void postNotification(String fromUsername, String message, long cROWID, boolean privateMessage) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(fromUsername)
                        .setContentText(message)
                        .setTicker("New message from " + fromUsername + " - \n" + message)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setDefaults(Notification.DEFAULT_VIBRATE);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MessageActivity.class);
        if (privateMessage) {
            resultIntent.putExtra(Constants.chatHandlesString, fromUsername);
        } else {
            resultIntent.putExtra(Constants.chatHandlesString, R.string.public_message_group_name);
        }

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MessageActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true);   // this removes notification on tap
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        Notification notification = mBuilder.build();
        mNotificationManager.notify(0, notification);
    }


}
