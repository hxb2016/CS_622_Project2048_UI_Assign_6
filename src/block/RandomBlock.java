package block;

import tool.GetEmptyBlockLocation;
import users.User;

import java.util.List;
import java.util.Random;

/**
 * purpose of this class is to create a randomBlock in empty area of currentBlockArray of currentUser
 *
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class RandomBlock extends Block {
    public RandomBlock(User user) {
        super();
        // Get the empty blocks list location of current user
        List<int[]> enableLocations = GetEmptyBlockLocation.getEmptyBlockLocation(user.currentBlocksArrayData);
        Random random = new Random();
        // Get a random number
        int randomNum = random.nextInt(1, 3) * 2;
        // Get a random location from enableLocations
        int[] randomLocation = enableLocations.get(random.nextInt(enableLocations.size()));

        this.number = randomNum;
        this.location = randomLocation;
    }

}
