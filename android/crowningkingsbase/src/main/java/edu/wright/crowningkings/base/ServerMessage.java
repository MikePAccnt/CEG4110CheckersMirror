package edu.wright.crowningkings.base;

/**
 * Created by csmith on 10/26/16.
 */

import java.security.Policy;

/**
 * This is where we will create a message by the "Server Message Handling" package from project 2.
 *
 * When we want to send a message to the server, we want to call toString() and use that string to
 * send to the server. This is obviously not implemented yet, but this is my vision for the future.
 */
public class ServerMessage {
    public static final String EOM = "<EOM>";
    private String[] parameters;
    private int messageCode;


    public ServerMessage(String stringFromServer) {
        System.out.println("\tServerMessage(String)");
        String[] parameters = stringFromServer.split("<EOM>");
        for (String parameter : parameters) {
            System.out.println("\t" + parameter);
        }
        try {
            messageCode = Integer.parseInt(parameters[0]);
        } catch (NumberFormatException nfe) {
            System.out.println("No code given");
            messageCode = -1;
        }
    }


    public ServerMessage(int messageCode, String ... parameters) {
        System.out.println("\tServerMessage(int, String ...)");

        this.messageCode = messageCode;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        String message = null;
        for (String param : parameters) {
            message = message + " " + param;
        }
        return messageCode + " " + message + " " + EOM;
    }


    public byte[] toBytesArray() {
        try {
            return this.toString().getBytes("UTF-8");
        } catch (java.io.UnsupportedEncodingException uee) {
            System.out.println(uee.getMessage());
            return new byte[0];
        }
    }


    public int getMessageCode(){
        return messageCode;
    }
}
