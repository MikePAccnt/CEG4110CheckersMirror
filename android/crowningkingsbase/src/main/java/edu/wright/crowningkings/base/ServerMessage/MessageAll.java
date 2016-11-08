package edu.wright.crowningkings.base.ServerMessage;

/**
 * Created by csmith on 11/3/16.
 */

public class MessageAll extends AbstractServerMessage {
    public MessageAll(String message) {
        String[] parameters = new String[2];
        parameters[0] = "101 ";
        parameters[1] = message;
        setParameters(parameters);
    }

    public void run(){
        //do something
    }
}
