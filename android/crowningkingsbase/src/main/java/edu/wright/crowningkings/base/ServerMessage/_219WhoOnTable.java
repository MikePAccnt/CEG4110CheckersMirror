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
		String[] fullParam = {msgID, table, clientB, clientR};
        setParameters(fullParam);
    }

    public void run(BaseClient client) {
		System.out.println("_219WhoOnTable.run(BaseClient)");
		//do something
    }
}