package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _101MessageAll extends AbstractServerMessage {
    public _101MessageAll(String message) {
        String[] parameters = new String[2];
        parameters[0] = "101 ";
        parameters[1] = message;
        setParameters(parameters);
    }

    public void run(BaseClient client) {
        String[] p = getParameters();
		client.messageAll(p[1]);
    }
}
