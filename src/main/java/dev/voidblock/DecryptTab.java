package dev.voidblock;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;

import static dev.voidblock.CryptoUtils.encrypt;
import static dev.voidblock.UiUtils.toggleVisibilityOnCheck;

public class DecryptTab {
    public static JPanel build() {
        JPanel DecryptPanel = new JPanel(new BorderLayout());
        DecryptPanel.setLayout(new BorderLayout());

        // input and output fields
        JTextArea encryptedTextInputField = new JTextArea(5, 30);
        JTextArea decryptedTextOutputField = new JTextArea(5, 30);
        JTextArea configInputField = new JTextArea(5,30);
        JTextArea keyInputField = new JTextArea(5,30);
        JTextArea consoleOutputField = new JTextArea(5,30);
        decryptedTextOutputField.setEditable(false);

        consoleOutputField.append("CryptoUtils loaded.. \n"  );
        consoleOutputField.append("Decryptor UI loaded.. \n" );
        consoleOutputField.append("C.E.A.S.A.R. startup finished! \n");




        JButton runButton = new JButton("Decrypt");
        runButton.addActionListener(e -> {

        });

        // top controls panel
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlsPanel.add(runButton);

        // text areas
        JPanel textPanel = new JPanel(new GridLayout(1, 3, 10, 0));



        //input box
        JPanel inputWrapper = new JPanel(new BorderLayout());
        inputWrapper.add(new JLabel("Encrypted Input:"), BorderLayout.NORTH);
        inputWrapper.add(new JScrollPane(encryptedTextInputField), BorderLayout.CENTER);

        //output field
        JPanel outputWrapper = new JPanel(new BorderLayout());
        outputWrapper.add(new JLabel("Decrypted Output:"), BorderLayout.NORTH);
        outputWrapper.add(new JScrollPane(decryptedTextOutputField), BorderLayout.CENTER);

        //config field
        JPanel configOutputWrapper = new JPanel(new BorderLayout());
        configOutputWrapper.add(new JLabel("Config Input:"), BorderLayout.NORTH);
        configOutputWrapper.add(new JScrollPane(configInputField), BorderLayout.CENTER);

        //key field
        JPanel keyOutputWrapper = new JPanel(new BorderLayout());
        keyOutputWrapper.add(new JLabel("De/Encryption key:"), BorderLayout.NORTH);
        keyOutputWrapper.add(new JScrollPane(keyInputField), BorderLayout.CENTER);

        //console field
        JPanel consoleOutputWrapper = new JPanel(new BorderLayout());
        consoleOutputWrapper.add(new JLabel("Console messages:"), BorderLayout.NORTH);
        consoleOutputWrapper.add(new JScrollPane(consoleOutputField), BorderLayout.CENTER);

        JPanel configKeyConsolePanel = new JPanel();
        configKeyConsolePanel.setLayout(new BoxLayout(configKeyConsolePanel, BoxLayout.Y_AXIS));
        configKeyConsolePanel.add(keyOutputWrapper);
        configKeyConsolePanel.add(configOutputWrapper);
        configKeyConsolePanel.add(consoleOutputWrapper);

        textPanel.add(inputWrapper);
        textPanel.add(configKeyConsolePanel);
        textPanel.add(outputWrapper);

        //assemble
        DecryptPanel.add(controlsPanel, BorderLayout.NORTH);
        DecryptPanel.add(textPanel, BorderLayout.CENTER);



        return DecryptPanel;
    }

}
