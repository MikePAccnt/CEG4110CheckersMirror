package edu.wright.crowningkings.base.UserInterface;

import java.util.Scanner;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/4/16.
 */

public class CommandLineUI implements AbstractUserInterface {
    BaseClient client;
    public void run() {
        client = new BaseClient(this);

        Scanner keyboard = new Scanner(System.in);

        boolean quit = false;
        while(!quit) {
            System.out.println("\n\n\n\nWhat do you want to do?");
            System.out.println("[" +
                    "\"setusername\", " +
                    "\"sendpublicmessage\", " +
                    "\"sendprivatemessage\", " +
                    "\"quit\", " +
                    "\"maketable\", " +
                    "\"jointable\", " +
                    "\"leavetable\", " +
                    "\"ready\", " +
                    "\"move\", " +
                    "]");
            String command = keyboard.nextLine();
            switch (command.toLowerCase()) {
                case "sendpublicmessage" :
                    client.sendPublicMessage();
                    break;
                case "setusername" :
                    client.setUsername();
                    break;
                case "sendprivatemessage" :
                    client.sendPrivateMessage();
                    break;
                case "quit" :
                    client.quit();
                    quit = true;
                    break;
                case "maketable" :
                    client.makeTable();
                    break;
                case "jointable" :
                    client.joinTable();
                    break;
                case "leavetable" :
                    client.leaveTable();
                    break;
                case "ready" :
                    client.ready();
                    break;
                case "move" :
                    client.move();
                    break;
                default :
                    System.out.println("default switch");
                    break;
            }
        }
    }


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
    public void makeTable(String tableID){
        System.out.println("New Table: " + tableID);
    }
    @Override
    public void makeTable(String[] tableID){
        System.out.println("");
        System.out.println("TABLES:");
        for (String table : tableID){
            System.out.println(table);
        }
    }
    @Override
    public void sendJoinPlayTable(String tableID){}
    @Override
    public void sendJoinObserveTable(String tableID){}
    @Override
    public void setJoinPlayTable(String tableID, String oponent){}
    @Override
    public void setJoinObserveTable(String tableID, String user1,String user2){}
    @Override
    public void sendPublicMessage(){}
    @Override
    public void sendPrivateMessage(){}
    @Override
    public void sendMoveToServer(){}
    @Override
    public void updateBoard(String[][] board){}
    @Override
    public void updateLobbyChat(String newMessage){}
    @Override
    public void addUser(String newUser){}
    @Override
    public void removeUser(String oldUser){}    
    @Override
    public void updateError(String errorConst){}
}
