package test;

import block.Block;
import org.junit.Assert;
import org.junit.Test;
import users.RegisteredUser;
import users.User;
/**
 * Purpose of the class is to test some methods in RegisteredUser class
 *
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class RegisteredUserTest {
    static User user = new RegisteredUser("Xiaobing","123456".toCharArray(), 30, "Male");

    @Test
    public void getInformationTest() {
        Assert.assertEquals("Username: Xiaobing; Age: 30; Gender: Male", user.getInformation());
    }

    @Test
    public void getTypeTest() {
        Assert.assertEquals("RegisteredUser", user.getType());
    }

    @Test
    public void setIntroduceTest() {
        user.setIntroduce("I am student in MET.");
        Assert.assertEquals("I am student in MET.", user.introduce);
    }

    @Test
    public void steDataTest() {
        Block b1 = new Block(2, new int[]{0, 0});
        Block b2 = new Block(0, new int[]{0, 1});
        Block b3 = new Block(4, new int[]{1, 0});
        Block b4 = new Block(0, new int[]{1, 1});
        Block[][] blocksArray = new Block[][]{
                {b1, b2},
                {b3, b4},
        };
        user.currentResult = "win";
        user.currentBlocksArrayData = blocksArray;
        user.currentTakeTime = 10000000;

        ((RegisteredUser) user).setData();

        Assert.assertArrayEquals(blocksArray, ((RegisteredUser) user).lastBlocksArrayData);
        Assert.assertEquals(10000000, ((RegisteredUser) user).lastTakeTime);

        Assert.assertArrayEquals(blocksArray, ((RegisteredUser) user).bestBlocksArrayData);
        Assert.assertEquals(10000000, ((RegisteredUser) user).bestTakeTime);
    }
}
