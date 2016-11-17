package edu.wright.crowningkings.base;

import edu.wright.crowningkings.base.ServerMessage.*;
import edu.wright.crowningkings.base.UserInterface.*;


/**
 * Created by csmith on 11/1/16.
 * <p>
 * Right now the class name/design is still a prototype. As long as we can keep it out of using a
 * "public static void main(String[] args)" since Android doesn't really use that.
 *
 * 11/15/2016
 * BaseClient will be acting like the Controller between the UI (View) and server (Model) to follow
 * the MVC design pattern.
 */
public class BaseClient {
    private static final int PORT_NUMBER = 45322;
    private ServerConnection server;
    private Thread serverMessageThread;
    private AbstractUserInterface ui;
    private BaseClient client;


    /**
     * Constructor methods
     */
    public BaseClient(AbstractUserInterface ui) {
        String serverAddress = "130.108.236.123";

        client = this;
        setServer(serverAddress, PORT_NUMBER);
        startServerMessageThread();
        setClientUI(ui);
    }


    private void setServer(String address, int port) {
        server = new ServerConnection(address, port);
    }

    private void startServerMessageThread() {
        if (serverMessageThread == null) {
            serverMessageThread = new Thread() {
                public void run() {
                    while (!Thread.interrupted()) {
                        String[] messages = server.getServerMessageString();

                        for (String stringMessage : messages) {
                            AbstractServerMessage sm = ServerMessageHandler.interpretMessage(stringMessage);
                            try {
                                sm.run(client);
                            } catch (NullPointerException npe) {
                                System.out.println("run NullPointerException : " + npe.getMessage());
                            }
                        }
                    }
                }
            };
        }
        serverMessageThread.start();
    }


    private void setClientUI(AbstractUserInterface ui) {
        this.ui = ui;
    }



    /**
     * Methods to be called FROM the user interface
     * These methods will primarly be used for sending server messages
     */
    public void setUsername(String username) {
        server.sendServerMessage(new SendUsername(username));
    }


    public void quit() {
        server.sendServerMessage(new Quit());
        serverMessageThread.interrupt();
    }


    public void makeTable() {
        server.sendServerMessage(new MakeTable());
    }


    public void leaveTable() {
        server.sendServerMessage(new LeaveTable());
    }


    public void ready() {
        server.sendServerMessage(new Ready());
    }


    public void sendPublicMessage(String message) {
        server.sendServerMessage(new MessageAll(message));
    }

    public void sendClientMessage(String recipient, String message) {
        server.sendServerMessage(new MessageClient(message, recipient));
    }

    public void movePiece(String fromx, String fromy, String tox, String toy){
        server.sendServerMessage(new Move(fromx, fromy, tox, toy));
    }

    /**
     * Methods to be called FROM the server (i.e. from an AbstractServerMessage's run method)
     * These methods will be used for updating the UI based on information given from server
     */
    public void updateTableList(String[] tables) {
        ui.makeTable(tables);
    }


    public void addTable(String table) {
        ui.makeTable(table);
    }



    /**
     * Only here to keep the commandlineui functional
     */
    public void joinTableObserver() {
        String tableId = ui.getTableIdFromUser();
        server.sendServerMessage(new JoinTableObserver(tableId));
    }


    public void joinTable() {
        String tableId = ui.getTableIdFromUser();
        server.sendServerMessage(new JoinTable(tableId));
    }


    public void status() {
        String table = ui.getTableIdFromUser();
        server.sendServerMessage(new Status(table));
    }
}
