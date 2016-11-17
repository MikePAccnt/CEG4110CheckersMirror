package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _200ConnectionOkay extends AbstractServerMessage {
    public _200ConnectionOkay(String message) {
        setParameters(message.split(" "));
    }

    public void run(BaseClient client) {
        //do something
    }
}
