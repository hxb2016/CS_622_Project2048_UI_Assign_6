package loginui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * purpose of this class is to create button for LoginUI
 *
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class LoginUIButton extends JButton {
    public LoginUIButton(String text) {
        this.setText(text);
        this.setFocusPainted(false);
        if (!text.equals("Sign in") && !text.equals("Sign up") && !text.equals("Cancel")) {
            // This area is used to Create 'create account' and 'start as guest' button
            this.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            this.setOpaque(false);
            this.setContentAreaFilled(false);
            this.setBorderPainted(false);
            this.setForeground(Color.GRAY);
            LoginUIButton finalThis = this;
            this.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    finalThis.setForeground(new Color(18, 150, 219));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    finalThis.setForeground(Color.GRAY);
                }
            });
        } else {
            // This area is used to Create 'Sign in', 'Sign up' and 'Cancel' button
            this.setFont(new Font("Times New Roman", Font.BOLD, 18));
            this.setForeground(Color.WHITE);
            this.setBackground(new Color(18, 150, 219));
        }

    }
}
