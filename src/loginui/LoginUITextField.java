package loginui;

import javax.swing.*;
import java.awt.*;
/**
 * purpose of this class is to create text box component for typing from users
 *
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class LoginUITextField extends JTextField {
    public LoginUITextField() {
        this.setPreferredSize(new Dimension(200,30));
        this.setForeground(new Color(18,150,219));
        this.setFont(new Font("Times New Roman",Font.BOLD,18));
    }
}
