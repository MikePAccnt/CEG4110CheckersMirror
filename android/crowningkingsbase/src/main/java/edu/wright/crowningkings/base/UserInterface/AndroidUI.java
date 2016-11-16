package edu.wright.crowningkings.base.UserInterface;

//import android.util.Log;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/10/16.
 */

public class AndroidUI implements AbstractUserInterface {

    @Override
    public String getUsernameFromUser() {
        //Log.d("AndroidUI", "bob");
        return "bob";
    }

    @Override
    public void sendWantTable() {

    }

    @Override
    public void makeTable(String tableID) {

    }

    @Override
    public void makeTable(String[] tableID) {

    }

    @Override
    public void sendJoinPlayTable(String tableID) {

    }

    @Override
    public void sendJoinObserveTable(String tableID) {

    }

    @Override
    public void setJoinPlayTable(String tableID, String oponent) {

    }

    @Override
    public void setJoinObserveTable(String tableID, String user1,String user2) {

    }

    @Override
    public void sendPublicMessage() {

    }

    @Override
    public void sendPrivateMessage() {

    }

    @Override
    public String[] getPublicMessageFromUser() {
        return new String[] {"Hi everyone"};
    }

    @Override
    public String[] getPrivateMessageFromUser() {
        return new String[]{"john", "hi john!"};
    }

    @Override
    public String getTableIdFromUser() {
        return "1234";
    }

    @Override
    public void sendMoveToServer() {

    }

    @Override
    public void updateBoard(String[][] board) {

    }

    @Override
    public void updateLobbyChat(String newMessage) {

    }

    @Override
    public void addUser(String newUser) {

    }

    @Override
    public void removeUser(String oldUser) {

    }

    @Override
    public void updateError(String errorConst) {

    }

    @Override
    public String[] getMoveFromUser() {
        return new String[] {"1", "2", "3", "4"};
    }
}
