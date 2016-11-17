package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _209GameLose extends AbstractServerMessage {
    public _209GameLose(String message) {
        setParameters(message.split(" "));
    }

    public void run(BaseClient client) {
		System.out.println("_209GameLose.run(BaseClient)");
		//do something
    }
}
