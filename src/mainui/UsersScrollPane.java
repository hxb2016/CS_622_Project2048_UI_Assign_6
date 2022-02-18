package mainui;

import users.User;

import javax.swing.*;
import java.util.Map;

/**
 * Purpose of this class is to create a UsersScrollPane
 *
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class UsersScrollPane extends JScrollPane {
    public UsersTable usersTable = null;
    private Map<String, User> usersData = null;

    private static UsersScrollPane usersScrollPane = null;

    private UsersScrollPane(Map<String, User> usersData) {
        this.usersData = usersData;
        this.usersTable = new UsersTable();
        this.setViewportView(this.usersTable);
    }

    /**
     * Return a UsersScrollPane with current system style
     */
    public static UsersScrollPane getUsersScrollPane(Map<String, User> usersData) {
        if (usersScrollPane == null) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                usersScrollPane = new UsersScrollPane(usersData);
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                usersScrollPane = new UsersScrollPane(usersData);
                e.printStackTrace();
            }
        }
        return usersScrollPane;
    }

    /**
     * Purpose of this method is to update the UsersScrollPane
     */
    public void updateUsersTable() {
        this.usersTable = new UsersTable();
        this.setViewportView(usersTable);
    }

}
