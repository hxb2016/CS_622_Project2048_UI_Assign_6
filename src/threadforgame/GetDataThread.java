package threadforgame;

import game2048_test.App;
import io.OperateDatabases;
import io.GetUsersData;

import java.util.HashMap;
/**
 * Purpose of this class is to create a GetDataThread thread for getting user data
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class GetDataThread extends Thread {
    public GetDataThread() {
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                App.statement = OperateDatabases.getStatement(App.profileDatabaseTitle, App.profileDatabaseType,App.profileDatabasePath);

                App.usersData = GetUsersData.getUsersData(App.userDataPath);//Get users' data
                if (App.usersData == null) {
                    App.usersData = new HashMap<>();
                }
            } catch (Exception e) {
                System.out.println("Error happened when get data.");
                e.printStackTrace();
            }
            this.notifyAll();
        }
    }
}
