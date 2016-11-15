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
import javax.swing.SwingConstants;


//This class is mainly used so that creating duplicate 
//user-interface objects easier
public class DesktopUIFactory {

	private static int tables = 1;
	private static Image board = new ImageIcon("C:\\Users\\Michael\\Pictures\\GameImages\\RealCheckerBoard.jpg").getImage();
	private static ImageIcon piece_black = new ImageIcon("C:\\Users\\Michael\\Pictures\\GameImages\\checkerspiece_black.png");
	private static ImageIcon piece_red = new ImageIcon("C:\\Users\\Michael\\Pictures\\GameImages\\checkerspiece_red.png");
	
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
	
	public CheckersGameUI(){
		
		setName("Name");
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0,0,1000,1000);
		//getContentPane().setLayout(null);
		
		JPanel gameLobby = new JPanel();
		gameLobby.setName("Lobby");
		gameLobby.setBackground(new Color(0, 128, 0));
		gameLobby.setBounds(0, 0, 1000, 1000);
		getContentPane().add(gameLobby);
		gameLobby.setLayout(null);
		
		checkerBoard = new ImagePanel(board);
		checkerBoard.setName("Board");
		checkerBoard.setBounds(gameLobby.getWidth() / 4,gameLobby.getHeight() / 4, 500, 500);
		checkerBoard.setLayout(null);
//		checkerBoard.addMouseListener(new MouseAdapter(){
//			
//			public void mouseClicked(MouseEvent e){
//				System.out.println(checkerBoard.getComponentAt(e.getX(), e.getY()).getName());
//			}
//		});
		
		gameLobby.add(checkerBoard);
		
		fillBoard(checkerBoard);
		
		//pack();
		
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
