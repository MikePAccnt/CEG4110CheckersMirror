package edu.wright.crowningkings.android;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.wright.crowningkings.base.BaseClient;
import edu.wright.crowningkings.base.UserInterface.AbstractUserInterface;

public class Lobby extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AbstractUserInterface {
    private final String TAG = "Lobby";

    private ListView tablesListView;
    private TablesListArrayAdapter tablesListArrayAdapter;
    private Menu navPrivateMessagesMenu;
    private BaseClient client;
    private String username;


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
        navigationView.getMenu().add("New Messages");
        navigationView.getMenu().add("Public Messages");
        navPrivateMessagesMenu = navigationView.getMenu().addSubMenu("Private");

        ArrayList<String> test = new ArrayList<>();
        test.add("Test");
        tablesListArrayAdapter = new TablesListArrayAdapter<>(this,
                R.layout.lobby_table_item, R.id.table_number, test);
        tablesListView = (ListView) findViewById(R.id.lobby_tables_list);
        tablesListView.setAdapter(tablesListArrayAdapter);

        new AsyncTask<AbstractUserInterface, Void, Void>() {
            protected Void doInBackground(AbstractUserInterface... voids){
                client = new BaseClient(voids[0]);
                return null;
            }
        }.execute(this);
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
        messageIntent.putExtra(Constants.chatROWID, Long.valueOf(item.getTitle().hashCode()));
        messageIntent.putExtra(Constants.chatHandlesString, item.getTitle());
        startActivity(messageIntent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void quit() {
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... voids){
                client.quit();
                return null;
            }
        }.execute();
    }

    public void joinTable(View tableView) {//askTableStatus should be method name, but onclick isn't working
        TextView tv = (TextView) tableView.findViewById(R.id.table_number);
        final String tableId = tv.getText().toString();
        System.out.println("tableId=" + tableId);
        new AsyncTask<String, Void, Void>() {
            protected Void doInBackground(String... tables){
                client.askTableStatus(tableId);
                return null;
            }
        }.execute();
    }


    /**
     * CROWNING KINGS METHODS
     */
    private void joinTable(final String tableId) {
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... voids){
                client.joinTable(tableId);
                return null;
            }
        }.execute();
    }

    //@Override
    private void makeTable() {
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... voids){
                client.makeTable();
                return null;
            }
        }.execute();
    }

    public void observeTable(final String tableId) {
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... voids){
                client.observeTable(tableId);
                return null;
            }
        }.execute();
    }




    @Override
    public void tableList(final String[] tableID) {
        System.out.println("UI updateTableList(String[])");
        System.out.println("tables.length=" + tableID.length);
        for (String table : tableID){
            System.out.println(table);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("runonuithread.run()");
                tablesListArrayAdapter.clear();
                for (String table : tableID) {
                    tablesListArrayAdapter.add(table);
                }
            }
        });
    }

    @Override
    public void nowLeftLobby(final String user) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //not when leaving lobby... navPrivateMessagesMenu.removeItem(user.hashCode());
            }
        });
    }

    @Override
    public void nowInLobby(final String user) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (navPrivateMessagesMenu.findItem(user.hashCode()) == null && !user.equals(username)) {
                    navPrivateMessagesMenu.add(Menu.NONE, user.hashCode(), Menu.NONE, user);
                }
            }
        });
    }

    @Override
    public void inLobby() {

    }

    @Override
    public void whoOnTable(final String userOne, final String userTwo, final String tableID, String userOneColor, String userTwoColor) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "whoOnTable(...) run()");
                AlertDialog.Builder builder = null;
                if (userOne.equals("-1") || userTwo.equals("-1")){
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
                                    // continue with delete
                                }
                            });
                } else {
                    builder = new AlertDialog.Builder(Lobby.this)
                            .setTitle(String.format(getResources().getString(R.string.status_dialog_title), tableID))
                            .setMessage(String.format(getResources().getString(R.string.status_dialog_message), userOne, userTwo))
                            .setNegativeButton(R.string.status_dialog_observe_button, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            });
                }

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public void opponentLeftTable() {

    }

    @Override
    public void yourTurn() {

    }

    @Override
    public void tableLeft(String tableID) {

    }

    @Override
    public void nowObserving(String tableID) {

    }

    @Override
    public void stoppedObserving(String tableID) {

    }

    @Override
    public void sendUsernameRequest() {
        Log.d(TAG, "setUsername");
        username = "AndroidBob";
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... voids){
                client.setUsername(username);
                return null;
            }
        }.execute();
    }

    @Override
    public void connectionOK() {

    }

    @Override
    public void message(String message, String from, boolean privateMessage) {
        postNotification(from, message, from.hashCode());
        if (privateMessage) {
            ///private message stuff

        }
        else {
            //public message stuff
        }
    }

    private void postNotification(String handleID, String message, long cROWID) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(handleID)
                        .setContentText(message)
                        .setTicker("New message from " + handleID + " - \n" + message)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setDefaults(Notification.DEFAULT_VIBRATE);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MessageActivity.class);
        resultIntent.putExtra(Constants.chatROWID, cROWID);
        resultIntent.putExtra(Constants.chatHandlesString, handleID);

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





    @Override
    public void newtable(final String tableID) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tablesListArrayAdapter.addTable(tableID);
            }
        });

    }

    @Override
    public void gameStart() {

    }

    @Override
    public void colorBlack() {

    }

    @Override
    public void colorRed() {

    }

    @Override
    public void opponentMove(String[] from, String[] to) {

    }

    @Override
    public void boardState(String[][] boardState) {

    }

    @Override
    public void gameWon() {

    }

    @Override
    public void gameLose() {

    }

    @Override
    public void tableJoined(String tableID) {
        Intent tableIntent = new Intent(Lobby.this, TableActivity.class);
        tableIntent.putExtra(Constants.JOIN_AS, Constants.PLAYER);
        tableIntent.putExtra(Constants.TABLE_ID, tableID);
        startActivity(tableIntent);
    }

    @Override
    public void whoInLobby(String[] users) {
        for (String user : users) {
            nowInLobby(user);
        }
    }

    @Override
    public void outLobby() {}
}
