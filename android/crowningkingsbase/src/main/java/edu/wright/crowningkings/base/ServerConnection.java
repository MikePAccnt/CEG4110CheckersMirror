package edu.wright.crowningkings.base;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import edu.wright.crowningkings.base.ServerMessage.AbstractServerMessage;

/**
 * Created by csmith on 10/26/16.
 *
 * This is responsible for reading messages from, and sending messages to the server.
 */

public class ServerConnection {
    private DataInputStream serverInputStream;
    private DataOutputStream serverOutputStream;


    public ServerConnection(String ipAddress, int portNumber) {
        System.out.println("\tServerConnection(String, int)");
        try {
            Socket checkersServerSocket = new Socket(ipAddress, portNumber);
            serverInputStream = new DataInputStream(checkersServerSocket.getInputStream());
            serverOutputStream = new DataOutputStream(checkersServerSocket.getOutputStream());

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage()+ ":_:");
        }
    }


    public void sendServerMessage(AbstractServerMessage message) {
        System.out.println("\tsendServerMessage(ServerMessage)");
        sendServerMessage(message.toBytesArray());
    }


//    public void sendServerMessage(String message) {
//        try{
//            //message = message + " " + ServerMessage.EOM;
//            sendServerMessage(message.getBytes("UTF-8"));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }


    private void sendServerMessage(byte[] bytes) {
        try {
            serverOutputStream.write(bytes, 0, bytes.length);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public String[] getServerMessageString() {
//        System.out.println("\tgetServerMessage()");
        String[] messages = null;
        try {
//            inMessage = serverInputStream.readUTF();
//            System.out.println("inMessage=\"" + inMessage + "\"");

            byte[] rawMessage = new byte[512];
            int numberOfBytes = serverInputStream.read(rawMessage);
//            System.out.println("\tnumberOfBytes=" + numberOfBytes);

            String inMessage = new String(rawMessage, 0, numberOfBytes, "UTF-8");
//            System.out.println("\tinMessage=\"" + inMessage + "\"");

            messages = inMessage.split("<EOM>");

        } catch (Exception e) {
            System.out.println(e.getMessage() + ":_:");
//            messages = new String[0];
        }

        return messages;
    }
}
