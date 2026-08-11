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
        decryptedTextOutputField.setEditable(false);


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

        JButton runButton = new JButton("Decrypt");
        runButton.addActionListener(e -> {

            //key generation
            String generatedKey = CryptoUtils.generateRandomKey(32);
            byte[] keyBytes = generatedKey.getBytes(StandardCharsets.UTF_8);


            byte[] encryptedRaw = encryptedTextInputField.getText().getBytes(StandardCharsets.UTF_8);

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

            decryptedTextOutputField.setText(finalData);
            configInputField.setText(config);
            keyInputField.setText(generatedKey);
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
        inputWrapper.add(new JLabel("Encrypted Input:"), BorderLayout.NORTH);
        inputWrapper.add(new JScrollPane(encryptedTextInputField), BorderLayout.CENTER);

        //output field
        JPanel outputWrapper = new JPanel(new BorderLayout());
        outputWrapper.add(new JLabel("Decrypted Output:"), BorderLayout.NORTH);
        outputWrapper.add(new JScrollPane(decryptedTextOutputField), BorderLayout.CENTER);

        //config field
        JPanel configOutputWrapper = new JPanel(new BorderLayout());
        configOutputWrapper.add(new JLabel("Your Config:"), BorderLayout.NORTH);
        configOutputWrapper.add(new JScrollPane(configInputField), BorderLayout.CENTER);

        //key field
        JPanel keyOutputWrapper = new JPanel(new BorderLayout());
        keyOutputWrapper.add(new JLabel("de/encryption key:"), BorderLayout.NORTH);
        keyOutputWrapper.add(new JScrollPane(keyInputField), BorderLayout.CENTER);

        JPanel configKeyPanel = new JPanel();
        configKeyPanel.setLayout(new BoxLayout(configKeyPanel, BoxLayout.Y_AXIS));
        configKeyPanel.add(keyOutputWrapper);
        configKeyPanel.add(configOutputWrapper);

        textPanel.add(inputWrapper);
        textPanel.add(configKeyPanel);
        textPanel.add(outputWrapper);

        //assemble
        DecryptPanel.add(controlsPanel, BorderLayout.NORTH);
        DecryptPanel.add(textPanel, BorderLayout.CENTER);



        return DecryptPanel;
    }

}
