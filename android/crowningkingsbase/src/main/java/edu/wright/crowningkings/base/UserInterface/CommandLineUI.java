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
                    client.messageAll(getPublicMessageFromUser()[0]);
                    break;
                case "setusername" :
                    String username = getUsernameFromUser();
                    client.setUsername(username);
                    break;
                case "sendprivatemessage" :
                    String[] msg = getPrivateMessageFromUser();
                    client.messageClient(msg[0], msg[1]);
                    break;
                case "quit" :
                    client.quit();
                    quit = true;
                    break;
                case "maketable" :
                    client.makeTable();
                    break;
                case "jointable" :
                    client.joinTable(getTableIdFromUser());
                    break;
                case "leavetable" :
                    client.leaveTable();
                    break;
                case "ready" :
                    client.ready();
                    break;
                case "move" :
                    String[] move = getMoveFromUser();
                    client.move(move[0], move[1], move[2], move[3]);
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

    public String[] getPublicMessageFromUser() {
        System.out.println("Enter global chat message to send");
        return new String[] {(new Scanner(System.in)).nextLine().trim()};
    }

    public String[] getPrivateMessageFromUser() {
        System.out.println("Enter recipient username");
        String recipient = (new Scanner(System.in)).nextLine().replace(" ", "").trim();
        System.out.println("Enter private chat message to send");
        String message = (new Scanner(System.in)).nextLine().trim();
        return new String[] {recipient, message};
    }

    public String getTableIdFromUser() {
        System.out.println("Enter table ID");
        return (new Scanner(System.in)).nextLine().replace(" ", "").trim();
    }

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
    public void sendUsernameRequest() {

    }

    @Override
    public void connectionOK() {

    }

    @Override
    public void message(String message, String from, boolean privateMessage) {

    }

    @Override
    public void newtable(String tableID){
        System.out.println("New Table: " + tableID);
    }

    @Override
    public void gameStart() {

    }

    @Override
    public void colorBlack() {

    }

    @Override
    public void colorRed() {

    }

    @Override
    public void opponentMove(String[] from, String[] to) {

    }

    @Override
    public void boardState(String[][] boardState) {

    }

    @Override
    public void gameWon() {

    }

    @Override
    public void gameLose() {

    }

    @Override
    public void tableJoined(String tableID) {

    }

    @Override
    public void whoInLobby(String[] users) {

    }

    @Override
    public void outLobby() {

    }

    @Override
    public void nowInLobby(String user) {

    }

    @Override
    public void tableList(String[] tableID){
        System.out.println("");
        System.out.println("TABLES:");
        for (String table : tableID){
            System.out.println(table);
        }
    }

    @Override
    public void nowLeftLobby(String user) {

    }

    @Override
    public void inLobby() {

    }

    @Override
    public void whoOnTable(String userOne, String userTwo, String tableID, String userOneColor, String userTwoColor) {

    }

    @Override
    public void opponentLeftTable() {

    }

    @Override
    public void yourTurn() {

    }

    @Override
    public void tableLeft(String tableID) {

    }

    @Override
    public void nowObserving(String tableID) {

    }

    @Override
    public void stoppedObserving(String tableID) {

    }

    public void netException(){}

    public void nameInUse(){}

    public void illegalMove(){}

    public void tblFull(){}

    public void notInLobby(){}

    public void badMessage(){}

    public void errorLobby(){}

    public void badName(){}

    public void playerNotReady(){}

    public void notYourTurn(){}

    public void tableNotExist(){}

    public void gameNotCreated(){}

    public void notObserving(){}
}
