package edu.wright.crowningkings.base.UserInterface;

/**
 * Created by csmith on 11/1/16.
 *
 * Hopefully in the future this will distinguish between using the Android and Desktop UI...
 *
 */

public abstract class AbstractUserInterface {

    public abstract String getUsernameFromUser();

    public abstract String[] getPublicMessageFromUser();

    public abstract String[] getPrivateMessageFromUser();

    public abstract String getTableIdFromUser();

    public abstract String[] getMoveFromUser();
}
