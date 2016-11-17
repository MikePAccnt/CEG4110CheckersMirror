package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _212WhoInLobby extends AbstractServerMessage{
    public _212WhoInLobby(String message) {
        setParameters(message.split(" "));
    }

    public void run(BaseClient client) {
        System.out.println("_212WhoInLobby.run(BaseClient)");
        String[] params = getParameters();
        String[] clients = new String[params.length -1];
        for (int i = 0; i < clients.length; i ++) {
            clients[i] = params[i+1];
        }
        client.whoInLobby(clients);
    }
}
