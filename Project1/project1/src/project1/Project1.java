package project1;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;



public class Project1 {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        String userName;
        String serverAddress = "130.108.13.36";
        int portNumber = 45322;
        
        
        System.out.println("Enter desired username with no spaces");
        userName = keyboard.nextLine();
//        System.out.println("Enter the server address");
//        serverAddress = keyboard.nextLine();
        //System.out.println("Enter the port number");
        //portNumber = keyboard.nextInt();
        System.out.println("Attempting to connect to the server " + serverAddress + " on port " + portNumber);
        try{
             Socket echoSocket = new Socket(serverAddress, portNumber); //Open connection 
             PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true); //Open writerstream
             
             DataInputStream inFromServer = new DataInputStream(echoSocket.getInputStream());
             
             while(true){
                 //Recieve message from server and convert to string
             byte[] rawMessage = new byte[512];
             inFromServer.read(rawMessage);
             String inMessage = new String(rawMessage, "UTF-8");
             //String trimmedMessage = inMessage.replaceAll("/[\\s]<EOM>.*/g" , "");
             String trimmedMessage;
             
             trimmedMessage = inMessage.substring(inMessage.indexOf("<EOM>"), inMessage.length());
             inMessage = inMessage.replace(trimmedMessage,"");
                 System.out.println(inMessage);
            

             }
   
    
        }   
        catch(java.net.UnknownHostException e){
            System.out.println("Host error" + e);
        } catch (IOException ex) {
            System.out.println("Error" + ex);
        }

    
    }
    public static void sendMessage(String message){
   
        
    }
    
}
