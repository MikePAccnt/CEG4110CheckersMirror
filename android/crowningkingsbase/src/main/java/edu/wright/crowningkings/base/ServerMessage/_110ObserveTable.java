package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by jlathery on 11/14/16.
 */

public class _110ObserveTable extends AbstractServerMessage {
    public _110ObserveTable(String tableId) {
        String[] parameters = new String[2];
        parameters[0] = "110 ";
        parameters[1] = tableId;
        setParameters(parameters);
    }

    public void run(BaseClient client) {
        //do something
    }
}
