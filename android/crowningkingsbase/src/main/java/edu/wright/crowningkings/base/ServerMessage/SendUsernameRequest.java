package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/16/16.
 */

public class SendUsernameRequest extends AbstractServerMessage {
    public SendUsernameRequest(String message) {
        String[] parameters = new String[1];
        parameters[0] = message;
        setParameters(parameters);
    }


    @Override
    public void run(BaseClient client) {
        client.sendUsernameRequest();
    }
}
