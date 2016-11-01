package edu.wright.crowningkings.base;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by csmith on 10/26/16.
 */

public class ServerConnection {
    private DataInputStream serverInputStream;
    private DataOutputStream serverOutputStream;


    public ServerConnection(String ipAddress, int portNumber) {
        System.out.println("\tServerConnection(String, int)");
        try {
            Socket serverSocket = new Socket(ipAddress, portNumber);
            //ServerSocket ss = new ServerSocket(portNumber);
            serverInputStream = new DataInputStream(serverSocket.getInputStream());
            serverOutputStream = new DataOutputStream(serverSocket.getOutputStream());

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage()+ ":_:");
        }
    }


    public void sendServerMessage(ServerMessage message) {
        System.out.println("\tsendServerMessage(ServerMessage)");
        sendServerMessage(message.toBytesArray());
    }


    private void sendServerMessage(String message) {
        try{
            //message = message + " " + ServerMessage.EOM;
            sendServerMessage(message.getBytes("UTF-8"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private void sendServerMessage(byte[] bytes) {
        try {
            serverOutputStream.write(bytes, 0, bytes.length);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void getServerMessage() {
        System.out.println("\tgetServerMessage()");
        try {
            byte[] rawMessage = new byte[512];
            serverInputStream.read(rawMessage);
            String inMessage = new String(rawMessage, "UTF-8");
            System.out.println("inMessage=\"" + inMessage + "\"");

            ServerMessage sm = new ServerMessage(inMessage);
            ServerMessageHandler.interpretMessage(sm);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage() + ":_:");
        }
    }



}
