package threadforgame;

import game2048_test.App;
import mainui.MainUI;
import profileui.ProfileUI;

/**
 * Purpose of this class is to create a GetProfileUIThread thread for init profileUI
 * <p>
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class GetProfileUIThread extends Thread {
    private final MainUI mainUI;

    public GetProfileUIThread(MainUI mainUI) {
        this.mainUI = mainUI;
    }

    @Override
    public void run() {
        App.profileUI = new ProfileUI(mainUI);//init profile UI
    }
}
