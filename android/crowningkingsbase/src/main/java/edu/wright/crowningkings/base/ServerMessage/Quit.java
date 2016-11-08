package edu.wright.crowningkings.base.ServerMessage;

/**
 * Created by csmith on 11/3/16.
 */

public class Quit extends AbstractServerMessage {
    public Quit() {
        String[] parameters = new String[1];
        parameters[0] = "108 ";
        setParameters(parameters);
    }

    public void run(){
        //do something
    }
}
