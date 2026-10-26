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

    public static JPanel defaultEncryptUI(String Label, String JPanelName, int min, int max, int stepSize) {
        JLabel encryptLabel = new JLabel(Label);
        SpinnerNumberModel shiftModel = new SpinnerNumberModel(min, min, max, stepSize);
        JSpinner encrypSpinner = new JSpinner(shiftModel);

        JPanel encryptPanel = new JPanel((new GridLayout(0, 1, 0, 1)));
        encryptPanel.add(encryptLabel);
        encryptPanel.add(encrypSpinner);

        return encryptPanel;

    }

}
