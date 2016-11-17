package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _401NameInUse extends AbstractServerMessage {
    public _401NameInUse(String message) {
        setParameters(message.split(" "));
    }

    public void run(BaseClient client) {
		System.out.println("_401NameInUse.run(BaseClient)");
		//do something
    }
}