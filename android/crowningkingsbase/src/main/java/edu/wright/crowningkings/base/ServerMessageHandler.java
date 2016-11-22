package edu.wright.crowningkings.base;

import edu.wright.crowningkings.base.ServerMessage.*;

/**
 * Created by csmith on 10/28/16.
 *
 * This class acts as an interpreter for the string message from the server.
 * The interpreter will take String messages from the server and translate them to ServerMessages.
 * These will make it easier to execute the functionality required for the message.
 *
 */

public class ServerMessageHandler {

    public static AbstractServerMessage interpretMessage(String message) {
        String[] params = message.split(" ");

        int messageCode = -1;
        try {
            messageCode = Integer.parseInt(params[0].trim());
        } catch (NumberFormatException nfe) {
            System.out.println("interpretMessage NumberFormatException : " + nfe.getMessage());
        }
        System.out.println("");
        System.out.println("message=" + message);
        switch (messageCode) {
            /**
             * messages from the server to the client.
              */
            case 201 :
                System.out.println("\t201 : MSG – client received message <3> from client <1>. If <2> = 1, the message is private.");
                return new _201MessageRecieved(message);
            case 202 :
                System.out.println("\t202 : NEW_TBL – a new table has been created with id <1>.");
                return new _202NewTable(message);
            case 203 :
                System.out.println("\t203 : GAME_START – the game being played at the table you are sitting at as a player has begun.");
                return new _203GameStart(message);
            case 204 :
                System.out.println("\t204 : COLOR_BLACK – you are playing as black.");
               return new _204ColorBlack(message);


            case 206 :
                System.out.println("\t206 : OPP_MOVE – your opponent moved a piece from <1> to <2>.");
                return new _206OpponentMove(message);
            case 207 :
                System.out.println("\t207 : BOARD_STATE – the current board state on table <1> is <2>. <2> is a string encoding of the board");
                return new _207BoardState(message);






            case 210 :
                System.out.println("\t210 : TBL_JOINED – the client has joined table <1>.");
                return new _210TableJoined(message);



            case 212 :
                System.out.println("\t212 : WHO_IN_LOBBY – clients <1>, <2>, ..., <n> are in the lobby.");
                return new _212WhoInLobby(message);
            case 213 :
                System.out.println("\t213 : OUT_LOBBY – the client has left the lobby.");
                return new _213LeftLobby(message);
            case 214 :
                System.out.println("\t214 : NOW_IN_LOBBY- client <1> has joined the lobby.");
                return new _214NowInLobby(message);



            case 216 :
                System.out.println("\t216 : TBL_LIST – the current tables on the server are <1> <2>, ..., <n>. This only gives the table ids, not status.");
                return new _216TableList(message);
            case 217 :
                System.out.println("\t217 : NOW_LEFT_LOBBY – client <1> has left the lobby.");
                return new _217NowLeftLobby(message);
            case 218 :
                System.out.println("\t218 : IN_LOBBY – the client has joined the game lobby.");
                return new _218InLobby(message);
            case 219 :
                System.out.println("\t219 : WHO_ON_TBL – clients <2> and <3> are on table with id <1>. <2> is black. <3> is red. If <2> or <3> = -1, then that seat is open.");
                return new _219WhoOnTable(message);



            case 221 :
                System.out.println("\t221 : YOUR_TURN – it’s your turn to play.");
                return new _221YourTurn(message);


            /**
             * messages indicating an error, from the server to the client.
             */
            case 405 :
                System.out.println("\t405 : BAD_MESSAGE – a TCP message sent to the server is in a bad format.");
                return new _405BadMessage(message);



            case 408 :
                System.out.println("\t408 : BAD_NAME – the username requested is bad (no whitespace allowed).");
                return new _408BadName(message);




            default:
                if (message.equals("Send username:")) {
                    System.out.println("\t000 : SEND_USERNAME – Send username to server");
                    return new SendUsernameRequest(message);
                }
                else if (message.equals("ping")) {
                    System.out.println("\t000 : PING – server sent ping message");
                    return new TestMessage();
                }
                else {
                    System.out.println("\tdefault messageCode");
                    return new TestMessage();
                }
        }
    }
}
