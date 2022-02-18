package threadforgame;

import game2048_test.App;
import loginui.LoginUI;
import mainui.MainUI;
/**
 * Purpose of this class is to create a GetLoginUIThread thread for getting loginUI
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class GetLoginUIThread extends Thread {
    private final MainUI mainUI;

    public GetLoginUIThread(MainUI mainUI) {
        this.mainUI = mainUI;
    }

    @Override
    public void run() {
        App.loginUI = LoginUI.getLoginUI(mainUI);//init login UI
    }
}
