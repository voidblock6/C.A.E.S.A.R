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


}
