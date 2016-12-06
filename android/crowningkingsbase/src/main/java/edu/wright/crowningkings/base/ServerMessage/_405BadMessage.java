package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _405BadMessage extends AbstractServerMessage {
    public _405BadMessage(String message) {
        setParameters(message.split(" "));
    }

    public void run(BaseClient client) {
		System.out.println("_405BadMessage.run(BaseClient)");
		client.badMessage();
    }
}
