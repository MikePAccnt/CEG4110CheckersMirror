package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/15/16.
 */

public class TableList extends AbstractServerMessage{
    public TableList(String message) {
        setParameters(message.split(" "));
    }

    public void run(BaseClient client) {
        System.out.println("TableList.run(BaseClient)");
        String[] params = getParameters();
        String[] tables = new String[params.length -1];
        for (int i = 0; i < tables.length; i ++) {
            tables[i] = params[i+1];
        }
        client.updateTableList(tables);
    }
}
