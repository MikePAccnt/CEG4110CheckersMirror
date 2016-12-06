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
import javax.swing.UIManager;
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
	private boolean play = false;
	private boolean turn = false;
	private int[] cords = {-1,-1};
	private int[] lastStartMove = {-1,-1};
	private int[] lastEndMove = {-1,-1};
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
				
				makeTable();
				
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
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
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
		cords = new int[] {-1,-1};
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
					joinTable(tableID);
				}
				
			}
		});
		
	    /* 
		 * When the observe button is pressed it sends a message to the server
		 * that it wants to join that table as an observer
		 */
		observeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				observeTable(tableID);
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
				
				makeTable();
				
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
	
	private void sendName(String name){
		client.setUsername(name);
	}


	public void setJoinPlayTable(String tableID, String oponentName) {

		currentGame = (CheckersGameUI) DesktopUIFactory.makeGameLobby(username, oponentName, tableID);
		currentGame.setVisible(true);
		currentGame.repaint();
		applyButtonActions(new Component[] {CheckersGameUI.readyButton, CheckersGameUI.quitButton});
		gameBoard = (JPanel)currentGame.checkerBoard;

		//Add the action listiner to the game board so the player can move the pieces
		gameBoard.addMouseListener(new gameBoardListener(new int[] {-1,-1},gameBoard,this.color));
		gameBoard.repaint();
			
	}

	public void setJoinObserveTable(String tableID, String user1,String user2) {

		currentGame = (CheckersGameUI) DesktopUIFactory.makeGameLobby(user1, user2, tableID);
		currentGame.setVisible(true);
		applyButtonActions(new Component[] {CheckersGameUI.readyButton, CheckersGameUI.quitButton});
		gameBoard = (JPanel)currentGame.checkerBoard;
		
		
	}

	public void updateLobbyChat(String newMessage) {
		
		//Whatever calls this method can handle formatting it with the username of who
		//sent the message and maybe a time-stamp if wanted or that can be implemented here
		lobbyChat.setText(lobbyChat.getText() + "\n" + newMessage);
		
	}


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
		updateLobbyChat("Msg Sent to: " + user + " " + msg);
		messageTextField.setText("");
    }

    public void makeTable(){
    	client.makeTable();
    }

    public void joinTable(String tableID){
    	client.joinTable(tableID);
    	//setJoinPlayTable(tableID,"");
    }

    public void ready(){
    	client.ready();
    }

    public void move(String fromx ,String fromy, String tox, String toy){
    	client.move(fromx,fromy,tox,toy);
    }

    public void leaveTable(){
    	client.leaveTable();
    }

    public void quit(){
    	currentGame.dispose();
    	currentGame = null;
    	client.quit();
    }

    public void askTableStatus(String tableID){
    	client.askTableStatus(tableID);
    }

    public void observeTable(String tableID){
    	client.observeTable(tableID);
    }


    /*
     * Bellow this is handling all incoming messages that
     * the client can recieve from the server
     */

    public void sendUsernameRequest(){}

    public void connectionOK(){}

    public void message(String message, String from, boolean privateMessage){
    	if(privateMessage == true){
    		updateLobbyChat("Private (" + from + ": " + message + ")");
    	} else {
    		updateLobbyChat(from + ": " + message);
    	}
    }

    public void newtable(String tableID){
    	makeNewGamePanel(tableID);
    }

    public void gameStart(){
    	play = true;
    }

    public void colorBlack(){
    	this.color = "Black";
    	if(gameBoard.getMouseListeners()[0] instanceof gameBoardListener){
    		gameBoardListener gb = (gameBoardListener)gameBoard.getMouseListeners()[0];
    		gb.setColor("Black");
    	}
    	//(gameBoardListener)gameBoard.getMouseListeners()[0].setColor("Black");
    }

    public void colorRed(){
    	this.color = "Red";
    	if(gameBoard.getMouseListeners()[0] instanceof gameBoardListener){
    		gameBoardListener gb = (gameBoardListener)gameBoard.getMouseListeners()[0];
    		gb.setColor("Red");
    	}
    }

    public void opponentMove(String[] from, String[] to){
    	int fx = (Integer.parseInt(from[0]) * 62) + 2;
    	int fy = (Integer.parseInt(from[1]) * 62) + 2;
    	int tx = (Integer.parseInt(to[0]) * 62) + 2;
    	int ty = (Integer.parseInt(to[1]) * 62) + 2;
    	gameBoard.getComponentAt(fx,fy).setBounds(tx,ty,62,62);
    }

    public void boardState(String[][] board){
    	System.out.println("In boardState");
    	//Later on update this so it draws the board differently depending on the players color
		if(gameBoard == null){System.out.println("gameBoard is null!!!!!");}
		//clear the entire board
		for(Component c : gameBoard.getComponents()){
			if(c == null){System.out.println("c Was NULL!!!!!!");}
			gameBoard.remove(c);
		}

		//Re-populate the board with the current board state from the server
		for (int x = 0;x<board.length;x++ ) {
			for(int y = 0;y<board[x].length;y++){
				System.out.println("Inside the forloop in boardState");
				int cx = (y*62)+2;
				int cy = (x*62)+2;
				System.out.println(board[x][y] == null ? "Yes":"No");

				if(board[x][y].charAt(0) == 'B'){
					System.out.println("Making Black Piece");
					JLabel temp = DesktopUIFactory.makePiece("Black");
					if(temp == null) {System.out.println("GB: Temp is null!!!");}
					temp.setName("Black");
					temp.setBounds(cx,cy,62,62);
					gameBoard.add(temp);
					System.out.println("Made a Black piece");
				}
				else if(board[x][y].charAt(0) == 'R'){
					System.out.println("Making Red Piece");
					JLabel temp = DesktopUIFactory.makePiece("Red");
					if(temp == null) {System.out.println("GB: Temp is null!!!");}
					temp.setName("Red");
					temp.setBounds(cx,cy,62,62);
					gameBoard.add(temp);
					System.out.println("Made a Red piece");
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
    	currentGame.dispose();
    	currentGame = null;
    	leaveTable();
    }

    public void gameLose(){
    	makeEndGameMessage("You Lose!!!");
    	currentGame.dispose();
    	currentGame = null;
    	leaveTable();
    }

    public void tableJoined(String tableID){
    	setJoinPlayTable(tableID,""); 
    }

    public void whoInLobby(String[] users){

    	for(String s : users){
    		currentUsers.setText((currentUsers.getText()) + "\n" + s);
		
    	}
    	initBlankTable();
    }

    public void outLobby(){}

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
    	currentGame.lblTurn.setVisible(true);
    	turn = true;
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

    //Bellow this are the mothods for hanlding errors sent from the server
    
    public void netException(){}

    public void nameInUse(){}

    public void illegalMove(){
    	turn = true;
    	currentGame.lblTurn.setVisible(true);

    	JLabel temp2 = (JLabel)gameBoard.getComponentAt(lastEndMove[0],lastEndMove[1]);
		//temp2.setBounds(lastMove[0],lastMove[1],62,62);
		gameBoard.remove(temp2);
		gameBoard.add(DesktopUIFactory.makePiece(this.color)).setBounds(lastStartMove[0],lastStartMove[1],62,62);
		gameBoard.repaint();

    }

    public void tblFull(){
    	updateLobbyChat("Server Message: That table is full!");
    }

    public void notInLobby(){}

    public void badMessage(){}

    public void errorLobby(){}

    public void badName(){}

    public void playerNotReady(){}

    public void notYourTurn(){}

    public void tableNotExist(){}

    public void gameNotCreated(){}

    public void notObserving(){}


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
				ready();
			}
		});
		
		quitBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				currentGame.dispose();
				currentGame = null;
				leaveTable();
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
		String color;
		
		public gameBoardListener(int[] cords, JPanel pan,String color){
			this.cords = cords;
			this.pan = pan;
			this.color = color;
		}
		
		public void setColor(String color){
			this.color = color;
		}

		public void mouseClicked(MouseEvent e){
			
			if(cords[0] == -1 && cords[1] == -1 && (play == true && turn == true)){
				System.out.println("Here2");
				System.out.println(e.getX() + ", " + e.getY());
				JLabel temp = pan.getComponentAt(e.getX(), e.getY()) instanceof JLabel ? (JLabel)pan.getComponentAt(e.getX(), e.getY()):null;
				if(temp == null){System.out.println("Temp is null!!!!");}
				if(temp != null){ //&& temp.getIcon() != null && temp instanceof JLabel){
					System.out.println("Here3");
					if(temp.getName().charAt(0) == color.charAt(0)){
						System.out.println("Here4");
						cords[0] = e.getX();
						cords[1] = e.getY();
						System.out.println("Pickup");
						CheckersGameUI.grabbedLbl.setVisible(true);
					}
				} else {}
			}
			else {
				JLabel temp2 = (JLabel)pan.getComponentAt(cords[0],cords[1]);
				String tX = Integer.toString((e.getX()/62));
				String tY = Integer.toString((e.getY()/62));

				String fx = Integer.toString(cords[0] / 62);
				String fy = Integer.toString(cords[1] / 62);
				CheckersGameUI.grabbedLbl.setVisible(false);
				turn = false;
				currentGame.lblTurn.setVisible(false);
				lastStartMove[0] = ((cords[0]/62)*62)+2;
				lastStartMove[1] = ((cords[1]/62)*62)+2;
				cords[0] = -1;
				cords[1] = -1;
				move(fx,fy,tX,tY);

				

				lastEndMove[0] = ((e.getX()/62)*62)+2;
				lastEndMove[1] = ((e.getY()/62)*62)+2;

				temp2.setBounds(e.getX(),e.getY(),62,62);
				pan.remove(temp2);
				pan.add(DesktopUIFactory.makePiece(this.color)).setBounds(((e.getX()/62)*62)+2,((e.getY()/62)*62)+2,62,62);

				pan.repaint();
				System.out.println("released");
			}
		}
	}



}

