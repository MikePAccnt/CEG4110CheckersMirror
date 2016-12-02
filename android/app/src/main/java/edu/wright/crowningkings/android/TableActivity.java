package edu.wright.crowningkings.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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

/**
 * Created by csmith on 11/22/16.
 */

public class TableActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final String TAG = "TableActivity";
    BroadcastReceiver tableActivityBroadcastReceiver;
    IntentFilter tableActivityIntentFilter;
    private Menu navPrivateMessagesMenu;
    private String tableId;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate(Bundle)");
        super.onCreate(savedInstanceState);

        this.tableId = getIntent().getStringExtra(Constants.TABLE_ID_EXTRA);
        this.username = getIntent().getStringExtra(Constants.USERNAME_EXTRA);

        setContentView(R.layout.activity_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Table " + tableId);
        setSupportActionBar(toolbar);

        final BoardView boardView = (BoardView) findViewById(R.id.board_view);
        boardView.setGame(new Game(tableId, new Board(8, Board.initializePieces(8)), Team.RED));

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


        tableActivityBroadcastReceiver = new TableActivityBroadcastReceiver();
        tableActivityIntentFilter = new IntentFilter();
        tableActivityIntentFilter.addAction(Constants.GAME_START_INTENT);
        tableActivityIntentFilter.addAction(Constants.NEW_TABLE_INTENT);
        tableActivityIntentFilter.addAction(Constants.COLOR_BLACK_INTENT);
        tableActivityIntentFilter.addAction(Constants.COLOR_RED_INTENT);
        tableActivityIntentFilter.addAction(Constants.OPPONENT_MOVE_INTENT);
        tableActivityIntentFilter.addAction(Constants.BOARD_STATE_INTENT);
        tableActivityIntentFilter.addAction(Constants.GAME_WIN_INTENT);
        tableActivityIntentFilter.addAction(Constants.GAME_LOSE_INTENT);
        tableActivityIntentFilter.addAction(Constants.WHO_ON_TABLE);
        tableActivityIntentFilter.addAction(Constants.OPPONENT_LEFT_TABLE_INTENT);
        tableActivityIntentFilter.addAction(Constants.YOUR_TURN_INTENT);
        tableActivityIntentFilter.addAction(Constants.TABLE_LEFT_INTENT);
        tableActivityIntentFilter.addAction(Constants.NOW_OBSERVING_INTENT);
        tableActivityIntentFilter.addAction(Constants.STOPPED_OBSERVING_INTENT);
        tableActivityIntentFilter.addAction(Constants.NEW_MESSAGE_INTENT);
    }


    @Override
    public void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();
        registerReceiver(tableActivityBroadcastReceiver, tableActivityIntentFilter);
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause()");
        super.onPause();
        unregisterReceiver(tableActivityBroadcastReceiver);
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop()");
        super.onStop();
        sendBroadcast(new Intent(Constants.LEAVE_TABLE_INTNENT));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //leaveTable
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        System.out.println("nav item " + item.getTitle() + " was pressed ");

        Intent messageIntent = new Intent(TableActivity.this, MessageActivity.class);
        messageIntent.putExtra(Constants.chatROWID, Long.valueOf(item.getTitle().hashCode()));
        messageIntent.putExtra(Constants.chatHandlesString, item.getTitle());
        startActivity(messageIntent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onReadyClick(final View view) {
        System.out.println("onReadyClick");
        AlertDialog.Builder builder = new AlertDialog.Builder(TableActivity.this)
                .setMessage(getResources().getString(R.string.table_dialog_ready_message))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sendBroadcast(new Intent(Constants.READY_INTENT));
                        view.setVisibility(View.GONE);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
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


    public void whoOnTable(final String userOne, final String userTwo, final String tableID) {
        if (tableID.equals(this.tableId)) {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!userOne.equals("-1") && !userTwo.equals("-1")) {
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(TableActivity.this)
                                .setTitle(String.format(getResources().getString(R.string.status_dialog_title), tableID))
                                .setMessage(String.format(getResources().getString(R.string.status_dialog_message), userOne, userTwo) + "\n" +
                                        getResources().getString(R.string.table_dialog_ready_message))
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d(TAG, "I'M READY!");
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });
        }
    }


    private class TableActivityBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constants.GAME_START_INTENT:
                    Log.d(TAG, "GAME_START_INTENT");
                    findViewById(R.id.board_view).setVisibility(View.VISIBLE);
                    break;
                case Constants.COLOR_BLACK_INTENT:
                    Log.d(TAG, "COLOR_BLACK_INTENT");
                    break;
                case Constants.COLOR_RED_INTENT:
                    Log.d(TAG, "COLOR_RED_INTENT");
                    break;
                case Constants.OPPONENT_MOVE_INTENT:
                    Log.d(TAG, "OPPONENT_MOVE_INTENT");
                    break;
                case Constants.BOARD_STATE_INTENT:
                    Log.d(TAG, "BOARD_STATE_INTENT");
                    break;
                case Constants.GAME_WIN_INTENT:
                    Log.d(TAG, "GAME_WIN_INTENT");
                    break;
                case Constants.GAME_LOSE_INTENT:
                    Log.d(TAG, "GAME_LOSE_INTENT");
                    break;
                case Constants.WHO_ON_TABLE:
                    Log.d(TAG, "WHO_ON_TABLE");
                    String userOne = intent.getStringExtra(Constants.USER_ONE_EXTRA);
                    String userTwo = intent.getStringExtra(Constants.USER_TWO_EXTRA);
                    String tableId = intent.getStringExtra(Constants.TABLE_ID_EXTRA);
                    whoOnTable(userOne, userTwo, tableId);
                    break;
                case Constants.OPPONENT_LEFT_TABLE_INTENT:
                    Log.d(TAG, "OPPONENT_LEFT_TABLE_INTENT");
                    break;
                case Constants.YOUR_TURN_INTENT:
                    Log.d(TAG, "YOUR_TURN_INTENT");
                    break;
                case Constants.TABLE_LEFT_INTENT:
                    Log.d(TAG, "TABLE_LEFT_INTENT");
                    break;
                case Constants.NOW_OBSERVING_INTENT:
                    Log.d(TAG, "NOW_OBSERVING_INTENT");
                    break;
                case Constants.STOPPED_OBSERVING_INTENT:
                    Log.d(TAG, "STOPPED_OBSERVING_INTENT");
                    break;
                case Constants.NEW_MESSAGE_INTENT:
                    Log.d(TAG, "NEW_MESSAGE_INTENT");
                    if (intent.getBooleanExtra(Constants.PRIVATE_MESSAGE_EXTRA, true)) {
                        addUserToPrivateMessagesMenu(intent.getStringExtra(Constants.USERNAME_EXTRA));
                    }
                    break;

            }
        }
    }
}
