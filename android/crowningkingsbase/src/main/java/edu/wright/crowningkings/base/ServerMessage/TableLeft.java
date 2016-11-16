package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class TableLeft extends AbstractServerMessage {
    public TableLeft(String table) {
        String[] parameters = new String[2];
        parameters[0] = "222 ";
		parameters[1] = table;
        setParameters(parameters);
    }

    public void run(BaseClient client) {
        //do something
    }
}