package dev.voidblock;
import javax.swing.*;
import java.awt.*;

public class UI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("C.E.A.S.A.R.");
        frame.setSize(450, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JCheckBox uppercaseToggle = new JCheckBox("Enable uppercase-only mode");

        JLabel roundsLabel = new JLabel("Rounds:");
        JSlider roundsSlider = new JSlider(1, 10, 1); // min, max, initial
        roundsSlider.setMajorTickSpacing(1);
        roundsSlider.setPaintTicks(true);
        roundsSlider.setPaintLabels(true);

        JTextArea inputField = new JTextArea(5, 30);
        JTextArea outputField = new JTextArea(5, 30);
        outputField.setEditable(false);

        JButton runButton = new JButton("Encrypt");
        runButton.addActionListener(e -> {
            String text = inputField.getText();
            int rounds = roundsSlider.getValue();
            // placeholder — plug in your actual encryption logic
            outputField.setText(text + " [encrypted x" + rounds + "]");
        });

        frame.add(uppercaseToggle);
        frame.add(roundsLabel);
        frame.add(roundsSlider);
        frame.add(new JLabel("Input:"));
        frame.add(new JScrollPane(inputField));
        frame.add(runButton);
        frame.add(new JLabel("Output:"));
        frame.add(new JScrollPane(outputField));

        frame.setVisible(true);
    }
}
