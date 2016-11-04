package edu.wright.crowningkings.base.ServerMessage;

/**
 * Created by csmith on 11/3/16.
 */

public class MakeTable extends AbstractServerMessage {
    public MakeTable() {
        String[] parameters = new String[1];
        parameters[0] = "103";
        setParameters(parameters);
    }

    public void run(){
        //do something
    }
}
