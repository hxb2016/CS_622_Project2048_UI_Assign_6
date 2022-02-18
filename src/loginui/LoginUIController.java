package loginui;

import game2048_test.App;
import io.OperateDatabases;
import io.SaveUsersData;
import profileui.ImageIconForPhoto;
import tool.OptionPane;
import tool.CreateBlockArrayData;
import mainui.MainUIBlocksArrayPaneUpdate;
import users.RegisteredUser;
import users.UnRegisteredUser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Arrays;

/**
 * purpose of this class is to set action listener of buttons in LoginUI
 * <p>
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class LoginUIController {
    public static void setController(LoginUI loginUI) {
        // Set controller for Sign in button
        loginUI.signIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginUI.userNameBox.getText().trim().equals("") ? " " : loginUI.userNameBox.getText();
                //If username in the local data, the user is a registered user
                if (App.usersData != null && App.usersData.containsKey(username)) {
                    //Judge that if the password is right
                    if (Arrays.equals(loginUI.passwordBox.getPassword(), App.usersData.get(username).password)) {
                        // If the game has been ended
                        if (!App.ifEnd) {
                            App.currentUser = App.usersData.get(username);
                            CreateBlockArrayData.creatBlockArrayData(App.interfaceSize, App.currentUser);
                            MainUIBlocksArrayPaneUpdate.updateUI(App.mainUI.blocksArray, App.currentUser.currentBlocksArrayData, App.mainUI.blocksArrayPane);
                        } else {
                            RegisteredUser newCurrentUser = (RegisteredUser) (App.usersData.get(username));
                            newCurrentUser.dataExchange(App.currentUser);
                            newCurrentUser.setData();//set the data to prepare for saving
                            App.usersData.put(newCurrentUser.username, newCurrentUser);
                            App.currentUser = newCurrentUser;
                            try {
                                ResultSet resultSet = OperateDatabases.selectData(App.statement, App.currentUser.username, null);
                                if (((RegisteredUser) App.currentUser).bestTakeTime < resultSet.getInt("bestRecord")) {
                                    OperateDatabases.changeBestRecord(App.statement, "bestRecord", App.currentUser.username, ((RegisteredUser) App.currentUser).bestTakeTime);
                                }
                                resultSet.close();
                                SaveUsersData.saveUsersData(App.usersData, App.userDataPath);
                                OptionPane.setJOptionPaneMessage(App.mainUI, "Successfully Login and Save!", "Message", null);
                            } catch (Exception ex) {
                                System.out.println("Error happened when save data.");
                                ex.printStackTrace();
                            }
                        }
                        loginUI.setVisible(false);

                        //Update record panel
                        if (((RegisteredUser) App.currentUser).lastBlocksArrayData != null) {
                            App.mainUI.updateLastBestRecord();
                        }

                        //Update username panel
                        App.mainUI.profilePhoto.setUsername(App.currentUser.username);

                        //Update user table
                        App.mainUI.usersScrollPane.updateUsersTable();

                        //Update profile photo
                        if (!((RegisteredUser) App.currentUser).photoName.equals("")) {
                            ImageIconForPhoto photo = new ImageIconForPhoto(App.photosLocation + ((RegisteredUser) App.currentUser).photoName);
                            App.mainUI.profilePhoto.roundLabel.setIcon(photo);
                        }
                    } else {
                        OptionPane.setJOptionPaneMessage(App.mainUI, "Wrong Password!", "Message", null);
                    }
                } else {
                    OptionPane.setJOptionPaneMessage(App.mainUI, "Sorry, you have no account!", "Message", null);
                }
            }
        });


        // Set controller for signUp button
        loginUI.signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (App.ifEnd) {
                    signUpAfterGame(loginUI);
                } else {
                    signUpBeforeGame(loginUI);
                }
                App.mainUI.usersScrollPane.updateUsersTable();
                //Update username panel
                App.mainUI.profilePhoto.setUsername(App.currentUser.username);
            }
        });

        // Set controller for creatAccount button
        loginUI.creatAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUI.informationArea.remove(loginUI.creatAccount);
                loginUI.informationArea.remove(loginUI.startAsGuest);
                loginUI.informationArea.add(loginUI.ageTitle);
                loginUI.informationArea.add(loginUI.ageBox);
                loginUI.informationArea.add(loginUI.genderTitle);
                loginUI.informationArea.add(loginUI.genderBox);

                loginUI.signInAndUp.remove(loginUI.signIn);
                loginUI.signInAndUp.add(loginUI.signUp);
                loginUI.signInAndUp.add(loginUI.cancelSignUp);

                loginUI.signInAndUp.updateUI();
                loginUI.informationArea.updateUI();
            }
        });

        // Set controller for cancelSignUp button
        loginUI.cancelSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                loginUI.informationArea.remove(loginUI.ageTitle);
                loginUI.informationArea.remove(loginUI.ageBox);
                loginUI.informationArea.remove(loginUI.genderTitle);
                loginUI.informationArea.remove(loginUI.genderBox);
                loginUI.informationArea.add(loginUI.creatAccount);
                loginUI.informationArea.add(loginUI.startAsGuest);

                loginUI.signInAndUp.remove(loginUI.signUp);
                loginUI.signInAndUp.remove(loginUI.cancelSignUp);
                loginUI.signInAndUp.add(loginUI.signIn);

                loginUI.signInAndUp.updateUI();
                loginUI.informationArea.updateUI();
            }
        });

        // Set controller for startAsGuest button
        loginUI.startAsGuest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.currentUser = new UnRegisteredUser();

                CreateBlockArrayData.creatBlockArrayData(App.interfaceSize, App.currentUser);
                MainUIBlocksArrayPaneUpdate.updateUI(App.mainUI.blocksArray, App.currentUser.currentBlocksArrayData, App.mainUI.blocksArrayPane);
                loginUI.setVisible(false);
            }
        });


    }

    /**
     * Purpose of this method is to be call by controller of signUp button when the game has not been started
     */
    public static void signUpBeforeGame(LoginUI loginUI) {
        String username = loginUI.userNameBox.getText().trim();
        char[] password = loginUI.passwordBox.getPassword();
        if (password.length != 0 && !username.equals("")) {
            if (App.usersData == null || !App.usersData.containsKey(username)) {
                int age = Integer.parseInt(loginUI.ageBox.getText().trim().equals("") ? "18" : loginUI.ageBox.getText());
                String gender = loginUI.genderBox.getText();
                App.currentUser = new RegisteredUser(username, password, age, gender);
                assert App.usersData != null;
                App.usersData.put(username, App.currentUser);

                try {
                    OperateDatabases.insertData(App.statement, App.currentUser.username, App.currentUser.password,
                            App.currentUser.age, App.currentUser.gender, App.currentUser.introduce, 0);

                    SaveUsersData.saveUsersData(App.usersData, App.userDataPath);
                    OptionPane.setJOptionPaneMessage(App.mainUI, "Good Job! Successfully registered!", "Message", null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                loginUI.setVisible(false);

                CreateBlockArrayData.creatBlockArrayData(App.interfaceSize, App.currentUser);
                MainUIBlocksArrayPaneUpdate.updateUI(App.mainUI.blocksArray, App.currentUser.currentBlocksArrayData, App.mainUI.blocksArrayPane);
                loginUI.setVisible(false);

            } else {
                OptionPane.setJOptionPaneMessage(App.mainUI, "User name already exists!", "Message", null);
            }
        } else {
            if (password.length == 0) {
                OptionPane.setJOptionPaneMessage(App.mainUI, "Password can't be empty!", "Message", null);
            }
            if (username.equals("")) {
                OptionPane.setJOptionPaneMessage(App.mainUI, "User name can't be empty!", "Message", null);
            }
        }

    }

    /**
     * Purpose of this method is to be call by controller of signUp button when the game has been ended
     */
    public static void signUpAfterGame(LoginUI loginUI) {
        String username = loginUI.userNameBox.getText().trim();
        char[] password = loginUI.passwordBox.getPassword();
        if (password.length != 0 && !username.equals("")) {
            if (App.usersData == null || !App.usersData.containsKey(username)) {
                int age = Integer.parseInt(loginUI.ageBox.getText().trim().equals("") ? "18" : loginUI.ageBox.getText());
                String gender = loginUI.genderBox.getText();
                RegisteredUser newCurrentUser = new RegisteredUser(username, password, age, gender);
                newCurrentUser.dataExchange(App.currentUser);
                newCurrentUser.setData();//set the data to prepare for saving
                App.usersData.put(newCurrentUser.username, newCurrentUser);
                App.currentUser = newCurrentUser;

                try {
                    OperateDatabases.insertData(App.statement, App.currentUser.username, App.currentUser.password,
                            App.currentUser.age, App.currentUser.gender, App.currentUser.introduce, ((RegisteredUser) App.currentUser).bestTakeTime);

                    SaveUsersData.saveUsersData(App.usersData, App.userDataPath);
                    OptionPane.setJOptionPaneMessage(App.mainUI, "Successfully Registered and Save!", "Message", null);
                    App.mainUI.updateLastBestRecord();
                } catch (Exception e) {
                    System.out.println("Error happened when save data.");
                    e.printStackTrace();
                }
                loginUI.setVisible(false);
            } else {
                OptionPane.setJOptionPaneMessage(App.mainUI, "User name already exists!", "Message", null);
            }
        } else {
            if (password.length == 0) {
                OptionPane.setJOptionPaneMessage(App.mainUI, "Password can't be empty!", "Message", null);
            }
            if (username.equals("")) {
                OptionPane.setJOptionPaneMessage(App.mainUI, "User name can't be empty!", "Message", null);
            }
        }
    }
}
