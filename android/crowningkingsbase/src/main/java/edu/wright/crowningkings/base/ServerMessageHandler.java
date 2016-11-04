package edu.wright.crowningkings.base;

import edu.wright.crowningkings.base.ServerMessage.*;

/**
 * Created by csmith on 10/28/16.
 *
 * This class acts as the controller between the client functionality, and sending/receiving
 * messages to/from the server.
 * The interpreter will take String messages from the server and translate them to ServerMessages.
 * These will make it easier to execute the functionality required for the message.
 * Preparing messages (for sending to the server) will also handled in this class.
 *
 */

public class ServerMessageHandler {

    public static AbstractServerMessage interpretMessage(String message) {
//        System.out.println("interpretMessage(String)");
//        System.out.println("message=" + message);
        String[] params = message.split(" ");
//        for (String param : params) {
//            System.out.println("\t\tparam=" + param);
//        }

        int messageCode = -1;
        try {
            messageCode = Integer.parseInt(params[0].trim());
        } catch (NumberFormatException nfe) {
//            System.out.println("\tNo code given");
        }
        System.out.println("");
        switch (messageCode) {
            case 201:
                System.out.println("\t201 : MSG – client received message <3> from client <1>. If <2> = 1, the message is private");
                break;
            case 214:
                System.out.println("\t214 : NOW_IN_LOBBY- client <1> has joined the lobby.");
                break;
            case 216:
                System.out.println("\t216 : TBL_LIST – the current tables on the server are <1> <2>, ..., <n>. This only gives the table ids, not status.");
                break;
            case 217:
                System.out.println("\t217 : NOW_LEFT_LOBBY – client <1> has left the lobby.");
                break;
            case 218:
                System.out.println("\t218 : IN_LOBBY – the client has joined the game lobby.");
                break;
            case 212:
                System.out.println("\t212 : WHO_IN_LOBBY – clients <1>, <2>, ..., <n> are in the lobby.");
                break;
            case 408:
                System.out.println("\t408 : BAD_NAME – the username requested is bad (no whitespace allowed).");
                break;
            default:
                System.out.println("\tdefault messageCode");
                if (message.equals("Send username:")) {
                    System.out.println("\tSendUsernamestuff");
                }
        }
        System.out.println("\tmessage=" + message);
//        System.out.println("Math.round(messageCode/100)=" + Math.round(messageCode/100));

        //return new ServerMessage(messageCode, params);
        return new TestMessage();
    }


//    //Work in progress
//    public static ServerMessage prepareMessage(int code, String ... parameters) {
//        switch (code) {
//            case 101:
//                System.out.println("\t101 : MSG_ALL – client <1> sends message <2> to everyone in the lobby.");
//                String message = "message";
//                MessageAll ma;
//                return new MessageAll(message);
//                //break;
//            case 102:
//                System.out.println("\t102 : MSG_C - client <1> sends message <3> to client <2>.");
//                break;
//            default:
//                System.out.println("\tdefault prepareMessage(int) code");
//        }
//        return null;
//    }

}
