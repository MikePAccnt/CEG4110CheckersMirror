package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _404NotInLobby extends AbstractServerMessage {
    public _404NotInLobby(String message) {
        setParameters(message.split(" "));
    }

    public void run(BaseClient client) {
		System.out.println("_404NotInLobby.run(BaseClient)");
		client.notInLobby();
    }
}
