package edu.wright.crowningkings.base;

import edu.wright.crowningkings.base.UserInterface.AbstractUserInterface;
import edu.wright.crowningkings.base.UserInterface.CommandLineUI;

public class MainTest {
    public static void main(String[] args) {
        (new CommandLineUI()).run();
    }
}
