package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class OpponentMove extends AbstractServerMessage {
    public OpponentMove(String fromx, String fromy, String tox, String toy) {
        String from = fromy + "," + fromx;
        String to = toy + "," + tox;
		
        String[] parameters = new String[3];
        parameters[0] = "206 ";
		parameters[1] = from;
		parameters[2] = to;
        setParameters(parameters);
    }

    public void run(BaseClient client) {
        //do something
    }
}