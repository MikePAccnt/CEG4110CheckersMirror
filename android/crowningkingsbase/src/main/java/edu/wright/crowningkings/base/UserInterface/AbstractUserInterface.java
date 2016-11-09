package edu.wright.crowningkings.base.UserInterface;

import java.util.Scanner;

/**
 * Created by csmith on 11/1/16.
 *
 * Hopefully in the future this will distinguish between using the Android and Desktop UI...
 *
 */

public abstract class AbstractUserInterface {

    public abstract String getUsernameFromUser();

    public abstract String getMessageFromUser();

    public abstract String getRecipientFromUser();

    public abstract String getTableIdFromUser();

    public abstract String[] getMoveFromUser();
}
