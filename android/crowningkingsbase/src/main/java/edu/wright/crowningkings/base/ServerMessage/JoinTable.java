package edu.wright.crowningkings.base.ServerMessage;

/**
 * Created by csmith on 11/3/16.
 */

public class JoinTable extends AbstractServerMessage {
    public JoinTable(String tableId) {
        String[] parameters = new String[2];
        parameters[0] = "104 ";
        parameters[1] = tableId;
        setParameters(parameters);
    }

    public void run(){
        //do something
    }
}


