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
            /*
             * messages from the server to the client.
             */
            case 201 :
                System.out.println("\t201 : MSG – client received message <3> from client <1>. If <2> = 1, the message is private.");
                break;
            case 202 :
                System.out.println("\t202 : NEW_TBL – a new table has been created with id <1>.");
                break;
            case 203 :
                System.out.println("\t203 : GAME_START – the game being played at the table you are sitting at as a player has begun.");
                break;
            case 204 :
                System.out.println("\t204 : COLOR_BLACK – you are playing as black.");
                break;
            case 206 :
                System.out.println("\t206 : OPP_MOVE – your opponent moved a piece from <1> to <2>.");
                break;
            case 207 :
                System.out.println("\t207 : BOARD_STATE – the current board state on table <1> is <2>. <2> is a string encoding of the board");
                break;
            case 221 :
                System.out.println("\t221 : YOUR_TURN – it’s your turn to play.");
                break;
            case 210 :
                System.out.println("\t210 : TBL_JOINED – the client has joined table <1>.");
                break;
            case 212 :
                System.out.println("\t212 : WHO_IN_LOBBY – clients <1>, <2>, ..., <n> are in the lobby.");
                break;
            case 213 :
                System.out.println("\t213 : OUT_LOBBY – the client has left the lobby.");
                break;
            case 214 :
                System.out.println("\t214 : NOW_IN_LOBBY- client <1> has joined the lobby.");
                break;
            case 216 :
                System.out.println("\t216 : TBL_LIST – the current tables on the server are <1> <2>, ..., <n>. This only gives the table ids, not status.");
                break;
            case 217 :
                System.out.println("\t217 : NOW_LEFT_LOBBY – client <1> has left the lobby.");
                break;
            case 218 :
                System.out.println("\t218 : IN_LOBBY – the client has joined the game lobby.");
                break;
            case 219 :
                System.out.println("\t219 : WHO_ON_TBL – clients <2> and <3> are on table with id <1>. <2> is black. <3> is red. If <2> or <3> = -1, then that seat is open.");
                break;

            /*
             * messages indicating an error, from the server to the client.
             */
            case 405 :
                System.out.println("\t405 : BAD_MESSAGE – a TCP message sent to the server is in a bad format.");
                break;
            case 408 :
                System.out.println("\t408 : BAD_NAME – the username requested is bad (no whitespace allowed).");
                break;
            default:
                if (message.equals("Send username:")) {
                    System.out.println("\t000 : SEND_USERNAME – Send username to server");
                }
                else if (message.equals("ping")) {
                    System.out.println("000 : PING – server sent ping message");
                }
                else {
                    System.out.println("\tdefault messageCode");
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
