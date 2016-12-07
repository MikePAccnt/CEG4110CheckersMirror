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
        String serverAddress = "130.108.28.165";

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

    public void messageAll(String message) {
        server.sendServerMessage(new _101MessageAll(message));
    }

    public void messageClient(String recipient, String message) {
        server.sendServerMessage(new _102MessageClient(message, recipient));
    }

    public void makeTable() {
        server.sendServerMessage(new _103MakeTable());
    }

    public void joinTable(String tableId) {
        server.sendServerMessage(new _104JoinTable(tableId));
    }

    public void ready() {
        server.sendServerMessage(new _105Ready());
    }

    public void move(String fromx, String fromy, String tox, String toy){
        server.sendServerMessage(new _106Move(fromx, fromy, tox, toy));
    }

    public void leaveTable() {
        server.sendServerMessage(new _107LeaveTable());
    }

    public void quit() {
        server.sendServerMessage(new _108Quit());
        serverMessageThread.interrupt();
    }

    public void askTableStatus(String tableID){
        server.sendServerMessage(new _109AskTableStatus(tableID));
    }

    public void observeTable(String tableID){
        server.sendServerMessage(new _110ObserveTable(tableID));
    }



    /**
     * Methods to be called FROM the server (i.e. from an AbstractServerMessage's run method)
     * These methods will be used for updating the UI based on information given from server
     */
    public void sendUsernameRequest() {
        ui.sendUsernameRequest();
    }

    public void connectionOk(){
        ui.connectionOK();
    }

    public void message(String message, String from, boolean privateMessage){
        ui.message(message,from,privateMessage);
    }

    public void newTable(String tableID){
        ui.newtable(tableID);
    }

    public void gameStart(){
        ui.gameStart();
    }

    public void colorBlack(){
        ui.colorBlack();
    }

    public void colorRed(){
        ui.colorRed();
    }

    public void opponentMove(String[] from, String[] to){
        ui.opponentMove(from,to);
    }

    public void boardState(String boardState){
        ui.boardState(boardState);
    }

    public void gameWon(){
        ui.gameWon();
    }

    public void gameLose(){
        ui.gameLose();
    }

    public void tableJoined(String tableID){
        ui.tableJoined(tableID);
    }

    public void whoInLobby(String[] users) {
        ui.whoInLobby(users);
    }

    public void outLobby(){
        ui.outLobby();
    }

    public void nowInLobby(String newClientUsername) {
        ui.nowInLobby(newClientUsername);
    }

    public void tableList(String[] tables) {
        ui.tableList(tables);
    }

    public void nowLeftLobby(String user) {
        ui.nowLeftLobby(user);
    }

    public void inLobby(){
        ui.inLobby();
    }

    public void whoOnTable(String userOne, String userTwo,String tableID, String userOneColor,String userTwoColor){
        ui.whoOnTable(userOne,userTwo,tableID,userOneColor,userTwoColor);
    }

    public void opponentLeftTable(){
        ui.opponentLeftTable();
    }

    public void yourTurn(){
        ui.yourTurn();
    }

    public void tableLeft(String tableID){
        ui.tableLeft(tableID);
    }

    public void nowObserving(String tableID){
        ui.nowObserving(tableID);
    }

    public void stoppedObserving(String tableID){
        ui.stoppedObserving(tableID);
    }


    public void netException(){
        ui.netException();
    }
    public void nameInUse(){
        ui.nameInUse();
    }
    public void illegalMove(){
        ui.illegalMove();
    }
    public void tblFull(){
        ui.tblFull();
    }
    public void notInLobby(){
        ui.notInLobby();
    }
    public void badMessage(){
        ui.badMessage();
    }
    public void errorLobby(){
        ui.errorLobby();
    }
    public void badName(){
        ui.badName();
    }
    public void playerNotReady(){
        ui.playerNotReady();
    }
    public void notYourTurn(){
        ui.notYourTurn();
    }
    public void tableNotExist(){
        ui.tableNotExist();
    }
    public void gameNotCreated(){
        ui.gameNotCreated();
    }
    public void notObserving(){
        ui.notObserving();
    }


}
