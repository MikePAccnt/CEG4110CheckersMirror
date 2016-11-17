package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class PlayersNotReady extends AbstractServerMessage {
    public PlayersNotReady() {
        String[] parameters = new String[1];
        parameters[0] = "409 ";
        setParameters(parameters);
    }

    public void run(BaseClient client) {
        //do something
    }
}