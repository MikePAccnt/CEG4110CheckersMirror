package edu.wright.crowningkings.base.ServerMessage;

/**
 * Created by csmith on 10/26/16.
 *
 * This is where we will create a message by the "Server Message Handling" package from project 2.
 *
 * When we want to send a message to the server, we want to call a method that returns a formatted
 * String that will be translated into a byte[] for sending to the server. the ServerMessage class
 * will be the parent of many different classes of messages
 *
 */

public abstract class AbstractServerMessage {
    //protected int messageCode;
    private String[] parameters;
    private final String EOM = "<EOM>";


    public String toServerMessage() {
        String message = "";
        for (String param : parameters) {
            message = message + param + " " ;
        }
        message = message + EOM;
        System.out.println("\ttoServerMessage() message=" + message);
        return message;
    }


    public byte[] toBytesArray() {
        try {
            return this.toServerMessage().getBytes("UTF-8");
        } catch (java.io.UnsupportedEncodingException uee) {
            System.out.println(uee.getMessage());
            return new byte[0];
        }
    }

    protected void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    protected String[] getParameters() {
        return parameters;
    }

    public abstract void run();
}