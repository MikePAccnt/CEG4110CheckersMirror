package edu.wright.crowningkings.base.ServerMessage;

/**
 * Created by jlathery on 11/14/16.
 */

public class Status extends AbstractServerMessage {
    public Status(String table) {
        String[] parameters = new String[2];
        parameters[0] = "109 ";
        parameters[1] = table ;
        setParameters(parameters);
    }

    public void run(){
        //do something
    }
}
