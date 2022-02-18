package loginui;

import javax.swing.*;
import java.awt.*;
/**
 * Purpose of this class is to create label for user information title in LoginUI
 *
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class LoginUILabel extends JLabel {
    public LoginUILabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        this.setPreferredSize(new Dimension(100,30));
        this.setFont(new Font("Times New Roman",Font.BOLD,18));
    }
}
