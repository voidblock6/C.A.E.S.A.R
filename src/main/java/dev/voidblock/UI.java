package dev.voidblock;
import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static dev.voidblock.CryptoUtils.encrypt;


public class UI {
        public static void main(String[] args) {
            JFrame frame = new JFrame("C.E.A.S.A.R.");
            frame.setSize(700, 450);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // input and output fields
            JTextArea inputField = new JTextArea(5, 30);
            JTextArea outputField = new JTextArea(5, 30);
            JTextArea configOutputField = new JTextArea(5,30);
            outputField.setEditable(false);
            configOutputField.setEditable(false);

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


            //encryption
            String generatedKey = CryptoUtils.generateRandomKey(32);
            byte[] keyBytes = generatedKey.getBytes(StandardCharsets.UTF_8);



            JButton runButton = new JButton("Encrypt");
            runButton.addActionListener(e -> {
                byte[] encryptedRaw = inputField.getText().getBytes(StandardCharsets.UTF_8);

                int xorRounds;
                if (xorEncryptionToggle.isSelected()) {
                    xorRounds = xorRoundsSlider.getValue();
                    System.out.println("DEBUG: XOR encryption was selected for " + xorRounds + " Rounds!");
                } else {
                    xorRounds = 0;
                }

                int vignereRounds;
                if (vignereEncryptionToggle.isSelected()) {
                    vignereRounds = vignereRoundsSlider.getValue();
                    System.out.println("DEBUG: Vignere encryption was selected for " + vignereRounds + " Rounds!");
                } else {
                    vignereRounds = 0;
                }


                CryptoUtils.EncryptionResult result = encrypt(encryptedRaw, keyBytes, xorRounds, vignereRounds, 0, 0);
                String finalData = result.data;
                String config = String.join("", result.operationOrder);

                outputField.setText(finalData);
                configOutputField.setText(config);


            });

            // --- top controls panel ---
            JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            controlsPanel.add(xorEncryptionToggle);
            controlsPanel.add(xorRoundsLabel);
            controlsPanel.add(xorRoundsSlider);
            controlsPanel.add(vignereEncryptionToggle);
            controlsPanel.add(vignereRoundsLabel);
            controlsPanel.add(vignereRoundsSlider);
            controlsPanel.add(runButton);

            // text areas
            JPanel textPanel = new JPanel(new GridLayout(1, 3, 10, 0));

            //input box
            JPanel inputWrapper = new JPanel(new BorderLayout());
            inputWrapper.add(new JLabel("Input:"), BorderLayout.NORTH);
            inputWrapper.add(new JScrollPane(inputField), BorderLayout.CENTER);

            //output field
            JPanel outputWrapper = new JPanel(new BorderLayout());
            outputWrapper.add(new JLabel("Output:"), BorderLayout.NORTH);
            outputWrapper.add(new JScrollPane(outputField), BorderLayout.CENTER);

            //config field
            JPanel configOutputWrapper = new JPanel(new BorderLayout());
            configOutputWrapper.add(new JLabel("Config:"), BorderLayout.NORTH);
            configOutputWrapper.add(new JScrollPane(configOutputField), BorderLayout.CENTER);

            textPanel.add(inputWrapper);
            textPanel.add(outputWrapper);
            textPanel.add(configOutputWrapper);

            //assemble
            frame.add(controlsPanel, BorderLayout.NORTH);
            frame.add(textPanel, BorderLayout.CENTER);

            frame.setVisible(true);
        }

        public static void toggleVisibilityOnCheck(JCheckBox trigger, Component... targets) {
            for (Component target : targets) {
                target.setVisible(false);
            }

            trigger.addActionListener(e -> {
                boolean enabled = trigger.isSelected();
                for (Component target : targets) {
                    target.setVisible(enabled);
                }
                trigger.getParent().revalidate();
                trigger.getParent().repaint();
            });
        }
    }


