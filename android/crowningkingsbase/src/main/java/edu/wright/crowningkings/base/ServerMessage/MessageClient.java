package edu.wright.crowningkings.base.ServerMessage;

/**
 * Created by csmith on 11/3/16.
 */

public class MessageClient extends AbstractServerMessage {
    public MessageClient(String message, String client) {
        String[] parameters = new String[3];
        parameters[0] = "102";
        parameters[1] = client;
        parameters[2] = message;
        setParameters(parameters);
    }

    public void run(){
        //do something
    }
}
