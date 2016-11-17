package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _102MessageClient extends AbstractServerMessage {
    public _102MessageClient(String message, String client) {
        String[] parameters = new String[3];
        parameters[0] = "102 ";
        parameters[1] = client;
        parameters[2] = message;
        setParameters(parameters);
    }

    public void run(BaseClient client) {
        //do something
    }
}
