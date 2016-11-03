package edu.wright.crowningkings.base;

import java.util.Scanner;

/**
 * Created by csmith on 11/1/16.
 *
 * Right now the class name/design is still a prototype. As long as we can keep it out of using a
 * "public static void main(String[] args)" since Android doesn't really use that.
 *
 */

public class BaseClient {

    public void run(){
        System.out.println("BaseClient.run()");


        Scanner keyboard = new Scanner(System.in);
        String serverAddress = "192.168.122.1";
        int portNumber = 45322;
        final ServerConnection server;
        final UserInterface ui = new UserInterface();

//        System.out.println("Enter the server address");
//        serverAddress = keyboard.nextLine();

        server = new ServerConnection(serverAddress, portNumber);

        Thread getServerMessagesThread = new Thread() {
            public void run(){
//                System.out.println("\tgetServerMessagesThread.run()");
                while (true) {
//                    System.out.println("\tserver.getServerMessage();");
                    String[] messages = server.getServerMessageString();

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
                        ServerMessage sm = ServerMessageHandler.interpretMessage(stringMessage);
                        sm.run();
                    }
                }
            }
        };
        getServerMessagesThread.start();


        while(true) {
            System.out.println("\n\n\n\nWhat do you want to do?");
            System.out.println("[\"sendMessage\", \"setusername\", \"sendprivatemessage\" <--(Work in progress)]");
            String command = keyboard.nextLine();
            switch (command.toLowerCase()) {
                case "sendmessage" :
                    System.out.println("enter global chat message");
                    String messageToServer = keyboard.nextLine();
                    server.sendServerMessage(new ServerMessage(101, messageToServer));
                    break;
                case "setusername" :
                    String username = ui.getUsernameFromUser();
                    //ServerMessage sm = ServerMessageHandler.prepareMessage(0);
                    setUsername(username, server);
                    break;
                case "sendprivatemessage" :
                    System.out.println("enter global chat message");
                    String pmessageToServer = keyboard.nextLine();
                    System.out.println("to who?");
                    String to = keyboard.nextLine();
                    server.sendServerMessage(new ServerMessage(102, to, pmessageToServer));
                    break;
                default :
                    System.out.println("default switch");
                    break;
            }
        }
    }

    private static void setUsername(String username, ServerConnection server) {
        server.sendServerMessage(username);
    }
}
