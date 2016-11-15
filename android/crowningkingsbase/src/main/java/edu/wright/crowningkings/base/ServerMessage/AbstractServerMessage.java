package edu.wright.crowningkings.base.ServerMessage;

import java.io.UnsupportedEncodingException;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 10/26/16.
 *
 * This is where we will create a message by the "Server Message Handling" package from project 2.
 *
 * When we want to send a message to the server, we want to call a method that returns a formatted
 * String that will be translated into a byte[] for sending to the server. the ServerMessage class
 * will be the parent of many different classes of messages
 *
 * Each extension of this AbstractServerMessage class will override the run method. In this method,
 * the message will perform its unique task (change client state, call ui to change view, etc.
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
        } catch (UnsupportedEncodingException uee) {
            System.out.println("toBytesArray UnsupportedEncodingException : " + uee.getMessage());
            return new byte[0];
        }
    }

    protected void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    protected String[] getParameters() {
        return parameters;
    }

    public abstract void run(BaseClient client);
}