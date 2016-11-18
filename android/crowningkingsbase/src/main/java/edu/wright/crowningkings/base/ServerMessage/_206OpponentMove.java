package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _206OpponentMove extends AbstractServerMessage {
    public _206OpponentMove(String message) {
        String[] param = message.split(" ");
		String msgID = param[0];
		String fromx = param[1];
		String fromy = param[2];
		String tox = param[3];
		String toy = param[4];
		String[] fullParam = {msgID, fromx, fromy, tox, toy};
        setParameters(fullParam);
    }

    public void run(BaseClient client) {
		//System.out.println("_206OpponentMove.run(BaseClient)");
		String[] p1 = {getParameters()[1], getParameters()[2]};
		String[] p2 = {getParameters()[3], getParameters()[4]};
		client.opponentMove(p1, p2);
    }
}