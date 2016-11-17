package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _210TableJoined extends AbstractServerMessage {
    public _210TableJoined(String message) {
        setParameters(message.split(" "));
    }

    public void run(BaseClient client) {
		System.out.println("_210TableJoined.run(BaseClient)");
		//do something
    }
}
