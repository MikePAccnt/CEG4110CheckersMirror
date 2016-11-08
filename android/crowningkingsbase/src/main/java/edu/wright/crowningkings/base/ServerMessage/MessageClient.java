package edu.wright.crowningkings.base.ServerMessage;

/**
 * Created by csmith on 11/3/16.
 */

public class MessageClient extends AbstractServerMessage {
    public MessageClient(String message, String client) {
        String[] parameters = new String[4];
        parameters[0] = "102";
        parameters[1] = "1";
        parameters[2] = client;
        parameters[3] = message;
        setParameters(parameters);
    }

    public void run(){
        //do something
    }
}
