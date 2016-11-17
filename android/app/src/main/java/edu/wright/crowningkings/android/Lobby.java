package edu.wright.crowningkings.android;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
    private String[] mPlanetTitles = new String[]{"one", "2", "THREEEEE"};
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private ListView tablesListView;
    private TablesListArrayAdapter tablesListArrayAdapter;
//    private ListView privateMessagesListView;
//    private TablesListArrayAdapter privateMessagesListArrayAdapter;
    private Menu navPrivateMessagesMenu;
    private BaseClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        navigationView.getMenu().add("New Message");
        navigationView.getMenu().add("Public Messages");
        navPrivateMessagesMenu = navigationView.getMenu().addSubMenu("Private");

        ArrayList test = new ArrayList<>();
        test.add("Test");
        tablesListArrayAdapter = new TablesListArrayAdapter<String>(this,
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
        switch ((String) item.getTitle()) {
            case "Foo" :
                System.out.println("FOO");
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }












    /**
     * CROWNING KINGS METHODS
     */
    public void joinTable(View tableView) {
        TextView tv = (TextView) tableView.findViewById(R.id.table_number);
        String tableId = tv.getText().toString();
        System.out.println("tableId=" + tableId);
        joinTable(tableId);
    }

    private void joinTable(String tableId) {
        client.joinTable(tableId);
    }

    private void makeTable() {
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... voids){
                client.makeTable();
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
    public void nowLeftLobby(String user) {

    }

    @Override
    public void nowInLobby(final String user) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                navPrivateMessagesMenu.add(user);
            }
        });
    }

    @Override
    public void inLobby() {

    }

    @Override
    public void whoOnTable(String userOne, String userTwo, String tableID, String userOneColor, String userTwoColor) {

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
        final String username = "AndroidBob";
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

    }

    @Override
    public void whoInLobby(String[] users) {

    }

    @Override
    public void outLobby() {

    }



}
