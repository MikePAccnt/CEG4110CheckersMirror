package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _230NowObserving extends AbstractServerMessage {
    public _230NowObserving(String message) {
        setParameters(message.split(" "));
    }

    public void run(BaseClient client) {
		System.out.println("_230NowObserving.run(BaseClient)");
		//do something
    }
}