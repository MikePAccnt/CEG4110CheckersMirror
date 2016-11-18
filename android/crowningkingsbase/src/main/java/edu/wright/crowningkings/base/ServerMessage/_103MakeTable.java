package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _103MakeTable extends AbstractServerMessage {
    public _103MakeTable() {
        String[] parameters = new String[1];
        parameters[0] = "103 ";
        setParameters(parameters);
    }

    public void run(BaseClient client) {
        String[] p = getParameters();
		client.makeTable();
    }
}
