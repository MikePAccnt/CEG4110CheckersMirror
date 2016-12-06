package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _400NetException extends AbstractServerMessage {
    public _400NetException(String message) {
        setParameters(message.split(" "));
    }

    public void run(BaseClient client) {
		System.out.println("_400NetException.run(BaseClient)");
		client.netException();
    }
}
