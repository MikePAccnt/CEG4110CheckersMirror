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
        String serverAddress = "192.168.122.1";

//        System.out.println("Enter the server address");
//        serverAddress = keyboard.nextLine();

        server = new ServerConnection(serverAddress, PORT_NUMBER);
        ui = new CommandLineUI();


        Thread getServerMessagesThread = new Thread() {
            public void run(){
//                System.out.println("\tgetServerMessagesThread.run()");
                while (!Thread.interrupted()) {
//                    System.out.println("\tserver.getServerMessage();");
                    String[] messages = server.getServerMessageString();
                    if (messages != null) {
                        for (String stringMessage : messages) {
//                        System.out.println("\n\tstringMessage=" + stringMessage);
//
//                        String[] params = stringMessage.split(" ");
//                        for (String param : params) {
//                            System.out.println("\t\tparam=" + param);
//                        }
//
//                        int messageCode;
//                        try {
//                            messageCode = Integer.parseInt(params[0].trim());
//                        } catch (NumberFormatException nfe) {
//                            System.out.println("\tNo code given");
//                            messageCode = -1;
//                        }
//
//                      ServerMessage sm = new ServerMessage(messageCode, stringMessage);
//                      ServerMessageHandler.interpretMessage(sm);
                            AbstractServerMessage sm = ServerMessageHandler.interpretMessage(stringMessage);
                            try {
                                sm.run();
                            } catch (java.lang.NullPointerException npe) {
                                System.out.println("\tnpe: " + npe.getMessage());
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
                default :
                    System.out.println("default switch");
                    break;
            }
        }
        getServerMessagesThread.interrupt();
    }


    private void setUsername() {
        String username = ui.getUsernameFromUser();
        server.sendServerMessage(new SendUsername(username));
    }


    private void sendPublicMessage() {
        String publicMessage = ui.getMessageFromUser();
        server.sendServerMessage(new MessageAll(publicMessage));
    }


    private void sendPrivateMessage() {
        String recipient = ui.getRecipientFromUser();
        String privateMessage = ui.getMessageFromUser();
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
