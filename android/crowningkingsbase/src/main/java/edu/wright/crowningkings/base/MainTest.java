package edu.wright.crowningkings.base;

import java.io.DataOutputStream;
import java.util.Scanner;

public class MainTest {
    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);
        String userName;
        String serverAddress = "192.168.122.1";
        int portNumber = 45322;
        final ServerConnection server;

        //System.out.println("Enter desired username with no spaces");
        //userName = keyboard.nextLine();
        //System.out.println("Enter the server address");
        //serverAddress = keyboard.nextLine();
        //System.out.println("Enter the port number");
        //portNumber = keyboard.nextInt();
        //System.out.println("Attempting to connect to the server " + serverAddress + " on port " + portNumber);
//        try {
        //Socket echoSocket = new Socket(serverAddress, portNumber); //Open connection
        //PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true); //Open writerstream

        //DataInputStream inFromServer = new DataInputStream(echoSocket.getInputStream());
        //DataOutputStream outToServer = new DataOutputStream(echoSocket.getOutputStream());

        server = new ServerConnection(serverAddress, portNumber);

        Thread getServerMessagesThread = new Thread() {
            public void run(){
                System.out.println("\trun()");
                server.getServerMessage();
            }
        };
        getServerMessagesThread.run();



        boolean wait = false;
        while(true) {
            if (!wait) {
                System.out.println("\nWhat do you want to do?");
                System.out.println("[wait, sendMessage, setusername]");
                String command = keyboard.nextLine();
                switch (command.toLowerCase()) {
                    case "wait" :
                        wait = true;
                        break;
                    case "sendmessage" :
                        System.out.println("enter global chat message");
                        String messageToServer = keyboard.nextLine();
                        server.sendServerMessage(new ServerMessage(101, messageToServer));
                        break;
                    case "setusername" :
                        System.out.println("Enter desired username with no spaces");
                        String username = keyboard.nextLine();
                        server.sendServerMessage(new ServerMessage(0, username));
                        break;
                    default :
                        System.out.println("default switch");
                        break;
                }
            }
        }
    }


    public static void sendMessage(DataOutputStream out){
        System.out.println("\tsendMessage()");
        try {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("enter global chat message");
            String messageToServer = keyboard.nextLine();
            messageToServer = "101  " + messageToServer + "<EOM>";
            byte[] toServerByteArray = new byte[512];
            toServerByteArray = messageToServer.getBytes("UTF-8");
            out.write(toServerByteArray, 0, toServerByteArray.length);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    private static void setUsername(DataOutputStream out) {
        System.out.println("\tsetUsername()");
        try {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter desired username with no spaces");
            String messageToServer = keyboard.nextLine();
            byte[] toServerByteArray = new byte[512];
            toServerByteArray = messageToServer.getBytes("UTF-8");
            out.write(toServerByteArray, 0, toServerByteArray.length);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
