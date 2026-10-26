package dev.voidblock;
import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;

import static dev.voidblock.CryptoUtils.encrypt;
import static dev.voidblock.UiUtils.defaultEncryptUI;
import static dev.voidblock.UiUtils.toggleVisibilityOnCheck;


public class EncryptTab {
    public static JPanel build() {
        JPanel encryptPanel = new JPanel(new BorderLayout());
            encryptPanel.setLayout(new BorderLayout());

            // input and output fields
            JTextArea inputField = new JTextArea(5, 30);
            JTextArea outputField = new JTextArea(5, 30);
            JTextArea configOutputField = new JTextArea(5,30);
            JTextArea keyOutputField = new JTextArea(5,30);
            JTextArea consoleOutputField = new JTextArea(5,30);
            outputField.setEditable(false);
            configOutputField.setEditable(false);
            keyOutputField.setEditable(false);
            consoleOutputField.setEditable(false);

            consoleOutputField.append("CryptoUtils loaded.. \n"  );
            consoleOutputField.append("Encryption Editor UI loaded.. \n" );
            consoleOutputField.append("C.E.A.S.A.R. startup finished! \n");


            //XOR
           JPanel XORPanel = defaultEncryptUI("XOR Rounds:", "XOR",1, 99, 1);


            //Vignére
            JLabel vignereRoundsLabel = new JLabel("Vignére Rounds:");
            JSlider vignereRoundsSlider = new JSlider(0, 10, 0);
            vignereRoundsSlider.setMajorTickSpacing(1);
            vignereRoundsSlider.setPaintTicks(true);
            vignereRoundsSlider.setPaintLabels(true);


            //Caesar
            JLabel ceasarRoundsLabel = new JLabel("Ceasar Rounds:");
            JLabel caesarShiftLabel = new JLabel("Caesar Shift (1-225):");
            JSlider caesarRoundsSlider = new JSlider(0, 10, 0);
            caesarRoundsSlider.setMajorTickSpacing(1);
            caesarRoundsSlider.setPaintTicks(true);
            caesarRoundsSlider.setPaintLabels(true);
            SpinnerNumberModel shiftModel = new SpinnerNumberModel(0, 0, 255, 1);
            JSpinner caesarShiftSpinner = new JSpinner(shiftModel);



           //Key length
           JLabel keyLengthLabel = new JLabel("Key Length (8-64): 32");

           JSlider keyLengthSlider = new JSlider(8, 64, 32);
           keyLengthSlider.setMajorTickSpacing(2);
           keyLengthSlider.setPaintTicks(true);
           keyLengthSlider.setPaintLabels(true);

        keyLengthSlider.addChangeListener(e -> {
            keyLengthLabel.setText("Key Length (8-64): " + keyLengthSlider.getValue());
        });



            //encryption

            JButton runButton = new JButton("Encrypt");
            runButton.addActionListener(e -> {

                //key generation
                String generatedKey = CryptoUtils.generateRandomKey(keyLengthSlider.getValue());
                byte[] keyBytes = generatedKey.getBytes(StandardCharsets.UTF_8);


                byte[] encryptedRaw = inputField.getText().getBytes(StandardCharsets.UTF_8);


                int xorRounds = XOR.getValue();
                int vignereRounds = vignereRoundsSlider.getValue();
                int caesarRounds = caesarRoundsSlider.getValue();
                int shift = (int) caesarShiftSpinner.getValue();
                CryptoUtils.EncryptionResult result = encrypt(encryptedRaw, keyBytes, xorRounds, vignereRounds, caesarRounds, shift);
                String finalData = result.data;
                String config = String.join("", result.operationOrder);

                outputField.setText(finalData);
                configOutputField.setText(config);
                keyOutputField.setText(generatedKey);
            });

            // Sidebar
            JPanel Sidebar = new JPanel((new GridLayout(0, 1, 0, 10)));

            Sidebar.add(XORPanel);

            Sidebar.add(vignereRoundsLabel);
            Sidebar.add(vignereRoundsSlider);

            Sidebar.add(ceasarRoundsLabel);
            Sidebar.add(caesarRoundsSlider);
            Sidebar.add(caesarShiftLabel);
            Sidebar.add(caesarShiftSpinner);



            // text areas
            JPanel textPanel = new JPanel(new GridLayout(1, 3, 10, 0));

            //input box
            JPanel inputWrapper = new JPanel(new BorderLayout());
            inputWrapper.add(new JLabel("Plaintext Input:"), BorderLayout.NORTH);
            inputWrapper.add(new JScrollPane(inputField), BorderLayout.CENTER);

            //output field
            JPanel outputWrapper = new JPanel(new BorderLayout());
            outputWrapper.add(new JLabel("Encrypted Output:"), BorderLayout.NORTH);
            outputWrapper.add(new JScrollPane(outputField), BorderLayout.CENTER);

            //config field
            JPanel configOutputWrapper = new JPanel(new BorderLayout());
            configOutputWrapper.add(new JLabel("Config Output:"), BorderLayout.NORTH);
            configOutputWrapper.add(new JScrollPane(configOutputField), BorderLayout.CENTER);

            //key field
            JPanel keyOutputWrapper = new JPanel(new BorderLayout());
            keyOutputWrapper.add(new JLabel("De/Encryption key:"), BorderLayout.NORTH);
            keyOutputWrapper.add(new JScrollPane(keyOutputField), BorderLayout.CENTER);

            //console field
            JPanel consoleOutputWrapper = new JPanel(new BorderLayout());
            consoleOutputWrapper.add(new JLabel("Console messages:"), BorderLayout.NORTH);
            consoleOutputWrapper.add(new JScrollPane(consoleOutputField), BorderLayout.CENTER);


        JPanel inputConsolePanel = new JPanel();
            inputConsolePanel.setLayout(new BoxLayout(inputConsolePanel, BoxLayout.Y_AXIS));
            inputConsolePanel.add(keyLengthLabel);
            inputConsolePanel.add(keyLengthSlider);
            inputConsolePanel.add(inputWrapper);
            inputConsolePanel.add(consoleOutputWrapper);

        JPanel keyConfigOutputPanel = new JPanel();
            keyConfigOutputPanel.setLayout(new BoxLayout(keyConfigOutputPanel, BoxLayout.Y_AXIS));
            keyConfigOutputPanel.add(keyOutputWrapper);
            keyConfigOutputPanel.add(configOutputWrapper);
            keyConfigOutputPanel.add(outputWrapper);


        //assemble sidebar


        //assemble text areas
        textPanel.add(inputConsolePanel);
        textPanel.add(keyConfigOutputPanel);

        //assemble all
        encryptPanel.add(Sidebar, BorderLayout.WEST);
        encryptPanel.add(textPanel, BorderLayout.CENTER);
        encryptPanel.add(runButton, BorderLayout.NORTH);

            return encryptPanel;
        }

    }


