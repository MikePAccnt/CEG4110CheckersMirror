package edu.wright.crowningkings.base.ServerMessage;

/**
 * Created by csmith on 11/8/16.
 */

public class Move extends AbstractServerMessage {
    public Move (String fromx, String fromy, String tox, String toy) {
        String from = fromy + "," + fromx;
        String to = toy + "," + tox;

        String[] parameters = new String[3];
        parameters[0] = "106 ";
        parameters[1] = from;
        parameters[2] = to;
        setParameters(parameters);
    }

    public void run() {
        //do something
    }
}
