package dev.voidblock;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

//C.E.A.S.A.R.  Coded Encryption Algorithm for Safety and Randomness


public class Main {
    public static void main(String[] args) {
System.out.println("C.E.A.S.A.R. starting...");
        JFrame frame = new JFrame("C.E.A.S.A.R.");
        frame.setSize(700, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        frame.setVisible(true);

        tabbedPane.addTab("Encrypt", EncryptTab.build());
        tabbedPane.addTab("Decrypt", DecryptTab.build());

        frame.add(tabbedPane);

    }
}




