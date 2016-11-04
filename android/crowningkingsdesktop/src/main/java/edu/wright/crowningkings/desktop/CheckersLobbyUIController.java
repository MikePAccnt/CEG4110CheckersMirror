package edu.wright.crowningkings.desktop;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

public class CheckersLobbyUIController {

	/**
	 * @param args
	 */
		
	final JTextField messageTextField;
	private CheckersLobbyUI lobby;
	
	public CheckersLobbyUIController(){
		lobby = new CheckersLobbyUI();
		
		//Possibly move this so we have control over when
		//the client is visible; such as only after a username
		//is given and accepted.
		lobby.setVisible(true);
		
		//Grabbed access to this whole component because I don't think we should
		//hard-code the key listener into the main UI 
		messageTextField = lobby.getMessageTextField(); 
		
		//Creating the key listener for the message box to send messages though
		messageTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				String x = messageTextField.getText();
				if((e.getKeyCode() == KeyEvent.VK_ENTER) && (x.equals("") == false)){
					if(x.matches("private [(][a-zA-Z0-9]+[)]: [a-zA-Z0-9]+")){
						System.out.println("true");
						//Grab out important info from the string
						//sendPrivateChatMessage(string); 
					}else {
						//sendChatMessage(); Unimplemented method that will send it to the server first
						lobby.setLobbyChatTextArea(lobby.getLobbyChatTextArea() + "\n" + messageTextField.getText());
						messageTextField.setText("");
					}
				}
			}
		});
		
		
	}

	//Empty method for sending private messages
	public void sendPrivateChatMessage(String message, String to, String from){}
	
	//Empty method for sending public messages to the lobby chat
	public void sendChatMessage(String message){}
	
	//Update the current users UI when a new user joins
	//Better the handle when you call this in the file that
	//controls server messages
	public void userJoin(String username){
		lobby.setCurrentUsersTextAreaText(username + lobby.getCurrentUsersTextAreaText());
	}
	//Update the current users UI when a user leaves
	//Better the handle when you call this in the file that
	//controls server messages
	public void userLeft(String username){
		lobby.setCurrentUsersTextAreaText(lobby.getCurrentUsersTextAreaText().replaceAll(username + "\n", ""));
	}


}


