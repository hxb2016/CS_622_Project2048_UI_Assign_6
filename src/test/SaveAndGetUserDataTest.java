package test;

import io.GetUsersData;
import io.SaveUsersData;
import users.RegisteredUser;
import users.User;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Because you need save data first, and then you can get it, I combine the two test
 *
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class SaveAndGetUserDataTest {
    static String userDataPath = "src" + File.separator + "userdata" + File.separator + "DataTest.dat";

    @Test
    public void saveAndGetUsersDataTest() throws IOException, ClassNotFoundException {
        User user = new RegisteredUser("Xiaobing", "123456".toCharArray(), 30, "Male");
        Map<String, User> saveData = new HashMap<>();
        saveData.put(user.username, user);

        //Test saveUsersData()
        SaveUsersData.saveUsersData(saveData, userDataPath);

        //Test getUsersData()
        Map<String, User> gotData = GetUsersData.getUsersData(userDataPath);

        if (gotData != null) {
            Assert.assertEquals("Xiaobing", gotData.get("Xiaobing").username);
            Assert.assertEquals(30, gotData.get("Xiaobing").age);
            Assert.assertEquals("Male", gotData.get("Xiaobing").gender);
        }

    }
}
