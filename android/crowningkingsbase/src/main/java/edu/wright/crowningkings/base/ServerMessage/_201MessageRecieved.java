package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _201MessageRecieved extends AbstractServerMessage {
    public _201MessageRecieved(String message) {
		setParameters(message.split(" "));
    }

    public void run(BaseClient client) {
		System.out.println("_201MessageRecieved.run(BaseClient)");
		//do something
    }
}
