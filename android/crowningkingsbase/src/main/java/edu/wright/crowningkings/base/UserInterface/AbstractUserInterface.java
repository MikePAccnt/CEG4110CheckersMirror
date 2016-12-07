package edu.wright.crowningkings.base.UserInterface;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/1/16. Updated by mpurcell on 11/13/16
 *
 * Hopefully in the future this will distinguish between using the Android and Desktop UI...
 *
 */

public interface AbstractUserInterface {

    void sendUsernameRequest();

    void connectionOK();

    void message(String message, String from, boolean privateMessage);

    void newtable(String tableID);

    void gameStart();

    void colorBlack();

    void colorRed();

    void opponentMove(String[] from, String[] to);

    void boardState(String boardState);

    void gameWon();

    void gameLose();

    void tableJoined(String tableID);

    void whoInLobby(String[] users);

    void outLobby();

    void nowInLobby(String user);

    void tableList(String[] tableIDs);

    void nowLeftLobby(String user);

    void inLobby();

    void whoOnTable(String userOne, String userTwo,String tableID, String userOneColor,String userTwoColor);

    void opponentLeftTable();

    void yourTurn();

    void tableLeft(String tableID);

    void nowObserving(String tableID);

    void stoppedObserving(String tableID);

    void netException();

    void nameInUse();

    void illegalMove();

    void tblFull();

    void notInLobby();

    void badMessage();

    void errorLobby();

    void badName();

    void playerNotReady();

    void notYourTurn();

    void tableNotExist();

    void gameNotCreated();

    void notObserving();
}
