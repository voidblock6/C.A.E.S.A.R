package dev.voidblock;
import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;

import static dev.voidblock.CryptoUtils.encrypt;
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
            JCheckBox xorEncryptionToggle = new JCheckBox("Enable XOR Encryption");

            JLabel xorRoundsLabel = new JLabel("XOR Rounds:");
            JSlider xorRoundsSlider = new JSlider(1, 10, 1);
            xorRoundsSlider.setMajorTickSpacing(1);
            xorRoundsSlider.setPaintTicks(true);
            xorRoundsSlider.setPaintLabels(true);
            toggleVisibilityOnCheck(xorEncryptionToggle, xorRoundsLabel, xorRoundsSlider);

            JCheckBox vignereEncryptionToggle = new JCheckBox("Enable Vignére Encryption");

            //Vignére
            JLabel vignereRoundsLabel = new JLabel("Vignére Rounds:");
            JSlider vignereRoundsSlider = new JSlider(1, 10, 1);
            vignereRoundsSlider.setMajorTickSpacing(1);
            vignereRoundsSlider.setPaintTicks(true);
            vignereRoundsSlider.setPaintLabels(true);
            toggleVisibilityOnCheck(vignereEncryptionToggle, vignereRoundsLabel, vignereRoundsSlider);

            //Caesar
            JCheckBox caesarEncryptionToggle = new JCheckBox("Enable Caesar Chiper Encryption");

            JLabel caesarShiftLabel = new JLabel("Caesar Shift (1-225):");

            JSlider caesarRoundsSlider = new JSlider(1, 10, 1);
            caesarRoundsSlider.setMajorTickSpacing(1);
            caesarRoundsSlider.setPaintTicks(true);
            caesarRoundsSlider.setPaintLabels(true);
            SpinnerNumberModel shiftModel = new SpinnerNumberModel(1, 1, 255, 1);
            JSpinner caesarShiftSpinner = new JSpinner(shiftModel);

           toggleVisibilityOnCheck(caesarEncryptionToggle, caesarShiftSpinner, caesarShiftLabel, caesarRoundsSlider);


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

                int xorRounds;
                if (xorEncryptionToggle.isSelected()) {
                    xorRounds = xorRoundsSlider.getValue();
                } else {
                    xorRounds = 0;
                }

                int vignereRounds;
                if (vignereEncryptionToggle.isSelected()) {
                    vignereRounds = vignereRoundsSlider.getValue();
                } else {
                    vignereRounds = 0;
                }

                int caesarRounds;
                if (caesarEncryptionToggle.isSelected()) {
                    caesarRounds = caesarRoundsSlider.getValue();
                } else {
                    caesarRounds = 0;
                }

                int shift = (int) caesarShiftSpinner.getValue();
                CryptoUtils.EncryptionResult result = encrypt(encryptedRaw, keyBytes, xorRounds, vignereRounds, caesarRounds, shift);
                String finalData = result.data;
                String config = String.join("", result.operationOrder);

                outputField.setText(finalData);
                configOutputField.setText(config);
                keyOutputField.setText(generatedKey);
            });

            // top controls panel
            JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            controlsPanel.add(xorEncryptionToggle);
            controlsPanel.add(xorRoundsLabel);
            controlsPanel.add(xorRoundsSlider);
            controlsPanel.add(vignereEncryptionToggle);
            controlsPanel.add(vignereRoundsLabel);
            controlsPanel.add(vignereRoundsSlider);
            controlsPanel.add(caesarEncryptionToggle);
            controlsPanel.add(caesarRoundsSlider);
            controlsPanel.add(caesarShiftLabel);
            controlsPanel.add(caesarShiftSpinner);
            controlsPanel.add(runButton);

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


        JPanel configKeyConsolePanel = new JPanel();
            configKeyConsolePanel.setLayout(new BoxLayout(configKeyConsolePanel, BoxLayout.Y_AXIS));
            configKeyConsolePanel.add(keyLengthLabel);
            configKeyConsolePanel.add(keyLengthSlider);
            configKeyConsolePanel.add(keyOutputWrapper);
            configKeyConsolePanel.add(configOutputWrapper);
            configKeyConsolePanel.add(consoleOutputWrapper);

            textPanel.add(inputWrapper);
            textPanel.add(configKeyConsolePanel);
            textPanel.add(outputWrapper);

            //assemble
            encryptPanel.add(controlsPanel, BorderLayout.NORTH);
            encryptPanel.add(textPanel, BorderLayout.CENTER);



            return encryptPanel;
        }

    }


