package edu.wright.crowningkings.desktop;

import edu.wright.crowningkings.base.UserInterface.AbstractUserInterface;
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
import javax.swing.JTextField;
import javax.swing.JTextArea;
import edu.wright.crowningkings.desktop.DesktopUIFactory.CheckersGameUI;
import edu.wright.crowningkings.base.ServerMessage.*;

public class CheckersLobbyUIController implements AbstractUserInterface{

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
		
	private AbstractServerMessage message;
	private CheckersLobbyUI lobby;
	private JTextField messageTextField;
	private JPanel lobbyPanel;
	private JTextArea currentUsers;
	private JTextArea lobbyChat;
	private int gamePanelx = 12;
	private int gamePanely = 13;
	private int gamePanels = 0;
	private String username = "";
	private CheckersGameUI currentGame;
	//private DesktopUIFactory factory;
	private Image im = new ImageIcon("C:\\Users\\Michael\\Pictures\\GameImages\\checkerboard.jpg").getImage();
	//private Graphics g;
	
	public CheckersLobbyUIController(){
		lobby = new CheckersLobbyUI(); //The whole game lobby
		messageTextField = lobby.getMessageTextField(); //The area that messages are typed. Public / Private
		lobbyPanel = lobby.getLobbyPanel(); //The area that shows all of the tables that are avilable
		currentUsers = lobby.getCurrentUsers(); //The area that shows all of the current users in the lobby
		lobbyChat = lobby.getLobbyChatTextArea(); //The area that shows all of the lobby chat that has happend since being in the server	
		
		//Creating the key listener for the messageTextField to send messages though (Can move the key listener to its own class)
		messageTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				String x = messageTextField.getText();
				if((e.getKeyCode() == KeyEvent.VK_ENTER) && (x.equals("") == false) && !(x.matches("username:[\\w\\W]+"))){
					if(x.matches("private[(][a-zA-Z0-9]+[)]:[\\w\\W]+")){

						sendPrivateMessage();
					} else {
						sendPublicMessage();
					}
				}
				else if((e.getKeyCode() == KeyEvent.VK_ENTER) && (x.matches("username:[\\w\\W]+"))){
					if(username.equals("")){
						username = messageTextField.getText().replace("username:", "").trim();
						messageTextField.setText("");
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

	//This adds a new table to the lobbyPanel for viewing
	public void makeNewGamePanel(final String tableID){
		JPanel p = DesktopUIFactory.makeGamePanel(tableID,im, null);
		Component[] comp = p.getComponents(); 
		final int[] cords = {-1,-1};
		//These two buttons are known from the DeskTopUIFactory 
		JButton playButton = (JButton) comp[0];
		JButton observeButton = (JButton) comp[1];
		
	    /* 
		 * Adds the functinality to the play button to make a new game window
		 */
		playButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(currentGame != null){
					//Handle this in a different way later on
					currentGame.dispose();
				}
				//sendJoinTable(p.getName());
				//currentGame = (CheckersGameUI) DesktopUIFactory.makeGameLobby("Name1", "Name2", tableID);
				//currentGame.setVisible(true);
				final JPanel pan = (JPanel)currentGame.checkerBoard;

				//System.out.println(currentGame.getComponents()[0].getComponentAt(500, 500).getName());

				/*  
				 * This adds the mouse listener to the game window to handle moving it already converts to the
				 * coordinates to the values that the server needs
				 * Need to fix where a piece can be moved to the border of the board 
				 */
				pan.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent e){
						//System.out.println("Test");
						//if(pan.getComponentAt(e.getX(), e.getY()) instanceof JLabel){
							if(cords[0] == -1 && cords[1] == -1){
								JLabel temp = pan.getComponentAt(e.getX(), e.getY()) instanceof JLabel ? (JLabel)pan.getComponentAt(e.getX(), e.getY()):null;
								if(temp != null && temp.getIcon() != null && temp instanceof JLabel){	
									cords[0] = e.getX();
									cords[1] = e.getY();
									System.out.println("grabbed");

								} else {}
							}
							//This needs to be changed to only send the move message with the specified coordinates
							else {
								JLabel temp2 = (JLabel)pan.getComponentAt((int)cords[0],(int)cords[1]);
								int tX = (e.getX()/62);// * 62) + 2;
								int tY = (e.getY()/62);// * 62) + 2;
								String fromx = Integer.toString(cords[0]);
								String fromy = Integer.toString(cords[1]);
								String tox = Integer.toString(tX);
								String toy = Integer.toString(tY);			
								temp2.setBounds(tX,tY,62,62);
								//sendMoveToServer(fromx,fromy,tox,toy);
								cords[0] = -1;
								cords[1] = -1;
								pan.repaint();
								System.out.println("released");
							}
						}
					//}
				});
				//Request to join table with this tables tableID
			}
		});
		
		//Unimplemented but should just create a DesktopUIFactory.makeGamePanel(tableID,im, null) but not add the mouse listiner for
		//the game board
		observeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				//Request to observe table with this tables tableID
			}
		});
		
		p.setBounds(gamePanelx, gamePanely, 250, 250);
		p.setVisible(true);
		lobbyPanel.add(p);
		gamePanelx += 300;
		gamePanels++;
		if(gamePanels % 4 == 0){
			gamePanely += 300;
			gamePanelx = 12;
		}
		lobby.repaint();
		
	}
	
	public void makeClientVisable(){
		lobby.setVisible(true);
	}
	//Old DO NOT USE
	public void sendPrivateChatMessage(String message, String to, String from){

	}
	
	//OLD DO NOT USE
	public String getMessageFromUser(String message){
		return message;
	}
	
	//Not sure about these right now are NOT part of the Interface
	//Update the current users UI when a new user joins
	//Better the handle when you call this in the file that
	//controls server messages
	public void addUser(String username){
		currentUsers.setText((currentUsers.getText()) + "\n" + username);
	}
	public void removeUser(String username){
		currentUsers.setText((currentUsers.getText().replaceAll(username + "\n", "")));
	}


	
	public String getUsernameFromUser() {
		return username;
	}

	
	public String getTableIdFromUser() {
		return lobbyPanel.getComponents()[0].getName();
	}


	//Old DO NOT USE
	public String[] getMoveFromUser() {
		
		return null;
	}


	//Old DO NOT USE
	public String[] getPublicMessageFromUser() {
		String[] message = {messageTextField.getText()};
		lobbyChat.setText((lobbyChat.getText() + "\n" + messageTextField.getText()));
		messageTextField.setText("");
		return message;
	}


	//Old DO NOT USE
	public String[] getPrivateMessageFromUser() {
			return null;
	}


	//ALL Methods bewllow this point should be form the Interface
	//There are a few above this that are also from the Interface


	public void updateLobbyChat(String newMessage) {
		// TODO Auto-generated method stub
		
	}


	
	public void updateUsers(String newUser) {
		// TODO Auto-generated method stub
		
	}


	
	public void sendWantTable() {
		// TODO Auto-generated method stub
		
	}


	
	public void makeTable(String tableID) {
		// TODO Auto-generated method stub
		
	}

	
	public void makeTable(String[] tableID) {
		// TODO Auto-generated method stub
		
	}

	
	public void sendJoinTable() {
		
		//message = new JoinTable(tableID);
		
	}


	
	public void setJoinTable(String tableID, String type) {
		// TODO Auto-generated method stub
		
	}


	
	public void sendPublicMessage() {
		
		//Not how we are doing this now
		//new MessageAll(messageTextField.getText().trim()).run();
		//messages = list<AbstractServerMessage> <---- Static 
		//messages.add(new MessageAll(messageTextField.getText().trim())
		
		
	}


	
	public void sendPrivateMessage() {
		String[] temp = messageTextField.getText().replace("private","").split(":");
		String user = temp[0].replace("(", "").replace(")", "");
		String msg = temp[1].trim();
		message = new MessageAll(messageTextField.getText().trim());

	}


	
	public void sendMoveToServer() {
		
		//message = new Move(fromx,fromy,tox,toy);
		
	}


	
	public void updateBoard(String[] board) {
		
	}


	
	public void updateError(String errorConst) {
		// TODO Auto-generated method stub
		
	}
	

}

