package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/16/16.
 */

public class _202NewTable extends AbstractServerMessage{
    public _202NewTable(String message) {
        setParameters(message.split(" "));
    }

    public void run(BaseClient client) {
		System.out.println("_202NewTable.run(BaseClient)");
		//do something
    }
}
