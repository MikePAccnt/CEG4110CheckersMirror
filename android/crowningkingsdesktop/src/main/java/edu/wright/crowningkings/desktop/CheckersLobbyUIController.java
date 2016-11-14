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
import edu.wright.crowningkings.desktop.DesktopUIFactory.CheckersGameUI;

public class CheckersLobbyUIController extends AbstractUserInterface{

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
		
	final JTextField messageTextField;
	JPanel lobbyPanel;
	private CheckersLobbyUI lobby;
	private int gamePanelx = 12;
	private int gamePanely = 13;
	private int gamePanels = 0;
	private String username = "";
	private CheckersGameUI currentGame;
	//private DesktopUIFactory factory;
	private Image im = new ImageIcon("C:\\Users\\Michael\\Pictures\\GameImages\\checkerboard.jpg").getImage();
	//private Graphics g;
	
	public CheckersLobbyUIController(){
		lobby = new CheckersLobbyUI();
		
		
		
		//Grabbed access to this whole component because I don't think we should
		//hard-code the key listener into the main UI 
		messageTextField = lobby.getMessageTextField(); 
		
		lobbyPanel = lobby.getLobbyPanel();
		
		
		//Creating the key listener for the message box to send messages though
		messageTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				String x = messageTextField.getText();
				if((e.getKeyCode() == KeyEvent.VK_ENTER) && (x.equals("") == false) && !(x.matches("username:[\\w\\W]+"))){
					if(x.matches("private[(][a-zA-Z0-9]+[)]:[\\w\\W]+")){
						getPrivateMessageFromUser();
						//Grab out important info from the string
						//sendPrivateChatMessage(string); 
					} else {
						getPublicMessageFromUser();
					}
				}
				else if((e.getKeyCode() == KeyEvent.VK_ENTER) && (x.matches("username:[\\w\\W]+"))){
					if(username.equals("")){
						username = messageTextField.getText().replace("username:", "").trim();
						messageTextField.setText("");
					} else {
						lobby.setLobbyChatTextArea(lobby.getLobbyChatTextArea() + "\n" + "ERROR: You already set your username!");
						messageTextField.setText("");
					}
				}
				else if(e.getKeyCode()== KeyEvent.VK_ESCAPE){
					messageTextField.setText("");
				}

			}
		});
		
		
	}

	
	public void makeNewGamePanel(final String tableID){
		JPanel p = DesktopUIFactory.makeGamePanel(tableID,im, null);
		Component[] comp = p.getComponents(); 
		final double[] cords = {-1,-1};
		//These two buttons are known from the DeskTopUIFactory
		JButton playButton = (JButton) comp[0];
		JButton observeButton = (JButton) comp[1];
		
		playButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				//testing code
				//lobby.setVisible(false);
				if(currentGame != null){
					//Handle this in a different way later on
					currentGame.dispose();
				}
				currentGame = (CheckersGameUI) DesktopUIFactory.makeGameLobby("Name1", "Name2", tableID);
				currentGame.setVisible(true);
				final JPanel pan = (JPanel)currentGame.checkerBoard;
//				for(int i = 0; i < 499;i++){
//					for(int j = 0; j < 499; j++){
//						System.out.println(currentGame.getComponents()[0].getComponentAt(i, j).getName());
//					}
//				}
				//currentGame.gameLobby;
				System.out.println(currentGame.getComponents()[0].getComponentAt(500, 500).getName());
				pan.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent e){
						System.out.println("Test");
						//if(pan.getComponentAt(e.getX(), e.getY()) instanceof JLabel){
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
	//Empty method for sending private messages
	public void sendPrivateChatMessage(String message, String to, String from){}
	
	//Empty method for sending public messages to the lobby chat
	public String getMessageFromUser(String message){
		return message;
	}
	
	//Update the current users UI when a new user joins
	//Better the handle when you call this in the file that
	//controls server messages
	public void addUser(String username){
		lobby.setCurrentUsersTextAreaText(username + "\n" +lobby.getCurrentUsersTextAreaText());
	}
	
//	//Creates a new panel for a game
//	public void addNewGame(){
//		JPanel gamePanel = lobby.getGamePanel();
//		gamePanel.setBounds(0,0,250,250);
//		lobby.add(gamePanel);
//		
//	}
	
	//Update the current users UI when a user leaves
	//Better the handle when you call this in the file that
	//controls server messages
	public void removeUser(String username){
		lobby.setCurrentUsersTextAreaText(lobby.getCurrentUsersTextAreaText().replaceAll(username + "\n", ""));
	}


	@Override
	public String getUsernameFromUser() {
		return username;
	}



	@Override
	public String getTableIdFromUser() {
		return lobbyPanel.getComponents()[0].getName();
	}


	@Override
	public String[] getMoveFromUser() {
		
		return null;
	}


	@Override
	public String[] getPublicMessageFromUser() {
		String[] message = {messageTextField.getText()};
		lobby.setLobbyChatTextArea(lobby.getLobbyChatTextArea() + "\n" + messageTextField.getText());
		messageTextField.setText("");
		return message;
	}


	@Override
	public String[] getPrivateMessageFromUser() {
		String[] message;
		String[] temp = messageTextField.getText().replace("private","").split(":");
		String user = temp[0].replace("(", "").replace(")", "");
		String msg = temp[1].trim();
		System.out.println(messageTextField.getText());
		System.out.println("User "+ user);
		System.out.println("Message "+ msg);
		message = new String[] {user ,msg};
		return message;
	}


	@Override
	public void updateLobbyChat(String newMessage) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateUsers(String newUser) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void sendWantTable() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void makeTable(String tableID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makeTable(String[] tableID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendJoinTable() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setJoinTable(String tableID, String type) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void sendPublicMessage() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void sendPrivateMessage() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void sendMoveToServer() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateBoard(String[] board) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateError(String errorConst) {
		// TODO Auto-generated method stub
		
	}
	

}

