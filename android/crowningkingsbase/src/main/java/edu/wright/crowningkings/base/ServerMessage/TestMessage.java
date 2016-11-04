package edu.wright.crowningkings.base.ServerMessage;

/**
 * Created by csmith on 11/3/16.
 */

public class TestMessage extends AbstractServerMessage {
    public TestMessage() {
        String[] parameters = new String[1];
        parameters[0] = "000";
        setParameters(parameters);
    }

    public void run(){
        System.out.println("\tTestMessage.run()");
        //do something
    }
}
