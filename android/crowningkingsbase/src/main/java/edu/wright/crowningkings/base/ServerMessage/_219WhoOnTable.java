package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _219WhoOnTable extends AbstractServerMessage {
    public _219WhoOnTable(String message) {
		String[] param = message.split(" ");
		String msgID = param[0];
		String table = param[1];
		String clientB = param[2];
		String clientR = param[3];
		String[] fullParam = {msgID, table, clientB, clientR, "Black", "Red"};
        setParameters(fullParam);
    }

    public void run(BaseClient client) {
		System.out.println("_219WhoOnTable.run(BaseClient)");
		String[] p = getParameters();
		client.whoOnTable(p[2], p[3], p[1], p[4], p[5]);
    }
}
