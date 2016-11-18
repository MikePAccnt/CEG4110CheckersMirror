package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _207BoardState extends AbstractServerMessage {
    public _207BoardState(String message) {
        String[] param = message.split(" ");
		String msgID = param[0];
		String table = param[1];
		String state = param[2];
		String[] fullParam = {msgID, table, state};
        setParameters(fullParam);
    }

    public void run(BaseClient client) {
		System.out.println("_207BoardState.run(BaseClient)");
		//do something
    }
}