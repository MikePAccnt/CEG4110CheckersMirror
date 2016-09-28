package project1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;



public class Project1 {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        String userName;
        String serverAddress;
        int portNumber = 45322;
        String currentLine
        System.out.println("Enter desired username with no spaces");
        userName = keyboard.nextLine();
        System.out.println("Enter the server address");
        serverAddress = keyboard.nextLine();
        System.out.println("Enter the port number");
        portNumber = keyboard.nextInt();
        System.out.println("Attempting to connect to the server " + serverAddress + " on port " + portNumber);
        try{
             Socket echoSocket = new Socket(serverAddress, portNumber);
             PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        }
        catch(java.net.UnknownHostException e){
            System.out.println("Host error" + e);
        } catch (IOException ex) {
            System.out.println("Error" + ex);
        }
        while(true) {
          
            
        }
    
    }
    public static void sendMessage(String message){
   
        
    }
    
}
