package project1;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;



public class Project1 {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        String userName;
        String serverAddress = "192.168.122.1";
        int portNumber = 45322;
        
        
        //System.out.println("Enter desired username with no spaces");
        //userName = keyboard.nextLine();
        //System.out.println("Enter the server address");
        //serverAddress = keyboard.nextLine();
        //System.out.println("Enter the port number");
        //portNumber = keyboard.nextInt();
        System.out.println("Attempting to connect to the server " + serverAddress + " on port " + portNumber);
        try{
			Socket echoSocket = new Socket(serverAddress, portNumber); //Open connection 
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true); //Open writerstream
             
            DataInputStream inFromServer = new DataInputStream(echoSocket.getInputStream());
            DataOutputStream outToServer = new DataOutputStream(echoSocket.getOutputStream());

			setUsername(outToServer);
        	
			boolean wait = false;

            while(true) {
				byte[] rawMessage = new byte[512];
				inFromServer.read(rawMessage);
		        String inMessage = new String(rawMessage, "UTF-8");
				System.out.println("inMessage=\"" + inMessage + "\"");
				String trimmedMessage;
		         	
		        trimmedMessage = inMessage.substring(inMessage.indexOf("<EOM>"), inMessage.length());
		        inMessage = inMessage.replace(trimmedMessage,"");
				System.out.println("inMessage=\"" + inMessage + "\"");
				
				if (!wait) {
					System.out.println("What do you want to do?");
					System.out.println("[wait, sendMessage]");
					String command = keyboard.nextLine();
					switch (command.toLowerCase()) {
						case "wait" :
							wait = true;
							break;
						case "sendmessage" :
							sendMessage(outToServer);
							break;
						default :
							System.out.println("default switch");
							break;
					}
				}
             }
        }   
        catch(java.net.UnknownHostException e){
            System.out.println("Host error" + e);
        } catch (IOException ex) {
            System.out.println("Error" + ex);
        }

    	while(true);
    }
    public static void sendMessage(DataOutputStream out){
   		System.out.println("\tsendMessage()");
		try {
 			Scanner keyboard = new Scanner(System.in);
			System.out.println("enter global chat message");
        	String messageToServer = keyboard.nextLine();
			messageToServer = "101 " + messageToServer + "<EOM>";
			byte[] toServerByteArray = new byte[512];
			toServerByteArray = messageToServer.getBytes("UTF-8");
			out.write(toServerByteArray, 0, toServerByteArray.length);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
        
    }
    
	private static void setUsername(DataOutputStream out) {
		System.out.println("\tsetUsername()");
		try {
 			Scanner keyboard = new Scanner(System.in);
			System.out.println("Enter desired username with no spaces");
        	String messageToServer = keyboard.nextLine();
			byte[] toServerByteArray = new byte[512];
			toServerByteArray = messageToServer.getBytes("UTF-8");
			out.write(toServerByteArray, 0, toServerByteArray.length);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
