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
            outputField.setEditable(false);
            configOutputField.setEditable(false);
            keyOutputField.setEditable(false);

            JCheckBox xorEncryptionToggle = new JCheckBox("Enable XOR Encryption");

            JLabel xorRoundsLabel = new JLabel("XOR Rounds:");
            JSlider xorRoundsSlider = new JSlider(1, 10, 1);
            xorRoundsSlider.setMajorTickSpacing(1);
            xorRoundsSlider.setPaintTicks(true);
            xorRoundsSlider.setPaintLabels(true);
            toggleVisibilityOnCheck(xorEncryptionToggle, xorRoundsLabel, xorRoundsSlider);

            JCheckBox vignereEncryptionToggle = new JCheckBox("Enable Vignére Encryption");

            JLabel vignereRoundsLabel = new JLabel("Vignére Rounds:");
            JSlider vignereRoundsSlider = new JSlider(1, 10, 1);
            vignereRoundsSlider.setMajorTickSpacing(1);
            vignereRoundsSlider.setPaintTicks(true);
            vignereRoundsSlider.setPaintLabels(true);
            toggleVisibilityOnCheck(vignereEncryptionToggle, vignereRoundsLabel, vignereRoundsSlider);

            JCheckBox ceasarEncryptionToggle = new JCheckBox("Enable Ceasar Chiper Encryption");

            JLabel ceasarShiftLabel = new JLabel("Caesar Shift (1-225):");

            JSlider ceasarRoundsSlider = new JSlider(1, 10, 1);
            ceasarRoundsSlider.setMajorTickSpacing(1);
            ceasarRoundsSlider.setPaintTicks(true);
            ceasarRoundsSlider.setPaintLabels(true);
            SpinnerNumberModel shiftModel = new SpinnerNumberModel(1, 1, 255, 1);
            JSpinner ceasarShiftSpinner = new JSpinner(shiftModel);

            toggleVisibilityOnCheck(ceasarEncryptionToggle, ceasarShiftSpinner, ceasarShiftLabel, ceasarRoundsSlider);

            //encryption

            JButton runButton = new JButton("Encrypt");
            runButton.addActionListener(e -> {

                //key generation
                String generatedKey = CryptoUtils.generateRandomKey(32);
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

                int ceasarRounds;
                if (ceasarEncryptionToggle.isSelected()) {
                    ceasarRounds = ceasarRoundsSlider.getValue();
                } else {
                    ceasarRounds = 0;
                }

                int shift = (int) ceasarShiftSpinner.getValue();
                CryptoUtils.EncryptionResult result = encrypt(encryptedRaw, keyBytes, xorRounds, vignereRounds, ceasarRounds, shift);
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
            controlsPanel.add(ceasarEncryptionToggle);
            controlsPanel.add(ceasarRoundsSlider);
            controlsPanel.add(ceasarShiftLabel);
            controlsPanel.add(ceasarShiftSpinner);
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
            configOutputWrapper.add(new JLabel("Your Config:"), BorderLayout.NORTH);
            configOutputWrapper.add(new JScrollPane(configOutputField), BorderLayout.CENTER);

            //key field
            JPanel keyOutputWrapper = new JPanel(new BorderLayout());
            keyOutputWrapper.add(new JLabel("de/encryption key:"), BorderLayout.NORTH);
            keyOutputWrapper.add(new JScrollPane(keyOutputField), BorderLayout.CENTER);

            JPanel configKeyPanel = new JPanel();
            configKeyPanel.setLayout(new BoxLayout(configKeyPanel, BoxLayout.Y_AXIS));
            configKeyPanel.add(keyOutputWrapper);
            configKeyPanel.add(configOutputWrapper);

            textPanel.add(inputWrapper);
            textPanel.add(configKeyPanel);
            textPanel.add(outputWrapper);

            //assemble
            encryptPanel.add(controlsPanel, BorderLayout.NORTH);
            encryptPanel.add(textPanel, BorderLayout.CENTER);



            return encryptPanel;
        }

    }


