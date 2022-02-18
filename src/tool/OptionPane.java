package tool;

import javax.swing.*;

/**
 * Purpose of this class is to show a dialog
 *
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class OptionPane {
    /**
     * Purpose of this class is to show a message dialog
     */
    public static void setJOptionPaneMessage(JFrame f, String content, String title, ImageIcon icon) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JOptionPane.showMessageDialog(f, content, title, JOptionPane.INFORMATION_MESSAGE, icon);
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(f, content, title, JOptionPane.INFORMATION_MESSAGE, icon);
            ex.printStackTrace();
        }
    }
    /**
     * Purpose of this class is to show a confirm dialog
     */
    public static int setJOptionPaneConfirm(JFrame f, String content, String title) {
        int userOption = -1;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            userOption = JOptionPane.showConfirmDialog(f, content, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userOption;
    }
}
