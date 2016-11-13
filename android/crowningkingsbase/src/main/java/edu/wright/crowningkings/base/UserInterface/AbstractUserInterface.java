package edu.wright.crowningkings.base.UserInterface;

/**
 * Created by csmith on 11/1/16. Updated by mpurcell on 11/13/16
 *
 * Hopefully in the future this will distinguish between using the Android and Desktop UI...
 *
 */

public abstract class AbstractUserInterface {

    
    /*
     * This method is used for providing the username
     * of the client
     */
    public abstract String getUsernameFromUser();

    
    /*
     * This method is used to tell the server that
     * the client wants to create a new table
     */
    public abstract void sendWantTable();
    
    
    /*
     * If the server message is that there is a new table
     * or when initializing the UI this method is used
     * to update the UI to show all of the current tables 
     */
    public abstract void makeTable(String tableID);
    public abstract void makeTable(String[] tableID);
    
    
    /*
     * This method is used to tell the server that
     * the client wants to join a table
     */
    public abstract void sendJoinTable();
    
    
    /*
     * If the server message is that the client can join
     * a table then this method is called to update
     * the UI to show the client that table
     * 
     * String type is to specify if the client is a "player" or a "observer"
     */
    public abstract void setJoinTable(String tableID, String type);
    
    
    /*
     * This method is used to send a public message
     * to the server
     */
    public abstract void sendPublicMessage();

    
    /*
     * This method is used to send a private message
     * to the server
     */
    public abstract void sendPrivateMessage();

    
    /*
     * This method is used if some part of the client
     * or the server needs the current tableID
     * of the client right now
     */
    public abstract String getTableIdFromUser();

    
    /*
     * This method is used to send the wanted move to the server
     * from the client
     */
    public abstract void sendMoveToServer();
    
    
    /*
     * If the server says the move that was sent was valid
     * this method needs to be called with the current state
     * of the board so the client can update its board
     */
    public abstract void updateBoard(String[] board);
    
    
    /*
     * ***This method might need to be changed/updated***
     * 
     * If the server sends a message that contains a new message,
     * public or private, call this method to update the lobby
     * chat.
     */
    public abstract void updateLobbyChat(String newMessage);
    
    
    /*
     * If the server sends a message that a new user has joined
     * call this method with the username so the UI can be updated
     */
    public abstract void updateUsers(String newUser);
    
    /*
     * If the message the server sent back was an error
     * call this method and the client will update according
     * to what the error was
     */
    
    /*
     * Undecided on how to implement this yet, most likely
     * it will be with defined constants for certian error types
     */
    public abstract void updateError(String errorConst);
    
}
