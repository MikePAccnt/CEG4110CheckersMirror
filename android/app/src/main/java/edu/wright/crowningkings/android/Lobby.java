package edu.wright.crowningkings.android;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Lobby extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final String TAG = "Lobby";

    private ListView tablesListView;
    private TablesListArrayAdapter tablesListArrayAdapter;
    private Menu navPrivateMessagesMenu;
    private String username;
    private BroadcastReceiver lobbyBroadcastReceiver = new LobbyBroadcastReceiver();
    private IntentFilter lobbyIntentFilter = new IntentFilter();
    private Intent androidUIService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate(Bundle)");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                makeTable();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().add(getResources().getString(R.string.make_new_message));
        navigationView.getMenu().add(getResources().getString(R.string.public_message_group_name));
        navPrivateMessagesMenu = navigationView.getMenu().addSubMenu(getResources().getString(R.string.private_messages_menu_name));

        ArrayList<String> test = new ArrayList<>();
        test.add("Test");
        tablesListArrayAdapter = new TablesListArrayAdapter<>(this,
                R.layout.lobby_table_item, R.id.table_number, test);
        tablesListView = (ListView) findViewById(R.id.lobby_tables_list);
        tablesListView.setAdapter(tablesListArrayAdapter);

        Log.d(TAG, "About to start the androiduiservice");
        androidUIService = new Intent(this, AndroidUIService.class);
        startService(androidUIService);

        //lobbyIntentFilter.addAction(Constants.USERNAME_REQUEST_INTENT);
        lobbyIntentFilter.addAction(Constants.SEND_USERNAME_REPLY_INTENT);
        lobbyIntentFilter.addAction(Constants.NEW_TABLE_INTENT);
        lobbyIntentFilter.addAction(Constants.WHO_IN_LOBBY_INTENT);
        lobbyIntentFilter.addAction(Constants.WHO_ON_TABLE);
        lobbyIntentFilter.addAction(Constants.TABLE_LIST_INTENT);
        lobbyIntentFilter.addAction(Constants.NOW_IN_LOBBY_INTENT);
        lobbyIntentFilter.addAction(Constants.TABLE_JOINED_INTENT);
        lobbyIntentFilter.addAction(Constants.NEW_MESSAGE_INTENT);
    }


    @Override
    protected void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();
        registerReceiver(lobbyBroadcastReceiver, lobbyIntentFilter);
    }


    @Override
    protected void onPause() {
        Log.d(TAG, "onPause()");
        super.onPause();
        unregisterReceiver(lobbyBroadcastReceiver);
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop()");
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy()");
        quit();
        stopService(androidUIService);
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lobby, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        System.out.println("nav item " + item.getTitle() + " was pressed ");

        Intent messageIntent = new Intent(Lobby.this, MessageActivity.class);
//        messageIntent.putExtra(Constants.chatHandlesString, item.getTitle());
        messageIntent.putExtra(Constants.USERNAME_EXTRA, item.getTitle());

        startActivity(messageIntent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addUserToPrivateMessagesMenu(final String user) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (navPrivateMessagesMenu.findItem(user.hashCode()) == null && !user.equals(username)) {
                    navPrivateMessagesMenu.add(Menu.NONE, user.hashCode(), Menu.NONE, user);
                }
            }
        });
    }


    /**
     * CROWNING KINGS METHODS
     */
    private void joinTable(final String tableId) {
        sendBroadcast(new Intent(Constants.JOIN_TABLE_INTNENT)
                .putExtra(Constants.TABLE_ID_EXTRA, tableId));
    }


    private void makeTable() {
        sendBroadcast(new Intent(Constants.MAKE_TABLE_INTNENT));
    }


    public void observeTable(final String tableId) {
        sendBroadcast(new Intent(Constants.OBSERVE_TABLE_INTENT)
                .putExtra(Constants.TABLE_ID_EXTRA, tableId));
    }


    public void addNewTables(final String[] tableID) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tablesListArrayAdapter.clear();
                for (String table : tableID) {
                    tablesListArrayAdapter.add(table);
                }
            }
        });
    }


    public void nowInLobby(final String user) {
//        addUserToPrivateMessagesMenu(user);
    }


    public void whoOnTable(final String userOne, final String userTwo, final String tableID) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder;
                if (userOne.equals("-1") || userTwo.equals("-1")) {
                    builder = new AlertDialog.Builder(Lobby.this)
                            .setTitle(String.format(getResources().getString(R.string.status_dialog_title), tableID))
                            .setMessage(String.format(getResources().getString(R.string.status_dialog_message), userOne, userTwo))
                            .setPositiveButton(R.string.status_dialog_join_button, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    joinTable(tableID);
                                }
                            })
                            .setNegativeButton(R.string.status_dialog_observe_button, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    observeTable(tableID);
                                }
                            })
                            .setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // cancel
                                }
                            });
                } else {
                    builder = new AlertDialog.Builder(Lobby.this)
                            .setTitle(String.format(getResources().getString(R.string.status_dialog_title), tableID))
                            .setMessage(String.format(getResources().getString(R.string.status_dialog_message), userOne, userTwo))
                            .setNegativeButton(R.string.status_dialog_observe_button, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    observeTable(tableID);
                                }
                            })
                            .setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // cancel
                                }
                            });
                }
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }


//    public void sendUsernameRequest() {
//        AlertDialog.Builder builder;
//        builder = new AlertDialog.Builder(Lobby.this)
//                .setTitle(getResources().getString(R.string.username_request_dialog_title))
//                .setMessage(getResources().getString(R.string.username_request_dialog_message))
//                .setCancelable(false)
//                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        sendUsernameReply("AndroidBob");
//                    }
//                });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }


//    public void sendUsernameReply(final String username) {
//        sendBroadcast(new Intent(Constants.SEND_USERNAME_REPLY_INTENT).putExtra(Constants.USERNAME_EXTRA, username));
//    }


    public void whoInLobby(String[] users) {
        for (String user : users) {
            nowInLobby(user);
        }
    }


    public void quit() {
        sendBroadcast(new Intent(Constants.QUIT_INTENT));
    }


    public void onTableClick(View tableView) {
        TextView tv = (TextView) tableView.findViewById(R.id.table_number);
        final String tableId = tv.getText().toString();
        sendBroadcast(new Intent(Constants.ASK_TABLE_STATUS_INTENT)
                .putExtra(Constants.TABLE_ID_EXTRA, tableId));
    }


    private void tableJoined(String tableId) {
        Intent tableIntent = new Intent(this, TableActivity.class);
        tableIntent.putExtra(Constants.JOIN_AS, Constants.PLAYER);
        tableIntent.putExtra(Constants.TABLE_ID_EXTRA, tableId);
        tableIntent.putExtra(Constants.USERNAME_EXTRA, username);
        startActivity(tableIntent);

        Log.d(TAG, "tableJoined(String)");
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
        Intent resultIntent = new Intent(Lobby.this, MessageActivity.class);
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
//        stackBuilder.addParentStack(this);
        // Adds the Intent that starts the Activity to the top of the stack
//        stackBuilder.addNextIntent(resultIntent);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.setContentIntent(resultPendingIntent);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(Lobby.this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setAutoCancel(true);   // this removes notification on tap
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        Notification notification = mBuilder.build();
        mNotificationManager.notify(0, notification);
    }


    private class LobbyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
//                case Constants.USERNAME_REQUEST_INTENT:
//                    Log.d(TAG, "USERNAME_REQUEST_INTENT");
//                    sendUsernameRequest();
//                    break;
//                case Constants.SEND_USERNAME_REPLY_INTENT:
//                    Log.d(TAG, "SEND_USERNAME_REPLY_INTENT");
//                    username = intent.getStringExtra(Constants.USERNAME_EXTRA);
//                    break;
                case Constants.TABLE_LIST_INTENT:
                case Constants.NEW_TABLE_INTENT:
                    addNewTables(intent.getStringArrayExtra(Constants.TABLE_ID_ARRAY_EXTRA));
                    break;
                case Constants.WHO_IN_LOBBY_INTENT:
                    Log.d(TAG, "WHO_IN_LOBBY_INTENT");
                    whoInLobby(intent.getStringArrayExtra(Constants.USERS_ARRAY_EXTRA));
                    break;
                case Constants.WHO_ON_TABLE:
                    Log.d(TAG, "WHO_ON_TABLE");
                    String userOne = intent.getStringExtra(Constants.USER_ONE_EXTRA);
                    String userTwo = intent.getStringExtra(Constants.USER_TWO_EXTRA);
                    String tableId = intent.getStringExtra(Constants.TABLE_ID_EXTRA);
                    whoOnTable(userOne, userTwo, tableId);
                    break;
                case Constants.NOW_IN_LOBBY_INTENT:
                    nowInLobby(intent.getStringExtra(Constants.USERNAME_EXTRA));
                    break;
                case Constants.TABLE_JOINED_INTENT:
                    tableJoined(intent.getStringExtra(Constants.TABLE_ID_EXTRA));
                    break;
                case Constants.NEW_MESSAGE_INTENT:
                    postNotification(
                            intent.getStringExtra(Constants.USERNAME_EXTRA),
                            intent.getStringExtra(Constants.MESSAGE_EXTRA),
                            intent.getBooleanExtra(Constants.PRIVATE_MESSAGE_EXTRA, true));

                    if (intent.getBooleanExtra(Constants.PRIVATE_MESSAGE_EXTRA, true)) {
                        addUserToPrivateMessagesMenu(intent.getStringExtra(Constants.USERNAME_EXTRA));
                    }
                    break;
            }
        }
    }
}
