package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class NotInLobby extends AbstractServerMessage {
    public NotInLobby() {
        String[] parameters = new String[1];
        parameters[0] = "404";
        setParameters(parameters);
    }

    public void run(BaseClient client) {
        //do something
    }
}