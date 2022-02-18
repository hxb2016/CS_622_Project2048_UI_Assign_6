package test;

import org.junit.Assert;
import org.junit.Test;
import users.UnRegisteredUser;
import users.User;
/**
 * Purpose of the class is to test some methods in UnRegisteredUser class
 *
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class UnRegisteredUserTest {
    static User user = new UnRegisteredUser();

    @Test
    public void getInformationTest() {
        Assert.assertEquals("You have not registered.", user.getInformation());
    }

    @Test
    public void getTypeTest() {
        Assert.assertEquals("UnRegisteredUser", user.getType());
    }

    @Test
    public void setIntroduceTest() {
        user.setIntroduce("I am student in MET.");
        Assert.assertEquals("You have not registered.", user.introduce);
    }
}
