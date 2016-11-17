package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _104JoinTable extends AbstractServerMessage {
    public _104JoinTable(String tableId) {
        String[] parameters = new String[2];
        parameters[0] = "104 ";
        parameters[1] = tableId;
        setParameters(parameters);
    }

    public void run(BaseClient client) {
        //do something
    }
}


