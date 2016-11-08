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

    public abstract String getMessageFromUser();

    public abstract String getRecipientFromUser();

    public abstract String getTableIdFromUser();
}
