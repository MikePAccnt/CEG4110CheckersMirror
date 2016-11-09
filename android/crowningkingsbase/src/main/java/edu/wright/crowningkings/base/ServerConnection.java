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
        try {
            Socket checkersServerSocket = new Socket(ipAddress, portNumber);
            serverInputStream = new DataInputStream(checkersServerSocket.getInputStream());
            serverOutputStream = new DataOutputStream(checkersServerSocket.getOutputStream());

        } catch (IOException ioe) {
            System.out.println("ServerConnection IOException : " + ioe.getMessage());
        }
    }


    public void sendServerMessage(AbstractServerMessage message) {
        sendServerMessage(message.toBytesArray());
    }


    private void sendServerMessage(byte[] bytes) {
        try {
            serverOutputStream.write(bytes, 0, bytes.length);
        } catch (Exception e) {
            System.out.println("sendServerMessage Exception : " + e.getMessage());
        }
    }


    public String[] getServerMessageString() {
        String[] messages = new String[0];
        try {
            byte[] rawMessage = new byte[512];
            int numberOfBytes = serverInputStream.read(rawMessage);
            String inMessage = new String(rawMessage, 0, numberOfBytes, "UTF-8");
            messages = inMessage.split("<EOM>");

        } catch (Exception e) {
            System.out.println("getServerMessage Exception : " + e.getMessage());
        }

        return messages;
    }
}
