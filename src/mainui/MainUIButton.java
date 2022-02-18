package mainui;

import tool.KeyBoardListener;

import javax.swing.*;
import java.awt.*;

/**
 * the purpose of this class is to create a button for MainUI
 *
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class MainUIButton extends JButton {
    public MainUIButton(String text, ImageIcon icon) {
        if (text != null) {
            this.setText(text);
            this.setFont(new Font("Times New Roman", Font.BOLD, 18));
            this.setForeground(Color.WHITE);
        } else {
            this.setIcon(icon);
        }
        this.setMargin(new Insets(5, 10, 5, 10));
        this.setFocusPainted(false);
        this.setBackground(new Color(18, 150, 219));

        KeyBoardListener<MainUIButton> keyBoardListener = new KeyBoardListener<>(this);
        keyBoardListener.setListener();
    }
}
