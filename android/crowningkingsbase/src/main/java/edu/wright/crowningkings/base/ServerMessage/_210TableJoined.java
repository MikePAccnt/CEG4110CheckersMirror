package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _210TableJoined extends AbstractServerMessage {
    public _210TableJoined(String message) {
        String[] param = message.split(" ");
		String msgID = param[0];
		String table = param[1];
		String[] fullParam = {msgID, table};
        setParameters(fullParam);
    }

    public void run(BaseClient client) {
		//System.out.println("_210TableJoined.run(BaseClient)");
		String[] p = getParameters();
		client.tableJoined(p[1]);
    }
}
