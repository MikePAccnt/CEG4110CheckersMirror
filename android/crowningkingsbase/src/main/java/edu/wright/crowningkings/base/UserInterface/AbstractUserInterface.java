package edu.wright.crowningkings.base.UserInterface;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/1/16. Updated by mpurcell on 11/13/16
 *
 * Hopefully in the future this will distinguish between using the Android and Desktop UI...
 *
 */

public interface AbstractUserInterface {
    /*
     * This method is used for providing the username
     * of the client
     */
    String getUsernameFromUser();

    
    /*
     * This method is used to tell the server that
     * the client wants to create a new table
     */
    void sendWantTable();
    
    
    /*
     * If the server message is that there is a new table
     * or when initializing the UI this method is used
     * to update the UI to show all of the current tables 
     */
    void makeTable(String tableID);
    void makeTable(String[] tableID);
    
    
    /*
     * This method is used to tell the server that
     * the client wants to join a table
     */
    void sendJoinTable();
    
    
    /*
     * If the server message is that the client can join
     * a table then this method is called to update
     * the UI to show the client that table
     * 
     * String type is to specify if the client is a "player" or a "observer"
     */
    void setJoinTable(String tableID, String type, String oponent);
    
    
    /*
     * This method is used to send a public message
     * to the server
     */
    void sendPublicMessage();

    
    /*
     * This method is used to send a private message
     * to the server
     */
    void sendPrivateMessage();

    
    /*
     * This method is used if some part of the client
     * or the server needs the current tableID
     * of the client right now
     */
    String getTableIdFromUser();

    
    /*
     * This method is used to send the wanted move to the server
     * from the client
     */
    void sendMoveToServer();
    
    
    /*
     * If the server says the move that was sent was valid
     * this method needs to be called with the current state
     * of the board so the client can update its board
     */
    void updateBoard(String[][] board);
    
    
    /*
     * ***This method might need to be changed/updated***
     * 
     * If the server sends a message that contains a new message,
     * public or private, call this method to update the lobby
     * chat.
     */
    void updateLobbyChat(String newMessage);
    
    
    /*
     * If the server sends a message that a new user has joined
     * call this method with the username so the UI can be updated
     */
    void addUser(String newUser);

    /*
     * If the server sends a message that a new user has left
     * call this method with the username so the UI can be updated
     */
    void removeUser(String oldUser);
    
    /*
     * If the message the server sent back was an error
     * call this method and the client will update according
     * to what the error was
     */
    
    /*
     * Undecided on how to implement this yet, most likely
     * it will be with defined constants for certian error types
     */
    void updateError(String errorConst);
    

    /*
     * These methods are a few of the old ones that are still used
     * in the commandline interface so they needed to stay to
     * prevent crashing
     */
    String[] getPrivateMessageFromUser();
    String[] getPublicMessageFromUser();
    String[] getMoveFromUser();
}
