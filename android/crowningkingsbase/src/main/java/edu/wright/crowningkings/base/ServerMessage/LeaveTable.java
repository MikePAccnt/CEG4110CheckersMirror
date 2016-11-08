package edu.wright.crowningkings.base.ServerMessage;

/**
 * Created by csmith on 11/8/16.
 */

public class LeaveTable extends AbstractServerMessage {
    public LeaveTable() {
        String[] parameters = new String[1];
        parameters[0] = "107 ";
        setParameters(parameters);
    }

    public void run(){
        //do something
    }
}
