package edu.wright.crowningkings.desktop;

import edu.wright.crowningkings.base.UserInterface.AbstractUserInterface;
import edu.wright.crowningkings.base.BaseClient;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import edu.wright.crowningkings.desktop.DesktopUIFactory.CheckersGameUI;
import edu.wright.crowningkings.base.ServerMessage.*;


/*
 * This is the clas that will be called to initilize the UI for the desktop client
 * NOT all methods for updating the client are added yet because the
 * AbstractUserInterface has not been finalized and is still missing possible
 * updating methods and sending methods
 * The actual game lobby where the players play the game is not finalized it still needs:
 *  - A ready button added
 *  - An exit button besides the top exit button
 *  - Chat inside of the game between the two players (I think this is required)
 *  - Extra visual stuff to show the player what is going on
 */
public class CheckersLobbyUIController implements AbstractUserInterface{

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
		
	private AbstractServerMessage message;
	private CheckersLobbyUI lobby;
	private JTextField messageTextField;
	private JPanel lobbyPanel;
	private JPanel gameBoard;
	private JTextArea currentUsers;
	private JTextArea lobbyChat;
	private int gamePanelx = 12;
	private int gamePanely = 13;
	private int gamePanels = 0;
	private String username = "";
	private CheckersGameUI currentGame;
	private BaseClient client;
	private String color = "";
	//private DesktopUIFactory factory;
	private Image im = new ImageIcon("desktop\\checkerboard.jpg").getImage();
	private Image im2 = new ImageIcon("desktop\\checkerboardFake.jpg").getImage();
	//private Graphics g;
	
	public CheckersLobbyUIController(){
		client = new BaseClient(this);
		lobby = new CheckersLobbyUI(); //The whole game lobby
		messageTextField = lobby.getMessageTextField(); //The area that messages are typed. Public / Private
		lobbyPanel = lobby.getLobbyPanel(); //The area that shows all of the tables that are avilable
		currentUsers = lobby.getCurrentUsers(); //The area that shows all of the current users in the lobby
		lobbyChat = lobby.getLobbyChatTextArea(); //The area that shows all of the lobby chat that has happend since being in the server	
		makeClientVisable();
		initBlankTable();

		//Creating the key listener for the messageTextField to send messages though (Can move the key listener to its own class)
		//This is still very messy and needs to be cleaned up at some point
		messageTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				String x = messageTextField.getText();
				if((e.getKeyCode() == KeyEvent.VK_ENTER) && (x.equals("") == false) && !(x.matches("username:[\\w\\W]+"))){
					if(x.matches("private[(][a-zA-Z0-9]+[)]:[\\w\\W]+")){

						messageClient("","");
					} else {
						messageAll("");
					}
				}
				else if((e.getKeyCode() == KeyEvent.VK_ENTER) && (x.matches("username:[\\w\\W]+"))){
					if(username.equals("")){
						username = messageTextField.getText().replace("username:", "").trim();
						messageTextField.setText("");
						sendName(username);
					} else {
						lobbyChat.setText((lobbyChat.getText() + "\n" + "ERROR: You already set your username!"));
						messageTextField.setText("");
					}
				}
				else if(e.getKeyCode()== KeyEvent.VK_ESCAPE){
					messageTextField.setText("");
				}
			}
		});
	}

	public void initBlankTable(){
		JPanel invis = DesktopUIFactory.makeNextGamePanel(im2);
		invis.setBounds(gamePanelx, gamePanely, 250, 250);
		invis.setVisible(true);
		JButton create = (JButton)invis.getComponents()[0];
		create.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
				sendWantTable();
				
			}
		});
		lobbyPanel.add(invis);
		gamePanelx += 300;
		gamePanels++;
		if(gamePanels % 4 == 0){
			gamePanely += 300;
			gamePanelx = 12;
		}
		lobby.repaint();
	}

	//This adds a new table to the lobbyPanel for viewing
	public void makeNewGamePanel(final String tableID){
		if(lobbyPanel.getComponents().length > 0){
			lobbyPanel.remove(lobbyPanel.getComponents()[lobbyPanel.getComponents().length - 1]);
			gamePanelx = 12;
			gamePanely = 13;
			gamePanels = 0;
			for(Component c : lobbyPanel.getComponents()){
				lobbyPanel.remove(c);
				c.setBounds(gamePanelx, gamePanely, 250, 250);
				c.setVisible(true);
				lobbyPanel.add(c);
				gamePanelx += 300;
				gamePanels++;
				if(gamePanels % 4 == 0){
					gamePanely += 300;
					gamePanelx = 12;
				}
			}
			lobbyPanel.repaint();
		}		

		JPanel p = DesktopUIFactory.makeGamePanel(tableID,im, null);
		Component[] comp = p.getComponents(); 
		final int[] cords = {-1,-1};
		//These two buttons are known from the DeskTopUIFactory 
		JButton playButton = (JButton) comp[0];
		JButton observeButton = (JButton) comp[1];
		
	    /* 
		 * When the play button is pressed it sends a message to the server
		 * that it wants to join that table as a player
		 */
		playButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(currentGame != null){
					//Handle this in a different way later on or leave it as is
					updateLobbyChat("Server Message: " + "You are already in a game! You must quit this game to join another.");
				} else {
					//Send message that the client wants to join this table
					sendJoinPlayTable(tableID);
				}
				
			}
		});
		
	    /* 
		 * When the observe button is pressed it sends a message to the server
		 * that it wants to join that table as an observer
		 */
		observeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				sendJoinObserveTable(tableID);
			}
		});
		
		//this is used to keep track of where to draw the tables that are avilable 
		p.setBounds(gamePanelx, gamePanely, 250, 250);
		p.setVisible(true);
		lobbyPanel.add(p);
		gamePanelx += 300;
		gamePanels++;
		if(gamePanels % 4 == 0){
			gamePanely += 300;
			gamePanelx = 12;
		}
		JPanel invis = DesktopUIFactory.makeNextGamePanel(im2);
		Component[] compss = invis.getComponents();
		System.out.println(compss.length);
		invis.setBounds(gamePanelx, gamePanely, 250, 250);
		invis.setVisible(true);
		JButton create = (JButton)invis.getComponents()[0];
		create.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
				sendWantTable();
				
			}
		});	
		lobbyPanel.add(invis);
		gamePanelx += 300;
		gamePanels++;
		if(gamePanels % 4 == 0){
			gamePanely += 300;
			gamePanelx = 12;
		}		
		lobby.repaint();
		
	}
	
	//Used for when the client decides it is time to make the UI visable
	public void makeClientVisable(){
		lobby.setVisible(true);
	
	}
	
	//Not sure about these right now are NOT part of the Interface
	//Update the current users UI when a new user joins
	//Better the handle when you call this in the file that
	//controls server messages
	public void addUser(String user){

	}

	public void removeUser(String user){

	}

	public String getUsernameFromUser() {
		
		return username;
	}

	public void sendName(String name){
		client.setUsername(name);
	}
	
	public String getTableIdFromUser() {
		return lobbyPanel.getComponents()[0].getName();
	}



	//ALL Methods bewllow this point should be form the Interface
	//There are a few above this that are also from the Interface


	public void updateLobbyChat(String newMessage) {
		
		//Whatever calls this method can handle formatting it with the username of who
		//sent the message and maybe a time-stamp if wanted or that can be implemented here
		lobbyChat.setText(lobbyChat.getText() + "\n" + newMessage);
		
	}

	
	public void sendWantTable() {
		
		System.out.println("Clocked");
		client.makeTable();
		
	}

	public void setColor(String c){
		color = c;
	}

	public void makeTable(String tableID) {
		

		
	}

	public void setJoinPlayTable(String tableID, String oponentName) {

		currentGame = (CheckersGameUI) DesktopUIFactory.makeGameLobby(username, oponentName, tableID);
		currentGame.setVisible(true);
		applyButtonActions(new Component[] {CheckersGameUI.readyButton, CheckersGameUI.quitButton});
		gameBoard = (JPanel)currentGame.checkerBoard;

		//Add the action listiner to the game board so the player can move the pieces
		gameBoard.addMouseListener(new gameBoardListener(new int[] {-1,-1},gameBoard));
			
	}

	public void setJoinObserveTable(String tableID, String user1,String user2) {

		currentGame = (CheckersGameUI) DesktopUIFactory.makeGameLobby(user1, user2, tableID);
		currentGame.setVisible(true);
		applyButtonActions(new Component[] {CheckersGameUI.readyButton, CheckersGameUI.quitButton});
		gameBoard = (JPanel)currentGame.checkerBoard;
		
		
	}


    public void sendJoinPlayTable(String tableID){}
   
    public void sendJoinObserveTable(String tableID){}


    /*
     * Below this is all outgoing messages that are called
     * to tell the client to send a server message
     */

    public void messageAll(String message){
    	String mess = messageTextField.getText();
		messageTextField.setText("");
		client.messageAll(mess);
    }

    public void messageClient(String toUser, String message){
    	String[] temp = messageTextField.getText().replace("private","").split(":");
		String user = temp[0].replace("(", "").replace(")", "");
		String msg = temp[1].trim();

		client.messageClient(user,msg);
		messageTextField.setText("");
    }

    public void makeTable(){}

    public void joinTable(String talbeID){}

    public void ready(){}

    public void move(String fromx ,String fromy, String tox, String toy){}

    public void leaveTable(){}

    public void quit(){}

    public void askTableStatus(String tableID){}

    public void observeTable(String talbeID){}


    /*
     * Bellow this is handling all incoming messages that
     * the client can recieve from the server
     */

    public void sendUsernameRequest(){}

    public void connectionOK(){}

    public void message(String message, String from, boolean privateMessage){}

    public void newtable(String tableID){
    	makeNewGamePanel(tableID);
		//setJoinPlayTable(tableID, "");
    }

    public void gameStart(){}

    public void colorBlack(){
    	this.color = "Black";
    }

    public void colorRed(){
    	this.color = "Red";
    }

    public void opponentMove(String[] from, String[] to){
    	int fx = (Integer.parseInt(from[0]) * 62) + 2;
    	int fy = (Integer.parseInt(from[1]) * 62) + 2;
    	int tx = (Integer.parseInt(to[0]) * 62) + 2;
    	int ty = (Integer.parseInt(to[1]) * 62) + 2;
    	gameBoard.getComponentAt(fx,fy).setBounds(tx,ty,62,62);
    }

    public void boardState(String[][] board){
    	//Later on update this so it draws the board differently depending on the players color
		
		//clear the entire board
		for(Component c : gameBoard.getComponents()){
			gameBoard.remove(c);
		}

		//Re-populate the board with the current board state from the server
		for (int x = 0;x<board.length;x++ ) {
			for(int y = 0;y<board[x].length;y++){
				int cx = (x*62)+2;
				int cy = (x*62)+2;

				if(board[x][y] == "B"){
					JLabel temp = DesktopUIFactory.makePiece("Black");
					temp.setName("Black");
					gameBoard.add(temp).setBounds(cx,cy,62,62);
				}
				else if(board[x][y] == "R"){
					JLabel temp = DesktopUIFactory.makePiece("Red");
					temp.setName("Red");
					gameBoard.add(temp).setBounds(cx,cy,62,62);
				}
				else {
					//Do nothing in the case that there is no piece in that spot.
				}
			}
			
		}
		gameBoard.repaint();
    }

    public void gameWon(){
    	makeEndGameMessage("You Win!!!");
    }

    public void gameLose(){
    	makeEndGameMessage("You Lose!!!");
    }

    public void tableJoined(String tableID){
    	setJoinPlayTable(tableID,""); 
    }

    public void whoInLobby(String[] users){

    	for(String s : users){
    		currentUsers.setText((currentUsers.getText()) + "\n" + s);
		
    	}
    }

    public void outLobby(){

    }

    public void nowInLobby(String user){

    	currentUsers.setText((currentUsers.getText()) + "\n" + user);
    	updateLobbyChat("Server Message: " + user + " has joined the lobby");
    }

    public void tableList(String[] tableIDs){

    	for(String s : tableIDs){
			makeNewGamePanel(s);
		}
    }

    public void nowLeftLobby(String user){
    	currentUsers.setText((currentUsers.getText().replaceAll(user + "\n", "")));
		updateLobbyChat("Server Message: " + user + " has left the lobby");
    }

    public void inLobby(){}

    public void whoOnTable(String userOne, String userTwo,String tableID, String userOneColor,String userTwoColor){

    }

    public void opponentLeftTable(){
    	gameWon();
    }

    public void yourTurn(){
    	//Add UI stuff to gameBoard to show this.
    }

    public void tableLeft(String tableID){
    	currentGame.dispose();
    	//Set variables that apply to the game to null / empty
    }

    public void nowObserving(String tableID){
    	setJoinObserveTable(tableID, "User1", "User2");
    }

    public void stoppedObserving(String tableID){
    	currentGame.dispose();
    } 



    private void makeEndGameMessage(String message){
    	JFrame tempF = new JFrame();
    	tempF.setResizable(false);
    	tempF.setBounds(0,0,250,100);
    	tempF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    	JLabel outCome = new JLabel(message);
    	outCome.setBounds(250/4,250/4,50,50);
    	tempF.add(outCome);
    }
	
	private void applyButtonActions(Component[] components){
		JButton readyBtn = (JButton)components[0];
		JButton quitBtn = (JButton)components[1];
		
		readyBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				//Tell the server you are ready to play
			}
		});
		
		quitBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				currentGame.dispose();
				//Tell the server client left the game
			}
		});
	}

    /*
     * This is the listiner that is added to the gameBoard if the
     * client it actually playing in that game
     */
	private class gameBoardListener extends MouseAdapter {
		
		int[] cords;
		JPanel pan;
		
		public gameBoardListener(int[] cords, JPanel pan){
			this.cords = cords;
			this.pan = pan;
		}
		
		public void mouseClicked(MouseEvent e){
			System.out.println("Test");
			if(cords[0] == -1 && cords[1] == -1){
				JLabel temp = pan.getComponentAt(e.getX(), e.getY()) instanceof JLabel ? (JLabel)pan.getComponentAt(e.getX(), e.getY()):null;
				if(temp != null && temp.getIcon() != null && temp instanceof JLabel){	
					cords[0] = e.getX();
					cords[1] = e.getY();
					System.out.println("grabbed");
				} else {}
			}
			else {
				JLabel temp2 = (JLabel)pan.getComponentAt((int)cords[0],(int)cords[1]);
				int tX = ((e.getX()/62) * 62) + 2;
				int tY = ((e.getY()/62) * 62) + 2;
				temp2.setBounds(tX,tY,62,62);
				//pan.remove(temp2);
				//pan.add(DesktopUIFactory.makePiece("Red")).setBounds(tX,tY,62,62);
				cords[0] = -1;
				cords[1] = -1;
				pan.repaint();
				System.out.println("released");
			}
		}
	}



}

