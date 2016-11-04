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

//    public ServerMessage() {}

//    public ServerMessage(int messageCode) {
//        this.messageCode = messageCode;
//    }

//    public ServerMessage(int messageCode, String[] parameters) {
////        System.out.println("\tServerMessage(int, String ...)");
//
//        this.messageCode = messageCode;
//        this.parameters = parameters;
//    }


    public String toServerMessage() {
        String message = "";
        for (String param : parameters) {
            System.out.println("\t\t\tparam=" + param);
            message = message + " " + param;
        }
        return message + " " + EOM;
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

    public String getMessageCode() {
        return parameters[0];
    }

    public abstract void run();// {
//        System.out.println("ServerMessage.run()");
  //  }
}