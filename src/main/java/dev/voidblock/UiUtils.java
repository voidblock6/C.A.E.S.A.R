package dev.voidblock;

import javax.swing.*;
import java.awt.*;

public class UiUtils {

    public static void toggleVisibilityOnCheck(JCheckBox trigger, Component... targets) {
        // hide all targets initially
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

    public static spinnerPanel defaultEncryptUI(String label, String roundsLabel, int min, int max, int stepSize) {
        JLabel encryptlabel = new JLabel(label);
        JLabel encryptionRoundsLabel = new JLabel(roundsLabel);

        SpinnerNumberModel roundsModel = new SpinnerNumberModel(min, min, max, stepSize);
        JSpinner encryptSpinner = new JSpinner(roundsModel);



        JPanel encryptPanel = new JPanel((new GridLayout(0, 1, 0, 1)));
        JPanel spinners = new JPanel((new GridLayout(0, 2, 0, 1)));
        spinners.add(encryptionRoundsLabel);
        spinners.add(encryptSpinner);
        encryptPanel.add(encryptlabel);
        encryptPanel.add(spinners);


        return new spinnerPanel(encryptPanel, encryptSpinner);
    }

    public static class spinnerPanel {
        public JPanel panel;
        public JSpinner spinner;

        public spinnerPanel(JPanel panel, JSpinner spinner) {
            this.panel = panel;
            this.spinner = spinner;
        }
    }

    public static DoubleSpinnerPanel doubleSpinnerEncryptUI(String label, String roundsLabel, String spinnerlabel, int minOne, int maxOne, int stepSizeOne, int minTwo, int maxTwo, int stepSizeTwo) {
        JLabel encryptlabel = new JLabel(label);
        JLabel encryptionRoundsLabel = new JLabel(roundsLabel);
        JLabel parameterLabel = new JLabel(spinnerlabel);


        SpinnerNumberModel roundsModel = new SpinnerNumberModel(minOne, minOne, maxOne, stepSizeOne);
        JSpinner encryptSpinner = new JSpinner(roundsModel);
        SpinnerNumberModel parameterSpinnerModel = new SpinnerNumberModel(minTwo, minTwo, maxTwo, stepSizeTwo);
        JSpinner parameterSpinner  = new JSpinner(parameterSpinnerModel);

        JPanel encryptPanel = new JPanel((new GridLayout(0, 1, 0, 1)));
        JPanel spinners = new JPanel((new GridLayout(0, 2, 0, 1)));

        spinners.add(encryptionRoundsLabel);
        spinners.add(encryptSpinner);
        spinners.add(parameterLabel);
        spinners.add(parameterSpinner);

        encryptPanel.add(encryptlabel);
        encryptPanel.add(spinners);


        return new DoubleSpinnerPanel(encryptPanel, encryptSpinner, parameterSpinner);
    }

    public static class DoubleSpinnerPanel {
        public JPanel panel;
        public JSpinner spinner1;
        public JSpinner spinner2;

        public DoubleSpinnerPanel(JPanel panel, JSpinner spinner1, JSpinner spinner2) {
            this.panel = panel;
            this.spinner1 = spinner1;
            this.spinner2 = spinner2;
        }
    }
}
