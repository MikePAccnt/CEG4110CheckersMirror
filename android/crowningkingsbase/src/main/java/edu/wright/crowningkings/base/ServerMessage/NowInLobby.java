package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class NowInLobby extends AbstractServerMessage {
    public NowInLobby(String client) {
        String[] parameters = new String[2];
        parameters[0] = "000";
		parameters[1] = client;
        setParameters(parameters);
    }

    public void run(BaseClient client) {
        //do something
    }
}