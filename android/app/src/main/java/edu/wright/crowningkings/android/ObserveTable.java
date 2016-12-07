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

import java.util.HashSet;
import java.util.Set;

public class ObserveTable extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    BroadcastReceiver observeTableActivityBroadcastReceiver;
    IntentFilter observeTableActivityIntentFilter;
    private Menu navPrivateMessagesMenu;
    private String tableId;
    private String username;
    private BoardView boardView = null;
    private Game game = null;
    final private String TAG = "ObserveTable";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.tableId = getIntent().getStringExtra(Constants.TABLE_ID_EXTRA);
        this.username = getIntent().getStringExtra(Constants.USERNAME_EXTRA);
        setContentView(R.layout.activity_observe_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Table " + tableId);
        setSupportActionBar(toolbar);
        boardView = (BoardView) findViewById(R.id.observe_board_view);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        game = new Game(tableId, new Board(Board.initializePieces()), Team.BLACK);
        game.clearTurn();
        boardView.setGame(game);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().add(getResources().getString(R.string.make_new_message));
        //navigationView.getMenu().add(getResources().getString(R.string.public_message_group_name));
        navPrivateMessagesMenu = navigationView.getMenu().addSubMenu(getResources().getString(R.string.private_messages_menu_name));

        observeTableActivityBroadcastReceiver = new ObserveTable.ObserveTableActivityBroadcastReceiver();
        observeTableActivityIntentFilter = new IntentFilter();
        observeTableActivityIntentFilter.addAction(Constants.GAME_START_INTENT);
        observeTableActivityIntentFilter.addAction(Constants.BOARD_STATE_INTENT);
        observeTableActivityIntentFilter.addAction(Constants.WHO_ON_TABLE);
        observeTableActivityIntentFilter.addAction(Constants.OPPONENT_LEFT_TABLE_INTENT);
        observeTableActivityIntentFilter.addAction(Constants.TABLE_LEFT_INTENT);
        observeTableActivityIntentFilter.addAction(Constants.NOW_OBSERVING_INTENT);
        observeTableActivityIntentFilter.addAction(Constants.STOPPED_OBSERVING_INTENT);
        observeTableActivityIntentFilter.addAction(Constants.NEW_MESSAGE_INTENT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendBroadcast(new Intent(Constants.READY_FOR_SERVER_MSGS_INTENT));
        registerReceiver(observeTableActivityBroadcastReceiver, observeTableActivityIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(observeTableActivityBroadcastReceiver);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        System.out.println("nav item " + item.getTitle() + " was pressed ");

        Intent messageIntent = new Intent(ObserveTable.this, MessageActivity.class);
        messageIntent.putExtra(Constants.USERNAME_EXTRA, item.getTitle());
        startActivity(messageIntent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setBoardState(String board) {
        final Set<Piece> pieces = new HashSet<>(8 * 8 / 2 - (2 * 8));
        int counter = 0;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (board.charAt(counter) == '1') {
                    final Location location = new Location(x, y);
                    pieces.add(new Piece(Team.BLACK, location));
                } else if (board.charAt(counter) == '2') {
                    final Location location = new Location(x, y);
                    pieces.add(new Piece(Team.RED, location));
                } else if (board.charAt(counter) == '3') {
                    final Location location = new Location(x, y);
                    Piece bk = new Piece(Team.BLACK, location);
                    bk.makeKing();
                    pieces.add(bk);
                } else if (board.charAt(counter) == '4') {
                    final Location location = new Location(x, y);
                    Piece rk = new Piece(Team.RED, location);
                    rk.makeKing();
                    pieces.add(rk);
                } else {
                }
                counter++;
            }
        }
        game.getBoard().setPieces(pieces);
        boardView.invalidate();
        System.out.println("setting pieces");
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


    private void postNotification(String fromUsername, String message, boolean privateMessage) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_stat_logomakr_2oz5ib)
                        .setContentTitle(fromUsername)
                        .setContentText(message)
                        .setTicker("New message from " + fromUsername + " - \n" + message)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setDefaults(Notification.DEFAULT_VIBRATE);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(ObserveTable.this, MessageActivity.class);
        //resultIntent.putExtra(Constants.MESSAGES_ARRAY_EXTRA, conversation);
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
                PendingIntent.getActivity(ObserveTable.this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setAutoCancel(true);   // this removes notification on tap
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        Notification notification = mBuilder.build();
        mNotificationManager.notify(0, notification);
    }

    public void whoOnTable(final String userOne, final String userTwo, final String tableID) {
        if (tableID.equals(this.tableId)) {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!userOne.equals("-1") && !userTwo.equals("-1") && (username.equals(userOne) || username.equals(userTwo))) {
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(ObserveTable.this)
                                .setTitle(String.format(getResources().getString(R.string.status_dialog_title), tableID))
                                .setMessage(String.format(getResources().getString(R.string.status_dialog_message), userOne, userTwo) + "\n" +
                                        getResources().getString(R.string.table_dialog_ready_message))
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        sendBroadcast(new Intent(Constants.READY_INTENT));
                                        View rb = findViewById(R.id.ready_button);
                                        if (rb != null) {
                                            rb.setVisibility(View.GONE);
                                        }
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d(TAG, "I'M NOT READY!");
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });
        }
    }

    private class ObserveTableActivityBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //boardView = (BoardView) findViewById(R.id.board_view);
            switch (intent.getAction()) {
                case Constants.GAME_START_INTENT:
                    Log.d(TAG, "GAME_START_INTENT");
                    boardView.setVisibility(View.VISIBLE);
                    View bv = findViewById(R.id.ready_button);
                    if (bv != null) {
                        bv.setVisibility(View.GONE);
                    }
                    View wait = findViewById(R.id.waiting_for_opponent);
                    if (wait != null) {
                        wait.setVisibility(View.GONE);
                    }
                    break;
                case Constants.BOARD_STATE_INTENT:
                    Log.d(TAG, "BOARD_STATE_INTENT");
                    Log.d(TAG, intent.getStringExtra(Constants.BOARD_STATE_EXTRA));
                    setBoardState(intent.getStringExtra(Constants.BOARD_STATE_EXTRA));
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
                    if (intent.getBooleanExtra(Constants.PRIVATE_MESSAGE_EXTRA, true)) {
                        addUserToPrivateMessagesMenu(intent.getStringExtra(Constants.USERNAME_EXTRA));
                        postNotification(
                                intent.getStringExtra(Constants.USERNAME_EXTRA),
                                intent.getStringExtra(Constants.MESSAGE_EXTRA),
                                intent.getBooleanExtra(Constants.PRIVATE_MESSAGE_EXTRA, true));
                    }
                    break;
            }
        }
    }
}
