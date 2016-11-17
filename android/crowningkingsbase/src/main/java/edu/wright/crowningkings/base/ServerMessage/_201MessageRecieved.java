package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _201MessageRecieved extends AbstractServerMessage {
    public _201MessageRecieved(String message) {
		String[] param = message.split(" ");
		String msgID = param[0];
		String from = param[1];
		String privateMessage = param[2];
		String msg = "";
		for (int i = 3; i < param.length; i++) {
			msg = msg + " " + param[i];
		}
		String[] fullParams = {msgID, from, Private, msg};
		setParameters(fullParams);
    }

    public void run(BaseClient client) {
		System.out.println("_201MessageRecieved.run(BaseClient)");
		//Add logic to say that param[2] is true if == 1
		//do something
    }
}