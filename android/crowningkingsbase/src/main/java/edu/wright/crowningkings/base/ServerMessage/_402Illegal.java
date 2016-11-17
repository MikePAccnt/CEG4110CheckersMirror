package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _402Illegal extends AbstractServerMessage {
    public _402Illegal(String message) {
        setParameters(message.split(" "));
    }

    public void run(BaseClient client) {
		System.out.println("_402Illegal.run(BaseClient)");
		//do something
    }
}