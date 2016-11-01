package edu.wright.crowningkings.android;

import android.support.v7.app.AppCompatActivity;
import edu.wright.crowningkings.base.*;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /** on onCreate we will start connection to server by doing something like the following
         *
         *      ServerConnection server = new ServerConnection(ipAddress, portNumber);
         *
         *  where ipAddress is the IP address String, and portNumber is the int value of the port
         *  number.
         */
    }
}
