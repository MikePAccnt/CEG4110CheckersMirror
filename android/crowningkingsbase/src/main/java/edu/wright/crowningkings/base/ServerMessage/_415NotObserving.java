package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _415NotObserving extends AbstractServerMessage {
    public _415NotObserving(String message) {
        setParameters(message.split(" "));
    }

    public void run(BaseClient client) {
		System.out.println("_415NotObserving.run(BaseClient)");
		//do something
    }
}