package edu.wright.crowningkings.android;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
/**
 * Created by csmith on 11/17/16. Based off of ericchee's PieMessage
 */
public class MessageActivity extends AppCompatActivity {
    public static final String TAG = MessageActivity.class.getSimpleName();
    TextView tvTarget;
    EditText etTarget;
    EditText etMessage;
    ImageButton ibCheckmark;
    ArrayList<Socket> listOfSockets;
    ListView lvMessages;
    ArrayList<Message> arrayOfMessages;
    MessagesAdapter adapter;
    Button btnSend;
    MessageActivityBroadcastReceiver messageActivityBroadcastReceiver;
    private boolean boundReceiveService = false;
    String targetString;
    String targetUsername;
    boolean isNewChat = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setStatusBarColor();    // set status bar color

        Log.i(TAG, "Creating activity");

//        targetUsername = getIntent().getStringExtra(Constants.chatHandlesString);
        targetUsername = getIntent().getStringExtra(Constants.USERNAME_EXTRA);

        if (getIntent() != null && !targetUsername.equals(getResources().getString(R.string.make_new_message))) {
            isNewChat = false;
        }

        tvTarget = (TextView) findViewById(R.id.tvTarget);
        etTarget = (EditText) findViewById(R.id.etTarget);
        ibCheckmark = (ImageButton) findViewById(R.id.ibCheckmark);
        listOfSockets = new ArrayList<>();

        setTvTargetListener();
        setIbCheckmarkListener();
        initMessagesListAdapter();


        btnSend = (Button) findViewById(R.id.btnSend);
        etMessage = (EditText) findViewById(R.id.etMessage);

        btnSend.setEnabled(true);
        btnSend.setBackgroundResource(R.color.colorAccent);

        setSendOnClickListener();
        setBackButtonListener();
    }

    private void setSendOnClickListener() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "pressed");

                updateTargetValue();
                if (hasSetTargetNumber()) {
                    // if has number, send msg
                    String targetName = tvTarget.getText().toString().trim();
                    String message = etMessage.getText().toString().trim();

                    etMessage.setText(message);

                    if (message.length() > 0) {
                        addSentMessageToListView();
                        if (targetName.equals(getResources().getString(R.string.public_message_group_name))) {
                            sendBroadcast(new Intent(Constants.MESSAGE_ALL_INTENT)
                                    .putExtra(Constants.MESSAGE_EXTRA, message));
                        } else {
                            sendBroadcast(new Intent(Constants.MESSAGE_CLIENT_INTENT)
                                    .putExtra(Constants.MESSAGE_EXTRA, message)
                                    .putExtra(Constants.USERNAME_EXTRA, targetName));
                        }

                        showBackButton();
                    } else {
                        Log.i(TAG, "Message text has no length");
                    }
                } else {
                    Log.d(TAG, "Has not set target value");
                }
            }
        });
    }

    public void addSentMessageToListView() {
        Message messageInProgress = new Message(etMessage.getText().toString(), MessageType.SENT, MessageStatus.IN_PROGRESS, "Me");
        messageInProgress.setDate(-1);  // TODO need to set date
        arrayOfMessages.add(messageInProgress);
        etMessage.setText(null);
        adapter.notifyDataSetChanged();
        lvMessages.smoothScrollToPosition(adapter.getCount() - 1);
    }

    public void addReceivedMessageToListView(String messageText, String handleID, long date) {
        Message receivedMessage = new Message(messageText, MessageType.RECEIVED, MessageStatus.SUCCESSFUL, handleID);
        receivedMessage.setDate(date);
        arrayOfMessages.add(receivedMessage);
        adapter.notifyDataSetChanged();
        lvMessages.smoothScrollToPosition(adapter.getCount() - 1);
    }

    private void enableSendButton() {
        btnSend.setEnabled(true);
    }

    public void messageStatusReceived(JSONObject messageStatusJSON) throws JSONException {
        boolean messageSuccessful = messageStatusJSON.getString("messageStatus").equals("successful");

        // Find most recent sent message in progress & same text
        Message sentMessage = null;
        for (int i = arrayOfMessages.size() - 1; i >= 0; i--) {
            Message curMessage = arrayOfMessages.get(i);
            if (curMessage.messageStatus == MessageStatus.IN_PROGRESS && curMessage.text.equals(messageStatusJSON.getString("message"))) {
                sentMessage = curMessage;
                break;
            }
        }

        if (sentMessage != null) {
            // Found most recent message. Sent its status, date, & cROWID
            sentMessage.messageStatus = messageSuccessful ? MessageStatus.SUCCESSFUL : MessageStatus.UNSUCCESSFUL;

            long date = messageStatusJSON.getLong("date");
            sentMessage.setDate(date);
            adapter.notifyDataSetChanged();
        } else {
            Log.e(TAG, "Could not find sent message that matched sent response JSON");
        }
    }

    private void setTvTargetListener() {
        if (!isNewChat) {
            // If previous chat, Set the Handle ID
//            targetString = getIntent().getStringExtra(Constants.chatHandlesString);
            targetString = getIntent().getStringExtra(Constants.USERNAME_EXTRA);
            tvTarget.setText(targetString);
            etTarget.setText(targetString);

            // Show back button because is previous chat
            showBackButton();
        } else {
            // If new chat, listen for if tap on Target textview
            tvTarget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvTarget.setVisibility(View.INVISIBLE);
                    etTarget.setVisibility(View.VISIBLE);
                    ibCheckmark.setVisibility(View.VISIBLE);
                    etTarget.requestFocus();
                }
            });
        }
    }

    private boolean hasSetTargetNumber(){
        return ! tvTarget.getText().toString().equals(getString(R.string.insert_number));
    }

    private void setIbCheckmarkListener() {
        if (isNewChat) {
            ibCheckmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateTargetValue();
                }
            });
        }
    }

    private void updateTargetValue() {
        String valueOfTarget = etTarget.getText().toString().trim();
        if (valueOfTarget.length() > 0) {
            tvTarget.setText(valueOfTarget);
        } else {
            tvTarget.setText(getString(R.string.insert_number));
            Log.i(TAG, "Target value is invalid");
            Toast.makeText(
                    getApplicationContext(),
                    "Please add valid target",
                    Toast.LENGTH_SHORT)
                    .show();
        }
        etTarget.setText(valueOfTarget);    // set etTarget to trimmed string
        tvTarget.setVisibility(View.VISIBLE);
        etTarget.setVisibility(View.INVISIBLE);
        ibCheckmark.setVisibility(View.INVISIBLE);
    }

    public void addSocket(Socket socket) {
        Log.i(TAG, "Adding socket to list of sockets");
        listOfSockets.add(socket);
    }

    // Construct data and set in custom adapter
    private void initMessagesListAdapter() {
        if (!isNewChat) {
            // grab all messages from chat in sqlite db
//            PieSQLiteOpenHelper dbHelper =  //.getDbHelper();
//            arrayOfMessages = dbHelper.getAllMessagesOfChat(chatROWID);
            arrayOfMessages = new ArrayList<>();
        } else {
            // If new chat, initiate new array of messages
            arrayOfMessages = new ArrayList<>();
        }
        adapter = new MessagesAdapter(this, arrayOfMessages);

        // Attach adapter to listView
        lvMessages = (ListView) findViewById(R.id.lvMessages);
        lvMessages.setAdapter(adapter);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    // TODO Delete this method once MessageResponseTask finishes
    public void disconnectSockets() {
        for (int i = 0; i < listOfSockets.size(); i++) {
            try {
                Log.i(TAG, "Closing socket");
                listOfSockets.get(i).close();
            } catch (IOException e) {
                Log.e(TAG, "Closing buffered reader was unsuccessful");
                e.printStackTrace();
            }
        }

//        asyncTask.keepAlive = false;

//        asyncTask.cancel(true);
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();
        messageActivityBroadcastReceiver = new MessageActivityBroadcastReceiver();
        registerReceiver(messageActivityBroadcastReceiver, new IntentFilter(Constants.NEW_MESSAGE_INTENT));

    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause()");
        super.onPause();
        unregisterReceiver(messageActivityBroadcastReceiver);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("etTarget", etTarget.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        String etTargetText = savedInstanceState.getString("etTarget");
        etTarget.setText(etTargetText);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected called");

            // Ever service has a binder. In service class we created LocalBinder to get service instance
            // - set our service field equal to the service instance
//            ReceiveMessagesService.LocalBinder binder = (ReceiveMessagesService.LocalBinder) service;
//            receiveMessagesService = binder.getServiceInstance();

            // Register this activity to get callbacks
//            receiveMessagesService.registerActivity(MessageActivity.this);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "Service has disconnected");
            boundReceiveService = false;
        }
    };


    public void onReceivedMessages(final String username, final String message, final boolean isPrivate, final long date) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if ((targetUsername.equals(username) && isPrivate) || (targetUsername.equals(getResources().getString(R.string.public_message_group_name)) && !isPrivate)) {
                    //incoming message belongs to this activity
                    addReceivedMessageToListView(message, username, date);
                } else {
                    postNotification(username, message, isPrivate);
                    Log.d(TAG, "Something went weird with onReceivedMessages");
                    Log.d(TAG, "username=" + username);
                    Log.d(TAG, "targetUsername=" + targetUsername);
                    Log.d(TAG, "isPrivate=" + isPrivate);
                }
            }
        });
    }


    private void setBackButtonListener() {
        ImageButton ibMABackArrow = (ImageButton) findViewById(R.id.ibMABackArrow);
        ibMABackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatsIntent = new Intent(MessageActivity.this, Lobby.class);
                startActivity(chatsIntent);
            }
        });
    }


    private void showBackButton() {
        TextView tvTo = (TextView) findViewById(R.id.tvTo);
        ImageButton ibMABackArrow = (ImageButton) findViewById(R.id.ibMABackArrow);

        // Hide To: textview
        tvTo.setVisibility(View.GONE);

        // Show back arrow
        ibMABackArrow.setVisibility(View.VISIBLE);
    }

//    private void bindToReceiveService() {
//        Intent receiveMessagesServiceIntent = new Intent(this, ReceiveMessagesService.class);
//        boundReceiveService = getApplicationContext().bindService(receiveMessagesServiceIntent, mConnection, Context.BIND_AUTO_CREATE);
//    }
//
//    private void unbindToReceiveService() {
//        if (boundReceiveService) {
//            if (receiveMessagesService != null) {
//                receiveMessagesService.unregisterActivity();
//            }
//            getApplicationContext().unbindService(mConnection);
//            boundReceiveService = false;
//        }
//    }

    private void printFoundMessages(JSONObject foundMessageJSON) throws JSONException {
        for (int i = 0; i < foundMessageJSON.names().length(); i++) {
            String key = foundMessageJSON.names().getString(i);
            String value = foundMessageJSON.get(key).toString();
            Log.i(TAG, key + " - " + value);
        }
    }


    private void postNotification(String fromUsername, String message, boolean privateMessage) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(fromUsername)
                        .setContentText(message)
                        .setTicker("New message from " + fromUsername + " - \n" + message)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setDefaults(Notification.DEFAULT_VIBRATE);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(MessageActivity.this, MessageActivity.class);
        if (privateMessage) {
//            resultIntent.putExtra(Constants.chatHandlesString, fromUsername);
            resultIntent.putExtra(Constants.USERNAME_EXTRA, fromUsername);
        } else {
//            resultIntent.putExtra(Constants.chatHandlesString, getResources().getString(R.string.public_message_group_name));
            resultIntent.putExtra(Constants.USERNAME_EXTRA, getResources().getString(R.string.public_message_group_name));
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



    private class MessageActivityBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constants.NEW_MESSAGE_INTENT :
                    onReceivedMessages(
                            intent.getStringExtra(Constants.USERNAME_EXTRA),
                            intent.getStringExtra(Constants.MESSAGE_EXTRA),
                            intent.getBooleanExtra(Constants.PRIVATE_MESSAGE_EXTRA, true),
                            intent.getLongExtra(Constants.DATE_EXTRA, 0));
                    break;
            }
        }
    }
}

