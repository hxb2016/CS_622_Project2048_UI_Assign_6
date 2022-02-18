package loginui;

import game2048_test.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * purpose of this class is to create a login ui interface
 *
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class LoginUI extends JDialog {
    public JTextField userNameBox;
    public JPasswordField passwordBox;
    public JLabel ageTitle;
    public JTextField ageBox;
    public JLabel genderTitle;
    public JTextField genderBox;
    public LoginUIButton signIn;
    public LoginUIButton signUp;
    public LoginUIButton cancelSignUp;
    public LoginUIButton creatAccount;
    public LoginUIButton startAsGuest;
    public JPanel signInAndUp;
    public JPanel informationArea;
    private static LoginUI loginUI = null;

    private LoginUI(Frame owner) {
        super(owner);
        this.setResizable(false);
        this.setTitle("loginui");
        ImageIcon logo = new ImageIcon("src" + File.separator + "image" + File.separator + "2048.png");
        this.setIconImage(logo.getImage());
        this.setLayout(new BorderLayout());
        this.setSize(300, 400); // 设置大小
        this.setLocationRelativeTo(null); // 相对屏幕居中
        this.setModal(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (!App.ifEnd) {// If you have not started the game, the game will quit when you close the login pane.
                    System.exit(0);
                }
            }
        });

        //////////////////////////information area////////////////////////////////
        //this area includes username, password, age, gender, creatAccount and startAsGuest components
        this.informationArea = new JPanel();

        JLabel userNameTitle = new LoginUILabel("User name:", SwingConstants.CENTER);
        this.userNameBox = new LoginUITextField();

        JLabel passwordTitle = new LoginUILabel("Password:", SwingConstants.CENTER);
        this.passwordBox = new JPasswordField();
        this.passwordBox.setPreferredSize(new Dimension(200, 30));

        this.creatAccount = new LoginUIButton("Creat Account");
        this.startAsGuest = new LoginUIButton("Start as Guest");

        this.ageTitle = new LoginUILabel("Age:", SwingConstants.CENTER);
        this.ageBox = new LoginUITextField();

        this.genderTitle = new LoginUILabel("Gender:", SwingConstants.CENTER);
        this.genderBox = new LoginUITextField();


        this.informationArea.add(userNameTitle);
        this.informationArea.add(this.userNameBox);
        this.informationArea.add(passwordTitle);
        this.informationArea.add(this.passwordBox);
        this.informationArea.add(this.creatAccount);
        this.informationArea.add(this.startAsGuest);
        //////////////////////////information area end////////////////////////////

        this.signInAndUp = new JPanel();
        this.signInAndUp.setLayout(new GridLayout(1, 2));
        this.signInAndUp.setSize(this.getWidth(), 50);

        this.signIn = new LoginUIButton("Sign in");// sign in button
        this.signInAndUp.add(this.signIn, BorderLayout.CENTER);

        this.signUp = new LoginUIButton("Sign up");// sign up button
        this.cancelSignUp = new LoginUIButton("Cancel");

        this.add(this.signInAndUp, BorderLayout.SOUTH);
        this.add(this.informationArea, BorderLayout.CENTER);
    }

    /**
     * purpose of this method is to return a single loginUI
     */
    public static LoginUI getLoginUI(Frame owner) {
        if (loginUI == null) {
            loginUI = new LoginUI(owner);
            LoginUIController.setController(loginUI);
            loginUI.setVisible(true);
        }
        return loginUI;

    }

}
