package game2048_test;

import loginui.LoginUI;
import threadforgame.GetDataThread;
import threadforgame.GetLoginUIThread;
import mainui.MainUI;
import profileui.ProfileUI;
import threadforgame.GetProfileUIThread;
import users.User;

import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * The purpose of this class is to run the game
 * Notes: In order to save time for test, I change the win number from 2048 to 16 (WinNum = 16).
 * Because it will very long time if the number is too large. which means that any number is larger than 16,
 * it will judge that you win.
 * <p>
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class App {
    public static String photosLocation = "src" + File.separator + "image" + File.separator + "photos" + File.separator;
    public static String iconsLocation = "src" + File.separator + "image" + File.separator;
    public static User currentUser = null;
    public final static int interfaceSize = 4;// Purpose of this parameter is to decide the blockArray's size
    public final static int WinNum = 16;// You will win the game, if there is any number larger than WinNum
    public static Map<String, User> usersData = null;
    public static String userDataPath = "src" + File.separator + "userdata" + File.separator + "Data.dat";
    public static String profileDatabasePath = "src" + File.separator + "userdata" + File.separator + "ProfileDatabase.db";
    public static String[] profileDatabaseTitle = new String[]{"age", "gender", "intro", "bestRecord"};
    public static String[] profileDatabaseType = new String[]{"int", "char(64)", "char(128)", "int"};
    public static Statement statement = null;
    public static MainUI mainUI = null;
    public static LoginUI loginUI = null;
    public static ProfileUI profileUI = null;
    public static boolean ifEnd = false;// Judge that the game end or not
    public static boolean ifDeleteAccount = false;

    private final static GetDataThread getDataThread = new GetDataThread();

    public static void main(String[] args) {

        try {
            getDataThread.start();
            synchronized (getDataThread) {
                getDataThread.wait();
                mainUI = MainUI.getMainUI();//init main UI
                GetProfileUIThread profileUIThread = new GetProfileUIThread(mainUI);
                GetLoginUIThread loginUIThread = new GetLoginUIThread(mainUI);
                profileUIThread.start();
                loginUIThread.start();
            }
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }

    }

}
