package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class BoardState extends AbstractServerMessage {
    public BoardState(String table, String state) {
        String[] parameters = new String[3];
        parameters[0] = "207 ";
		parameters[1] = table;
		parameters[2] = state;
        setParameters(parameters);
    }

    public void run(BaseClient client) {
        //do something
    }
}