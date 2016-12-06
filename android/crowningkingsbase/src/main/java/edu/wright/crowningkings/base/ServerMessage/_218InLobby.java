package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

import static java.lang.Thread.sleep;

/**
 * Created by csmith on 11/3/16.
 */

public class _218InLobby extends AbstractServerMessage {
    public _218InLobby(String message) {
        String[] param = message.split(" ");
		String msgID = param[0];
		String[] fullParam = {msgID};
        setParameters(fullParam);
    }

    public void run(BaseClient client) {
		System.out.println("_218InLobby.run(BaseClient)");
		client.inLobby();
        try {
            sleep(200);
        } catch (InterruptedException ie) {
            System.out.println("InterruptedException : " + ie.getMessage());
        }
    }
}
