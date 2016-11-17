package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _222TableLeft extends AbstractServerMessage {
    public _222TableLeft(String message) {
        setParameters(message.split(" "));
    }

    public void run(BaseClient client) {
		System.out.println("_222TableLeft.run(BaseClient)");
		//do something
    }
}