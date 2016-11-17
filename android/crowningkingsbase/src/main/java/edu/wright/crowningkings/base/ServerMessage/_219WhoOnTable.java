package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _219WhoOnTable extends AbstractServerMessage {
    public _219WhoOnTable(String message) {
        setParameters(message.split(" "));
    }

    public void run(BaseClient client) {
		System.out.println("_219WhoOnTable.run(BaseClient)");
		//do something
    }
}