package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _208GameWin extends AbstractServerMessage {
    public _208GameWin(String message) {
        String[] param = message.split(" ");
		String msgID = param[0];
		String[] fullParam = {msgID};
        setParameters(fullParam);
    }

    public void run(BaseClient client) {
		System.out.println("_208GameWin.run(BaseClient)");
		//do something
    }
}