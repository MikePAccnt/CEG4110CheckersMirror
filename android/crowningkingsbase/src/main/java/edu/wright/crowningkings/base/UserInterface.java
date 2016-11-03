package edu.wright.crowningkings.base;

import java.util.Scanner;

/**
 * Created by csmith on 11/1/16.
 *
 * Hopefully in the future this will distinguish between using the Android and Desktop UI...
 *
 */

public class UserInterface {
    public UserInterface() {
//        System.out.println(System.getProperty("java.runtime.name"));
    }


    public String getUsernameFromUser() {
        System.out.println("Enter desired username with no spaces");
        return (new Scanner(System.in)).nextLine().replace(" ", "").trim();
    }
}
