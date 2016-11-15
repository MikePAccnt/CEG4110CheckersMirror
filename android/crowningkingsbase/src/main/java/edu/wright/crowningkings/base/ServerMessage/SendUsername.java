package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class SendUsername extends AbstractServerMessage {
    public SendUsername(String username) {
        String[] parameters = new String[1];
        parameters[0] = username;
        setParameters(parameters);
    }

    @Override
    public String toServerMessage() {
        return getParameters()[0];
    }

    @Override
    public void run(BaseClient client) {
        //Run stuff
    }
}
