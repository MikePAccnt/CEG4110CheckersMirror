package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class WhoOnTable extends AbstractServerMessage {
    public WhoOnTable(String table, String clientBlack, String clientRed) {
        String[] parameters = new String[1];
        parameters[0] = "219 ";
		parameters[1] = clientBlack;
		parameters[2] = clientRed;
        setParameters(parameters);
    }

    public void run(BaseClient client) {
        //do something
    }
}