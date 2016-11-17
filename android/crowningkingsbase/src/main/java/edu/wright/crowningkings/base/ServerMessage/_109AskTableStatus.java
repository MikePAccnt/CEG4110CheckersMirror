package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by jlathery on 11/14/16.
 */

public class _109AskTableStatus extends AbstractServerMessage {
    public _109AskTableStatus(String table) {
        String[] parameters = new String[2];
        parameters[0] = "109 ";
        parameters[1] = table ;
        setParameters(parameters);
    }

    public void run(BaseClient client) {
        //do something
    }
}
