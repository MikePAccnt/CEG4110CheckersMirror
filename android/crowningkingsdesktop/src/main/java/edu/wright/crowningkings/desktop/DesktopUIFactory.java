package edu.wright.crowningkings.desktop;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.*;
import javax.swing.SwingConstants;


//This class is mainly used so that creating duplicate 
//user-interface objects easier
public class DesktopUIFactory {


	private static int tables = 1;
	private static Image board = new ImageIcon("desktop\\RealCheckerBoard.jpg").getImage();
	private static ImageIcon piece_black = new ImageIcon("desktop\\checkerspiece_black.png");
	private static ImageIcon piece_red = new ImageIcon("desktop\\checkerspiece_red.png");
	
	public static JPanel makeGamePanel(String tableID, Image im, LayoutManager layout){
		JPanel panel = new ImagePanel(im);
		panel.setLayout(layout);
		panel.setName(tableID);
		
		JButton playButton = new JButton("Join Game");
		playButton.setBounds(12, 200, 97, 25);
		panel.add(playButton);
		
	    JButton observeButton = new JButton("Observe");
		observeButton.setBounds(140, 200, 97, 25);
    	panel.add(observeButton);
		
		JLabel label = new JLabel("Table " + (tables) + " " + tableID);
		label.setBounds(0, 0, 250, 30);
		label.setBackground(Color.BLACK);
		label.setFont(new Font("Times new Roman",Font.PLAIN, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		
		panel.add(label);
		
		return panel;
	}
	
	public static final JFrame makeGameLobby(String userOne,String userTwo, String tableID){
		CheckersGameUI gameLobby = new CheckersGameUI();
		gameLobby.setTitle("Game: "+tableID);
		//gameLobby.pack();
		return gameLobby;
	}

	public static JPanel makeNextGamePanel(Image im){
		JPanel panel = new ImagePanel(im);
		panel.setLayout(null);
		panel.setName("NewTable");
		
		JButton makeButton = new JButton("Make Table");
		makeButton.setBounds(70,110, 110, 25);
		panel.add(makeButton);
		return panel;
		
	}
	
public static class ImagePanel extends JPanel{
	 Image im;
	 
	 ImagePanel(Image i){
		 this.im = i;
	 }
	 
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(im, 0, 0, getWidth(),getHeight(),this);
	}
}

public static class CheckersGameUI extends JFrame{
	
	public static JPanel checkerBoard;
	public static JButton readyButton;
	public static JButton quitButton;
	public static JLabel grabbedLbl;
	public static JLabel lblPlayer1;
	public static JLabel lblPlayer2;
	public static JLabel lblTurn;

	public CheckersGameUI(){
		
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0,0,1000,835);
		getContentPane().setLayout(null);
		
		JPanel gameLobby = new JPanel();
		gameLobby.setBounds(0, 0, 1000, 1000);
		gameLobby.setBackground(new Color(0, 128, 0));
		getContentPane().add(gameLobby);
		gameLobby.setLayout(null);
		

		//this.add(jlayer);
		checkerBoard = new ImagePanel(board);
		checkerBoard.setBounds(250, 83, 500, 500);
		checkerBoard.setLayout(null);
		fillBoard(checkerBoard);
		gameLobby.add(checkerBoard);
		
		readyButton = new JButton("Ready");
		readyButton.setBounds(10, 11, 89, 23);
		gameLobby.add(readyButton);
		
		quitButton = new JButton("Quit");
		quitButton.setBounds(10, 45, 89, 23);
		gameLobby.add(quitButton);
		
		grabbedLbl = new JLabel("Piece In Hand");
		grabbedLbl.setBounds(41, 537, 165, 46);
		grabbedLbl.setFont(new Font("Arial", Font.PLAIN, 20));
		grabbedLbl.setBackground(new Color(255, 255, 255));
		grabbedLbl.setHorizontalAlignment(SwingConstants.CENTER);
		grabbedLbl.setVisible(false);
		gameLobby.add(grabbedLbl);
		
		JPanel observersPanel = new JPanel();
		observersPanel.setBounds(10, 83, 230, 443);
		observersPanel.setVisible(true);
		
		lblPlayer1 = new JLabel("Player 1");
		lblPlayer1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayer1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlayer1.setBounds(760, 83, 230, 23);
		gameLobby.add(lblPlayer1);
		
		lblPlayer2 = new JLabel("Player 2");
		lblPlayer2.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayer2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlayer2.setBounds(760, 302, 230, 23);
		gameLobby.add(lblPlayer2);
		gameLobby.add(observersPanel);
		observersPanel.setLayout(null);

		lblTurn = new JLabel("Your Turn");
		lblTurn.setHorizontalAlignment(SwingConstants.CENTER);
		lblTurn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTurn.setBounds(822, 546, 105, 27);
		lblTurn.setVisible(false);
		gameLobby.add(lblTurn);
		
		
		JLabel observersLbl = new JLabel("Observers");
		observersLbl.setBounds(66, 13, 98, 19);
		observersPanel.add(observersLbl);
		observersLbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		observersLbl.setHorizontalAlignment(SwingConstants.CENTER);
		observersLbl.setVisible(true);
		
		JScrollPane observersScrollPane = new JScrollPane();
		observersScrollPane.setBounds(10, 43, 210, 389);
		observersPanel.add(observersScrollPane);
		
		JTextArea textArea = new JTextArea();
		observersScrollPane.setViewportView(textArea);
		textArea.setRows(1);
		textArea.setColumns(1);
		textArea.setEditable(false);
		
		
		JPanel messagePanel = new JPanel();
		messagePanel.setBounds(0, 773, 990, 30);
		gameLobby.add(messagePanel);
		messagePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		messagePanel.setName("MessagePanel");
		
		JTextField messageTextField = new JTextField("");
		messageTextField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		messageTextField.setToolTipText("");
		messageTextField.setHorizontalAlignment(SwingConstants.LEFT);
		messageTextField.setColumns(107);
		messageTextField.setName("MessageTextField");
		
		
		messagePanel.add(messageTextField);
		
		
		JPanel lobbyChatPanel = new JPanel();
		lobbyChatPanel.setBounds(0, 588, 990, 186);
		gameLobby.add(lobbyChatPanel);
		lobbyChatPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lobbyChatPanel.setName("LobbyChatPanel");
		lobbyChatPanel.setLayout(null);
		
		JScrollPane lobbyChatScrollPane = new JScrollPane();
		lobbyChatScrollPane.setBounds(0, 0, 990, 186);
		lobbyChatPanel.add(lobbyChatScrollPane);
		
//		JScrollPane scrollPane = new JScrollPane();
//		scrollPane.setBounds(0, 0, 990, 186);
//		lobbyChatPanel.add(scrollPane);
		
		JTextArea lobbyChatTextArea = new JTextArea();
		lobbyChatTextArea.setText("a\r\na\r\na\r\na\r\nasd\r\ns\r\nds\r\nds\r\nds\r\nde\r\nd\r\ned\r\ned\r\nef\r\nef\r\nef\r\nt\r\ng\r\ngf\r\ng\r\nt\r\nd\r\ndf\r\ndf\r\nr\r\ng\r\nr\r\ndf\r\ndf\r\n\r\nrf\r\nf\r\nd\r\nfd\r\nf\r\nr\r\nfdf");
		lobbyChatScrollPane.setViewportView(lobbyChatTextArea);
		lobbyChatTextArea.setEditable(false);
		lobbyChatTextArea.setLineWrap(true);
		lobbyChatTextArea.setColumns(1);
		lobbyChatTextArea.setWrapStyleWord(true);
		lobbyChatTextArea.setRows(1);
		lobbyChatTextArea.setName("LobbyChatTextArea");

	}
//	
//	public JPanel getBoard(){
//		return gameLobby;
//	}
	
	
	
}

private static void fillBoard(JPanel cBoard){
	int xCord = 2;
	int yCord = 2;
		for(int x = 0;x<64;x++){
			if(x < 8 && (x % 2 != 0)){
				JLabel temp = makePiece("Black");
				temp.setBounds(xCord,yCord,62,62);
				cBoard.add(temp);
			}
			else if(x < 16 && x > 7 && (x % 2 == 0)){
				JLabel temp = makePiece("Black");
				temp.setBounds(xCord,yCord,62,62);
				cBoard.add(temp);
			}
			else if(x > 15 && x < 24 && (x % 2 != 0)){
				JLabel temp = makePiece("Black");
				temp.setBounds(xCord,yCord,62,62);
				cBoard.add(temp);
			}
			else if (x > 39 && x < 48 && (x%2==0)){
				JLabel temp = makePiece("Red");
				temp.setBounds(xCord,yCord,62,62);
				cBoard.add(temp);
			}
			else if(x > 47 && x < 56 && (x % 2 !=0)){
				JLabel temp = makePiece("Red");
				temp.setBounds(xCord,yCord,62,62);
				cBoard.add(temp);
				
			}
			else if(x > 55 && (x%2==0)){
				JLabel temp = makePiece("Red");
				temp.setBounds(xCord,yCord,62,62);
				cBoard.add(temp);
			}
			
			xCord+=62;
			//Move down to the next line
			if((x+1)%8 == 0){
				xCord = 2;
				yCord+=62;
			}
		}
	} 

//Creates a new "Checker piece" and gives it a name based on its color for use in playing
public static JLabel makePiece(String color){
	JLabel lab = new JLabel();
	lab.setName(color);
	if(color.equals("Black")){
		lab.setIcon(piece_black);
		lab.setName("Black");
	}
	else if(color.equals("Red")){
		lab.setIcon(piece_red);
		lab.setName("Red");
	}
	return lab;
}

}
