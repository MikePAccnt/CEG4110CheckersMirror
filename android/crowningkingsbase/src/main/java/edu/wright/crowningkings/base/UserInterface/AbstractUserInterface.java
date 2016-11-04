package edu.wright.crowningkings.base.UserInterface;

import java.util.Scanner;

/**
 * Created by csmith on 11/1/16.
 *
 * Hopefully in the future this will distinguish between using the Android and Desktop UI...
 *
 */

public abstract class AbstractUserInterface {
//    private final int CMND_LINE = 0;
//    private final int ANDROID = 1;
//    private final int DESKTOP_UI = 2;
//    private int ui;
//    public UserInterface() {
//        System.out.println(System.getProperty("java.runtime.name"));
//        ui = CMND_LINE;
        //new edu.wright.crowningkings.desktop.CheckersLobbyUIController();
//    }


    public abstract String getUsernameFromUser();
    /*{
        switch (ui) {
            case CMND_LINE:
                System.out.println("Enter desired username with no spaces");
                return (new Scanner(System.in)).nextLine().replace(" ", "").trim();
            case ANDROID:
                break;
            case DESKTOP_UI:
                break;
        }
        return "";
    }*/

    public abstract String getMessageFromUser();
    /*{
        switch (ui) {
            case CMND_LINE:
                System.out.println("Enter global chat message to send");
                return (new Scanner(System.in)).nextLine().replace(" ", "").trim();
            case ANDROID:
                break;
            case DESKTOP_UI:
                break;
        }
        return "";
    }*/

    public abstract String getRecipientFromUser();
    /*{
        switch (ui) {
            case CMND_LINE:
                System.out.println("Enter recipient username");
                return (new Scanner(System.in)).nextLine().replace(" ", "").trim();
            case ANDROID:
                break;
            case DESKTOP_UI:
                break;
        }
        return "";
    }*/
}
