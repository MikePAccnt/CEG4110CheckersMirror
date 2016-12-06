package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _403TableFull extends AbstractServerMessage {
    public _403TableFull(String message) {
        setParameters(message.split(" "));
    }

    public void run(BaseClient client) {
		System.out.println("_403TableFull.run(BaseClient)");
		client.tblFull();
    }
}
