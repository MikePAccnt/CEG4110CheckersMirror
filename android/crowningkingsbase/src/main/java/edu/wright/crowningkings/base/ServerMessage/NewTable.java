package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/16/16.
 */

public class NewTable extends AbstractServerMessage{
    public NewTable(String message) {
        setParameters(message.split(" "));
    }

    public void run(BaseClient client) {
        System.out.println("NewTable.run(BaseClient)");
        String[] params = getParameters();
        client.addTable(params[1]);
    }
}
