package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _409PlayersNotReady extends AbstractServerMessage {
    public _409PlayersNotReady(String message) {
        setParameters(message.split(" "));
    }

    public void run(BaseClient client) {
		System.out.println("_409PlayersNotReady.run(BaseClient)");
		//do something
    }
}