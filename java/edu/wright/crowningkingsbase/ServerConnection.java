package edu.wright.crowningkingsbase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by csmith on 10/26/16.
 */

public class ServerConnection {
    private DataInputStream serverInputStream;
    private DataOutputStream serverOutputStream;


    public ServerConnection(String ipAddress, int portNumber) {
        try {
            Socket serverSocket = new Socket(ipAddress, portNumber);
            serverInputStream = new DataInputStream(serverSocket.getInputStream());
            serverOutputStream = new DataOutputStream(serverSocket.getOutputStream());
        } catch (IOException ioe) {
            //ioe exception handling
        }
    }



    private void sendServerMessage(ServerMessage message) {
        System.out.println("\tsendMessage()");
        try {
            byte[] serverMessage = message.getServerMessage();
            serverOutputStream.write(serverMessage, 0, serverMessage.length);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    private void getServerMessage() {
        try {
            byte[] rawMessage = new byte[512];
            serverInputStream.read(rawMessage);
            String inMessage = new String(rawMessage, "UTF-8");
            System.out.println("inMessage=\"" + inMessage + "\"");
        } catch (IOException ioe) {
            //ioe exception handling
        }
    }


}
