package edu.wright.crowningkings.android;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import edu.wright.crowningkings.base.BaseClient;
import edu.wright.crowningkings.base.UserInterface.AbstractUserInterface;

public class MainActivity extends AppCompatActivity implements AbstractUserInterface {
    private final String TAG = "MainActivity";
    private String[] mPlanetTitles = new String[]{"one", "2", "THREEEEE"};
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private ListView tablesListView;
    private TablesListArrayAdapter tablesListArrayAdapter;

    private BaseClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


//        mDrawerLayout = (DrawerLayout) findViewById(R.id.lobby_drawer_layout);
//        mDrawerList = (ListView) findViewById(R.id.left_drawer);
//        Log.d(TAG, "mDrawerLayout=" + mDrawerLayout);
//        Log.d(TAG, "mDrawerList=" + mDrawerList);
//
//        //Set the adapter for the list view
//        mDrawerList.setAdapter(new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1, mPlanetTitles));
//        //Set the list's click listener
//        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        ArrayList test = new ArrayList<>();
        test.add("Test");
        tablesListArrayAdapter = new TablesListArrayAdapter<>(this,
                R.layout.lobby_table_item, R.id.table_number, test);
//        tablesListArrayAdapter = new TablesListArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1, test);
        tablesListView = (ListView) findViewById(R.id.lobby_tables_list);
        tablesListView.setAdapter(tablesListArrayAdapter);


        new AsyncTask<AbstractUserInterface, Void, Void>() {
            protected Void doInBackground(AbstractUserInterface... voids){
                client = new BaseClient(voids[0]);
                return null;
            }
        }.execute(this);
    }




    public void setUsername(View view) {
        Log.d(TAG, "setUsername");
        final String username = "AndroidBob";
        if (client != null) {
            new AsyncTask<Void, Void, Void>() {
                protected Void doInBackground(Void... voids){
                    client.setUsername(username);
                    return null;
                }
            }.execute();
        }
    }

    public void sendPublicMessage(View view) {

    }

    public void joinTable(View tableView) {
        TextView tv = (TextView) tableView.findViewById(R.id.table_number);
        System.out.println("text=" + tv.getText());
    }

    @Override
    public String getUsernameFromUser() {
        return "oldAndroid";
    }

    @Override
    public void sendWantTable() {

    }

    @Override
    public void removeUser(String user) {

    }

    @Override
    public void makeTable(final String tableID) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tablesListArrayAdapter.addTable(tableID);
            }
        });

    }

    @Override
    public void makeTable(final String[] tableID) {
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
    public void sendJoinPlayTable(String tableID) {

    }

    @Override
    public void sendJoinObserveTable(String tableID) {

    }

    @Override
    public void setJoinPlayTable(String tableID, String oponent) {

    }

    @Override
    public void setJoinObserveTable(String tableID, String user1, String user2) {

    }

    @Override
    public void sendPublicMessage() {

    }

    @Override
    public void sendPrivateMessage() {

    }

    @Override
    public String getTableIdFromUser() {
        return null;
    }

    @Override
    public void sendMoveToServer() {

    }

    @Override
    public void updateBoard(String[][] board) {

    }


    @Override
    public void updateLobbyChat(String newMessage) {

    }

    @Override
    public void addUser(String newUser) {

    }

    @Override
    public void updateError(String errorConst) {

    }

    private class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        }
    }
}
