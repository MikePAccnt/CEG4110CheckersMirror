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


    public BaseClient(AbstractUserInterface ui) {
        String serverAddress = "192.168.122.1";

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


    public void status() {
        String table = ui.getTableIdFromUser();
        server.sendServerMessage(new Status(table));
    }


    public void setUsername(String username) {
        server.sendServerMessage(new SendUsername(username));
    }

    public void sendPublicMessage() {
        String[] publicMessage = ui.getPublicMessageFromUser();
        server.sendServerMessage(new MessageAll(publicMessage[0]));
    }

    public void sendPrivateMessage() {
        String[] privateMessageArray = ui.getPrivateMessageFromUser();
        String recipient = privateMessageArray[0];
        String privateMessage = privateMessageArray[1];
        server.sendServerMessage(new MessageClient(privateMessage, recipient));
    }


    public void quit() {
        server.sendServerMessage(new Quit());
        //serverMessageThread.interrupt();
    }


    public void makeTable() {
        server.sendServerMessage(new MakeTable());
    }


    public void joinTable() {
        String tableId = ui.getTableIdFromUser();
        server.sendServerMessage(new JoinTable(tableId));
    }


    public void joinTableObserver() {
        String tableId = ui.getTableIdFromUser();
        server.sendServerMessage(new JoinTableObserver(tableId));
    }


    public void leaveTable() {
        server.sendServerMessage(new LeaveTable());
    }


    public void ready() {
        server.sendServerMessage(new Ready());
    }


    public void move() {
        String[] move = ui.getMoveFromUser();
        server.sendServerMessage(new Move(move[0], move[1], move[2], move[3]));
    }

    public void updateTableList(String[] tables) {
        ui.updateTablesList(tables);
    }

    public void addTable(String table) {
        ui.addTable(table);
    }


    //Only here to keep the commandlineui functional
    public void setUsername() {
        String username = ui.getUsernameFromUser();
        server.sendServerMessage(new SendUsername(username));
    }
}
