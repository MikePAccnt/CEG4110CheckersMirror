package edu.wright.crowningkings.base.UserInterface;

import java.util.Scanner;

import edu.wright.crowningkings.base.UserInterface.AbstractUserInterface;

/**
 * Created by csmith on 11/4/16.
 */

public class CommandLineUI extends AbstractUserInterface {
    public String getUsernameFromUser() {
        System.out.println("Enter desired username with no spaces");
        return (new Scanner(System.in)).nextLine().replace(" ", "").trim();
    }

    @Override
    public String getMessageFromUser() {
        System.out.println("Enter global chat message to send");
        return (new Scanner(System.in)).nextLine().trim();
    }

    @Override
    public String getRecipientFromUser() {
        System.out.println("Enter recipient username");
        return (new Scanner(System.in)).nextLine().replace(" ", "").trim();
    }
}
