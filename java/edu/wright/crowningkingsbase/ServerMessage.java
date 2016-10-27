package edu.wright.crowningkingsbase;

/**
 * Created by csmith on 10/26/16.
 */

/**
 * This is where we will create a message by the "Server Message Handling" package from project 2.
 *
 * When we want to send a message to the server, we want to call toString() and use that string to
 * send to the server. This is obviously not implemented yet, but this is my vision for the future.
 */
public class ServerMessage {
    private String message;
    private String code;

    public ServerMessage(String stringFromServer) {

    }

    public ServerMessage(int messageCode, String ... parameters) {

    }

    @Override
    public String toString() {
        return code + message + "<EOM>";
    }

    public byte[] getServerMessage() throws java.io.UnsupportedEncodingException {
        return this.toString().getBytes("UTF-8");
    }
}
