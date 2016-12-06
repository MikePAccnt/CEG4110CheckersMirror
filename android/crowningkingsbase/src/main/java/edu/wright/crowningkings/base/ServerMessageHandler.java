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

        System.out.println("\n\tmessage=" + message);
        switch (params[0].trim()) {
            /**
             * messages from the server to the client.
              */
            case "201" :
                System.out.println("\t201 : MSG – client received message <3> from client <1>. If <2> = 1, the message is private.");
                return new _201MessageRecieved(message);
            case "202" :
                System.out.println("\t202 : NEW_TBL – a new table has been created with id <1>.");
                return new _202NewTable(message);
            case "203" :
                System.out.println("\t203 : GAME_START – the game being played at the table you are sitting at as a player has begun.");
                return new _203GameStart(message);
            case "204" :
                System.out.println("\t204 : COLOR_BLACK – you are playing as black.");
               return new _204ColorBlack(message);
            case "205" :
                System.out.println("\t205 : COLOR_RED - you are playing as red.");
               return new _205ColorRed(message);
            case "206" :
                System.out.println("\t206 : OPP_MOVE – your opponent moved a piece from <1> to <2>.");
                return new _206OpponentMove(message);
            case "207" :
                System.out.println("\t207 : BOARD_STATE – the current board state on table <1> is <2>. <2> is a string encoding of the board");
                return new _207BoardState(message);
            case "208" :
                System.out.println("\t208 : GAME_WIN - the client has won!");
                return new _208GameWin(message);
            case "209" :
                System.out.println("\t209 : GAME_LOSE - the client has lost their game.");
                return new _209GameLose(message);
            case "210" :
                System.out.println("\t210 : TBL_JOINED – the client has joined table <1>.");
                return new _210TableJoined(message);



            case "212" :
                System.out.println("\t212 : WHO_IN_LOBBY – clients <1>, <2>, ..., <n> are in the lobby.");
                return new _212WhoInLobby(message);
            case "213" :
                System.out.println("\t213 : OUT_LOBBY – the client has left the lobby.");
                return new _213LeftLobby(message);
            case "214" :
                System.out.println("\t214 : NOW_IN_LOBBY- client <1> has joined the lobby.");
                return new _214NowInLobby(message);



            case "216" :
                System.out.println("\t216 : TBL_LIST – the current tables on the server are <1> <2>, ..., <n>. This only gives the table ids, not status.");
                return new _216TableList(message);
            case "217" :
                System.out.println("\t217 : NOW_LEFT_LOBBY – client <1> has left the lobby.");
                return new _217NowLeftLobby(message);
            case "218" :
                System.out.println("\t218 : IN_LOBBY – the client has joined the game lobby.");
                return new _218InLobby(message);
            case "219" :
                System.out.println("\t219 : WHO_ON_TBL – clients <2> and <3> are on table with id <1>. <2> is black. <3> is red. If <2> or <3> = -1, then that seat is open.");
                return new _219WhoOnTable(message);
            case "220" :
                System.out.println("\t220 : OPP_LEFT_TABLE - Your opponent has left the table.");
                return new _220OpponentLeft(message);


            case "221" :
                System.out.println("\t221 : YOUR_TURN – it’s your turn to play.");
                return new _221YourTurn(message);
            case "222" :
                System.out.println("\t222 : TBL_LEFT – Client has left the table <1>.");
                return new _222TableLeft(message);
            case "230" :
                System.out.println("\t230 : NOW_OBSERVING–you are now observing table <1>");
                return new _230NowObserving(message);
            case "235" :
                System.out.println("\t235 : STOPPED_OBSERVING–you stopped observer table <1>");
                return new _235StoppedObserving(message);

            /**
             * messages indicating an error, from the server to the client.
             */
            case "400" :
                System.out.println("\t400 : NET_EXCEPTION– a network exception has occurred. See server console for details");
                return new _400NetException(message);
            case "401" :
                System.out.println("\t401 : NAME_IN_USE - the client name requested is already in use on the server");
                return new _401NameInUse(message);
            case "402" :
                System.out.println("\t402 : ILLEGAL	–	the	move	sent	is	illegal.");
                return new _402Illegal(message);
            case "403" :
                System.out.println("\t403 : TBL_FULL	–	the	table	the	client	tried	to	join	is	full.");
                return new _403TableFull(message);
            case "404" :
                System.out.println("\t404 : NOT_IN_LOBBY - you	tried	to	join	a	table,	but	you	are	not	in	the	lobby.");
                return new _404NotInLobby(message);
            case "405" :
                System.out.println("\t405 : BAD_MESSAGE – a TCP message sent to the server is in a bad format.");
                return new _405BadMessage(message);
            case "406" :
                System.out.println("\t406 : ERR_IN_LOBBY	-	the	action	requested	cannot	be	completed	because	you	are	not	in	the
lobby.");
                return new _406ErrorInLobby(message);
            case "408" :
                System.out.println("\t408 : BAD_NAME – the username requested is bad (no whitespace allowed).");
                return new _408BadName(message);
            case "409" :
                System.out.println("\t409 : PLAYERS_NOT_READY - client	sent	a	move	but	the	game	hasn’t	begun	yet.");
                return new _409PlayersNotReady(message);
            case "410" :
                System.out.println("\t410 : NOT_YOUR_TURN	–	client	sent	a	move	but	it	is	not	their	turn	to	move.");
                return new _410NotYourTurn(message);
            case "411" :
                System.out.println("\t411 : TBL_NOT_EXIST	–	table	queried	does	not	exist.");
                return new _411TableNotExist(message);
            case "412" :
                System.out.println("\t412 : GAME_NOT_CREATED	–	client	said	they	are	ready,	but	there	is	no	game	on	the	table	(2
players	are	not	sitting	on	the	table).");
                return new _412GameNotCreated(message);
            case "415" :
                System.out.println("\t415 : NOT_OBSERVING	–	the	client	is	not	observing	a	table.");
                return new _415NotObserving(message);





            default:
                if (message.equals("Send username:")) {
                    System.out.println("\t000 : SEND_USERNAME – Send username to server");
                    return new SendUsernameRequest(message);
                }
                else if (message.equals("ping")) {
                    System.out.println("\t001 : PING – server sent ping message");
                    return new TestMessage();
                }
                else {
                    System.out.println("\tunknown message");
                    return new TestMessage();
                }
        }
    }
}
