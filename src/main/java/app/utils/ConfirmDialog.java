package app.utils;

import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class ConfirmDialog {

    public static boolean confirmDialog(Component parent, String message, String title) {
        JOptionPane pane = new JOptionPane(
                "<html><div style='width:250px; text-align:center;'>" + message + "</div></html>",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION);
        JDialog dialog = pane.createDialog(parent, title);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.setVisible(true);

        Object selectedValue = pane.getValue();
        return (selectedValue instanceof Integer) && ((int) selectedValue == JOptionPane.YES_OPTION);
    }
}
