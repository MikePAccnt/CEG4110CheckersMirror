package edu.wright.crowningkings.base.UserInterface;

import java.util.Scanner;

import edu.wright.crowningkings.base.UserInterface.AbstractUserInterface;

/**
 * Created by csmith on 11/4/16.
 */

public class CommandLineUI extends AbstractUserInterface {
    public String getUsernameFromUser() {
        System.out.println("Enter desired username with no spaces");
        return (new Scanner(System.in)).nextLine().replace(" ", "").trim();
    }

    @Override
    public String[] getPublicMessageFromUser() {
        System.out.println("Enter global chat message to send");
        return new String[] {(new Scanner(System.in)).nextLine().trim()};
    }

    @Override
    public String[] getPrivateMessageFromUser() {
        System.out.println("Enter recipient username");
        String recipient = (new Scanner(System.in)).nextLine().replace(" ", "").trim();
        System.out.println("Enter private chat message to send");
        String message = (new Scanner(System.in)).nextLine().trim();
        return new String[] {recipient, message};
    }

    @Override
    public String getTableIdFromUser() {
        System.out.println("Enter table ID");
        return (new Scanner(System.in)).nextLine().replace(" ", "").trim();
    }

    @Override
    public String[] getMoveFromUser() {
        System.out.println("Enter from x (0-7)");
        String fromx =  (new Scanner(System.in)).nextLine().replace(" ", "").trim();

        System.out.println("Enter from y (0-7)");
        String fromy =  (new Scanner(System.in)).nextLine().replace(" ", "").trim();

        System.out.println("Enter to x (0-7)");
        String tox =  (new Scanner(System.in)).nextLine().replace(" ", "").trim();

        System.out.println("Enter to y (0-7)");
        String toy =  (new Scanner(System.in)).nextLine().replace(" ", "").trim();

        return new String[] {fromx, fromy, tox, toy};
    }

    //From the updated AbstractUserInterface class added to allow the
    //Test client to still compile
    @Override
    public void sendWantTable(){}
    @Override
    public void makeTable(String tableID){}
    @Override
    public void makeTable(String[] tableID){}
    @Override
    public void sendJoinTable(){}
    @Override
    public void setJoinTable(String tableID, String type){}
    @Override
    public void sendPublicMessage(){}
    @Override
    public void sendPrivateMessage(){}
    @Override
    public void sendMoveToServer(){}
    @Override
    public void updateBoard(String[] board){}
    @Override
    public void updateLobbyChat(String newMessage){}
    @Override
    public void updateUsers(String newUser){}
    @Override
    public void updateError(String errorConst){}


}
