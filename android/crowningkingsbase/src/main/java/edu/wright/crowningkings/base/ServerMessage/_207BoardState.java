package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _207BoardState extends AbstractServerMessage {
    public _207BoardState(String message) {
        setParameters(message.split(" "));
    }

    public void run(BaseClient client) {
		System.out.println("_207BoardState.run(BaseClient)");
		//do something
    }
}