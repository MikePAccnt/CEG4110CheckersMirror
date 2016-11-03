package edu.wright.crowningkings.base;

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

public class ServerMessage {
    private static final String EOM = "<EOM>";
    private String[] parameters;
    private int messageCode;


//    public ServerMessage(String stringFromServer) {
//
//    }


    public ServerMessage(int messageCode, String ... parameters) {
//        System.out.println("\tServerMessage(int, String ...)");

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

    public void run() {
//        System.out.println("ServerMessage.run()");
    }
}
