package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _230NowObserving extends AbstractServerMessage {
    public _230NowObserving(String message) {
		String[] param = message.split(" ");
		String msgID = param[0];
		String table = param[1];
		String[] fullParam = {msgID, table};
        setParameters(fullParam);
    }

    public void run(BaseClient client) {
		//System.out.println("_230NowObserving.run(BaseClient)");
		String[] p = getParameters();
		client.nowObserving(p[1]);
    }
}