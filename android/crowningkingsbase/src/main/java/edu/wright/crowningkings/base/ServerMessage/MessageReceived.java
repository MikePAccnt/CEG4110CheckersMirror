package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class MessageReceived extends AbstractServerMessage {
    public MessageReceived(String message, String isPrivate, String client) {
        String[] parameters = new String[4];
        parameters[0] = "201 ";
		parameters[1] = client;
		parameters[2] = isPrivate;
		parameters[3] = message;
        setParameters(parameters);
    }

    public void run(BaseClient client) {
        //do something
    }
}
