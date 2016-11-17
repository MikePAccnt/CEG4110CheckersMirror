package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _206OpponentMove extends AbstractServerMessage {
    public _206OpponentMove(String message) {
        setParameters(message.split(" "));
    }

    public void run(BaseClient client) {
		System.out.println("_206OpponentMove.run(BaseClient)");
		//do something
    }
}