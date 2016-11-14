package edu.wright.crowningkings.base;

import edu.wright.crowningkings.base.ServerMessage.*;
import edu.wright.crowningkings.base.UserInterface.*;
import edu.wright.crowningkings.base.UserInterface.CommandLineUI;

import java.util.Scanner;

/**
 * Created by csmith on 11/1/16.
 *
 * Right now the class name/design is still a prototype. As long as we can keep it out of using a
 * "public static void main(String[] args)" since Android doesn't really use that.
 *
 */

public class BaseClient {
    ServerConnection server;
    AbstractUserInterface ui;
    private final int PORT_NUMBER = 45322;

    public void run(){
        System.out.println("BaseClient.run()");


        Scanner keyboard = new Scanner(System.in);
        String serverAddress = "130.108.13.36";

//        System.out.println("Enter the server address");
//        serverAddress = keyboard.nextLine();

        server = new ServerConnection(serverAddress, PORT_NUMBER);
        ui = new CommandLineUI();


        Thread getServerMessagesThread = new Thread() {
            public void run(){
                while (!Thread.interrupted()) {
                    String[] messages = server.getServerMessageString();
                    if (messages != null) {
                        for (String stringMessage : messages) {
                            AbstractServerMessage sm = ServerMessageHandler.interpretMessage(stringMessage);
                            try {
                                sm.run();
                            } catch (NullPointerException npe) {
                                System.out.println("run NullPointerException : " + npe.getMessage());
                            }
                        }
                    }
                }
            }
        };
        getServerMessagesThread.start();


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
                    "\"tablestatus\", " +
                    "]");
            String command = keyboard.nextLine();
            switch (command.toLowerCase()) {
                case "sendpublicmessage" :
                    sendPublicMessage();
                    break;
                case "setusername" :
                    setUsername();
                    break;
                case "sendprivatemessage" :
                    sendPrivateMessage();
                    break;
                case "quit" :
                    quitServer();
                    quit = true;
                    break;
                case "maketable" :
                    makeTable();
                    break;
                case "jointable" :
                    joinTable();
                    break;
                case "leavetable" :
                    leaveTable();
                    break;
                case "ready" :
                    ready();
                    break;
                case "move" :
                    move();
                    break;
                case "tablestatus" :
                    status();
                    break;
                default :
                    System.out.println("default switch");
                    break;
            }
        }
        getServerMessagesThread.interrupt();
    }


    private void status() {
        String table = ui.getTableIdFromUser();
        server.sendServerMessage(new Status(table));
    }

    private void setUsername() {
        String username = ui.getUsernameFromUser();
        server.sendServerMessage(new SendUsername(username));
    }


    private void sendPublicMessage() {
        String[] publicMessage = ui.getPublicMessageFromUser();
        server.sendServerMessage(new MessageAll(publicMessage[0]));
    }


    private void sendPrivateMessage() {
        String[] privateMessageArray = ui.getPrivateMessageFromUser();
        String recipient = privateMessageArray[0];
        String privateMessage = privateMessageArray[1];
        server.sendServerMessage(new MessageClient(privateMessage, recipient));
    }


    private void quitServer() {
        server.sendServerMessage(new Quit());
    }


    private void makeTable() {
        server.sendServerMessage(new MakeTable());
    }


    private void joinTable() {
        String tableId = ui.getTableIdFromUser();
        server.sendServerMessage(new JoinTable(tableId));
    }


    private void leaveTable() {
        server.sendServerMessage(new LeaveTable());
    }


    private void ready() {
        server.sendServerMessage(new Ready());
    }


    private void move() {
        String[] move = ui.getMoveFromUser();
        server.sendServerMessage(new Move(move[0], move[1], move[2], move[3]));
    }
}
