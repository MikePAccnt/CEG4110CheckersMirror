package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _218InLobby extends AbstractServerMessage {
    public _218InLobby(String message) {
        setParameters(message.split(" "));
    }

    public void run(BaseClient client) {
		System.out.println("_218InLobby.run(BaseClient)");
		//do something
    }
}