package edu.wright.crowningkings.base;

/**
 * Created by csmith on 10/28/16.
 */

public class ServerMessageHandler {
    public static void interpretMessage(ServerMessage message) {
        System.out.println("\tinterpretMessage(ServerMessage)");
        int messageCode = message.getMessageCode();
        System.out.println("messageCode=" + messageCode);
        switch (messageCode){
            case -1 :
                break;
            case 101:
                break;
            default:
                System.out.println("\tinterpretMessage() default");
        }
    }

}
