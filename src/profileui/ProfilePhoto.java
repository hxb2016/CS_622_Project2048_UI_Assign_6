package profileui;

import game2048_test.App;
import tool.RoundLabel;
import users.RegisteredUser;
import users.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Purpose of this class is to create a profile photo panel
 *
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class ProfilePhoto extends JPanel {
    public RoundLabel roundLabel;
    public JLabel username;
    public JLabel message;

    public ProfilePhoto(User user) {
        if (user == null || ((RegisteredUser) user).photoName.equals("")) {
            roundLabel = new RoundLabel(App.photosLocation + "profile1.png");
        } else {
            roundLabel = new RoundLabel(App.photosLocation + ((RegisteredUser) user).photoName);
        }

        message = new JLabel("Profile", SwingConstants.CENTER);
        message.setOpaque(true);
        message.setBackground(new Color(0, 0, 0, 100));
        message.setBorder(new EmptyBorder(0, 0, 5, 0));
        message.setForeground(Color.WHITE);
        message.setFont(new Font("Times New Roman", Font.BOLD, 12));

        username = new JLabel("", SwingConstants.CENTER);
        username.setPreferredSize(new Dimension(200, 20));
        username.setForeground(new Color(200, 170, 100));
        username.setFont(new Font("Times New Roman", Font.BOLD, 20));
        username.setLayout(new BorderLayout());

        JLabel lineLabel = new JLabel();
        lineLabel.setOpaque(true);
        lineLabel.setPreferredSize(new Dimension(150, 2));
        lineLabel.setBackground(new Color(200, 170, 100));

        this.setPreferredSize(new Dimension(150, 200));
        this.add(roundLabel);
        this.add(username);
        this.add(lineLabel);
    }

    /**
     * Purpose of the method is to set username into username box
     */
    public void setUsername(String username) {
        this.username.setText(username);
    }

}
