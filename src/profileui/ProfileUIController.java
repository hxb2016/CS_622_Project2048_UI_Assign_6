package profileui;

import game2048_test.App;
import io.OperateDatabases;
import io.SaveUsersData;
import mainui.MainUIBlocksArrayPaneUpdate;
import operation.Operate;
import tool.*;
import users.RegisteredUser;
import users.UnRegisteredUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;

/**
 * Purpose of the class is to set listener for profile interface
 * <p>
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class ProfileUIController {
    /**
     * Purpose of the method is to set listener for profile interface
     */
    public static void setController(ProfileUIContent profileUIContent) {
        // set action listener for profilePhoto label in profileUIContent
        profileUIContent.profilePhoto.roundLabel.addMouseListener(new MouseAdapter() {
            final JFileChooser fc = FileChooserForPhoto.getFileChooser();

            @Override
            public void mouseClicked(MouseEvent e) {

                int returnVal = fc.showOpenDialog(App.mainUI);
                File file = fc.getSelectedFile();

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    //Update profile photo of profile panel
                    ImageIconForPhoto icon = new ImageIconForPhoto(file.getAbsolutePath());
                    profileUIContent.profilePhoto.roundLabel.setIcon(icon);

                    // Set new photo name into user object
                    int dotLocation = file.getName().lastIndexOf(".");
                    ((RegisteredUser) App.currentUser).photoName = App.currentUser.username + file.getName().substring(dotLocation);

                    try {
                        // Save user data
                        SaveUsersData.saveUsersData(App.usersData, App.userDataPath);

                        //Update profile photo of main panel
                        ImageIconForPhoto photo = new ImageIconForPhoto(file.getAbsolutePath());
                        App.mainUI.profilePhoto.roundLabel.setIcon(photo);

                        // Copy photo file into system
                        CopyFile.copyFile(file.getAbsolutePath());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                profileUIContent.profilePhoto.message.setText("Edit");
                profileUIContent.profilePhoto.roundLabel.add(profileUIContent.profilePhoto.message, BorderLayout.SOUTH);
                profileUIContent.profilePhoto.roundLabel.updateUI();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                profileUIContent.profilePhoto.roundLabel.remove(profileUIContent.profilePhoto.message);
                profileUIContent.profilePhoto.roundLabel.updateUI();
            }

        });

        // set action listener for username edit button
        profileUIContent.editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (profileUIContent.editButton.getText().equals("Edit")) {
                    profileUIContent.editButton.setText("Confirm");

                    profileUIContent.username.setEditable(true);
                    profileUIContent.password.setEditable(true);
                    profileUIContent.age.setEditable(true);
                    profileUIContent.gender.setEditable(true);
                    profileUIContent.introduction.setEditable(true);
                } else {
                    if (profileUIContent.username.getText().trim().equals("") || profileUIContent.password.getPassword().length == 0) {
                        OptionPane.setJOptionPaneMessage(App.mainUI, "Username and password can't be empty", "Message", null);
                    } else {
                        try {
                            OperateDatabases.deleteData(App.statement, App.currentUser.username);

                            App.usersData.remove(App.currentUser.username);
                            App.currentUser.username = profileUIContent.username.getText();
                            App.currentUser.password = profileUIContent.password.getPassword();
                            App.currentUser.age = Integer.parseInt(profileUIContent.age.getText().trim());
                            App.currentUser.gender = profileUIContent.gender.getText();
                            App.currentUser.introduce = profileUIContent.introduction.getText();
                            App.usersData.put(App.currentUser.username, App.currentUser);

                            // Save user profile information into database
                            OperateDatabases.insertData(App.statement, App.currentUser.username, App.currentUser.password, App.currentUser.age,
                                    App.currentUser.gender, App.currentUser.introduce, ((RegisteredUser) App.currentUser).bestTakeTime);

                            // Save user object data into file
                            SaveUsersData.saveUsersData(App.usersData, App.userDataPath);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        profileUIContent.editButton.setText("Edit");
                        profileUIContent.username.setEditable(false);
                        profileUIContent.password.setEditable(false);
                        profileUIContent.age.setEditable(false);
                        profileUIContent.gender.setEditable(false);
                        profileUIContent.introduction.setEditable(false);

                        // Update profileUIContent username
                        profileUIContent.profilePhoto.username.setText(App.currentUser.username);
                        // Update mainUI username
                        App.mainUI.profilePhoto.setUsername(App.currentUser.username);
                        // Update user table
                        App.mainUI.usersScrollPane.updateUsersTable();
                    }

                }

            }

        });

        // set action listener for delete account button
        profileUIContent.delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = OptionPane.setJOptionPaneConfirm(App.mainUI, "Delete your account?", "Message");
                if (option == JOptionPane.YES_OPTION) {
                    try {
                        OperateDatabases.deleteData(App.statement, App.currentUser.username);

                        App.usersData.remove(App.currentUser.username);
                        SaveUsersData.saveUsersData(App.usersData, App.userDataPath);

                        App.currentUser = new UnRegisteredUser();

                        App.profileUI.setVisible(false);

                        CreateBlockArrayData.creatBlockArrayData(App.interfaceSize, App.currentUser);
                        MainUIBlocksArrayPaneUpdate.updateUI(App.mainUI.blocksArray, App.currentUser.currentBlocksArrayData, App.mainUI.blocksArrayPane);

                        //Update profile photo of profile panel
                        ImageIconForPhoto icon = new ImageIconForPhoto(App.photosLocation + "profile1.png");
                        profileUIContent.profilePhoto.roundLabel.setIcon(icon);

                        //Update profile photo of main panel
                        ImageIconForPhoto photo = new ImageIconForPhoto(App.photosLocation + "profile1.png");
                        App.mainUI.profilePhoto.roundLabel.setIcon(photo);

                        // Update profileUIContent username
                        profileUIContent.profilePhoto.username.setText(App.currentUser.username);
                        // Update mainUI username
                        App.mainUI.profilePhoto.setUsername(App.currentUser.username);
                        // Update user table
                        App.mainUI.usersScrollPane.updateUsersTable();
                        // Update record panel
                        App.mainUI.updateLastBestRecord(true);

                        // Update champion panel
                        ResultSet resultSet = OperateDatabases.selectBestData(App.statement, "bestRecord");
                        App.mainUI.ChampionPanel.setUserToPanel(resultSet.getString("username"), resultSet.getInt("bestRecord"));
                        resultSet.close();

                        App.ifEnd = false;

                        // init profile page
                        ProfileUIContent.profileUIContent = null;

                        //init timer panel
                        UpdateTimerPane.endTimer();
                        App.mainUI.timerPane.setSecond("0 s");

                        Operate.ifStartOperate = false;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

    }
}
