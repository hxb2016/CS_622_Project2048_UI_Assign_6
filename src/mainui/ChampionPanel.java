package mainui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
/**
 * Purpose of the class is to create a champion panel object
 *
 * Author: Xiaobing Hou
 * Date: 02/18/2022
 * Course: CS-622
 */
public class ChampionPanel extends JPanel {
    private final JLabel username;
    private final JLabel bestRecord;

    public ChampionPanel() {
        username = new JLabel();
        bestRecord = new JLabel();
        username.setHorizontalAlignment(SwingConstants.CENTER);
        username.setFont(new Font("Times New Roman", Font.ITALIC, 16));
        username.setForeground(Color.WHITE);
        bestRecord.setHorizontalAlignment(SwingConstants.CENTER);
        bestRecord.setFont(new Font("Times New Roman", Font.ITALIC, 16));
        bestRecord.setForeground(Color.WHITE);
        this.setLayout(new GridLayout(3, 1));
        this.setBackground(new Color(247, 124, 95));
        this.setBorder(new EmptyBorder(0, 5, 0, 5));

        JLabel title = new JLabel("Champion", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 16));
        title.setForeground(Color.WHITE);

        JPanel namePanel = new JPanel();
        namePanel.setOpaque(false);
        namePanel.setLayout(new BorderLayout());
        JLabel usernameTitle = new JLabel("Username: ", SwingConstants.RIGHT);
        usernameTitle.setFont(new Font("Times New Roman", Font.ITALIC, 16));
        usernameTitle.setForeground(Color.WHITE);
        namePanel.add(usernameTitle, BorderLayout.WEST);
        namePanel.add(username, BorderLayout.CENTER);

        JPanel recordPanel = new JPanel();
        recordPanel.setOpaque(false);
        recordPanel.setLayout(new BorderLayout());
        JLabel bestRecordTitle = new JLabel("Best Record: ", SwingConstants.RIGHT);
        bestRecordTitle.setFont(new Font("Times New Roman", Font.ITALIC, 16));
        bestRecordTitle.setForeground(Color.WHITE);
        recordPanel.add(bestRecordTitle, BorderLayout.WEST);
        recordPanel.add(bestRecord, BorderLayout.CENTER);

        this.add(title);
        this.add(namePanel);
        this.add(recordPanel);
    }

    /**
     * Purpose of the method is to set and update content to champion panel
     */
    public void setUserToPanel(String name, int bestRecord) {
        this.username.setText(name);
        this.bestRecord.setText(bestRecord + " s");
    }
}
