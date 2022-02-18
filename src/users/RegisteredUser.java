package users;

import block.Block;

/**
 * The purpose of this class is to create a registered user by given username, age and gender
 *
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class RegisteredUser extends User {
    public Block[][] lastBlocksArrayData;
    public int lastTakeTime;
    public Block[][] bestBlocksArrayData = null;
    public int bestTakeTime = 0;

    public String photoName = "";


    public RegisteredUser(String name, char[] password, int age, String gender) {
        super(name, password, age, gender);
    }

    /**
     * The purpose of setData method is to set data when the game end
     */
    public void setData() {
        this.lastBlocksArrayData = this.currentBlocksArrayData;
        this.lastTakeTime = this.currentTakeTime;
        if (this.currentResult.equals("win")) {
            if (this.bestBlocksArrayData == null) {
                this.bestTakeTime = this.currentTakeTime;
                this.bestBlocksArrayData = this.currentBlocksArrayData;
            } else {
                if (this.bestTakeTime > this.currentTakeTime) {
                    this.bestTakeTime = this.currentTakeTime;
                    this.bestBlocksArrayData = this.currentBlocksArrayData;
                }
            }
        }

    }

    /**
     * The purpose of is to exchange some data with other user
     */
    public void dataExchange(User other) {
        this.currentResult = other.currentResult;
        this.currentBlocksArrayData = other.currentBlocksArrayData;
        this.currentTakeTime = other.currentTakeTime;
    }

    /**
     * The purpose of getInformation method is to get username, age and gender
     */
    @Override
    public String getInformation() {
        return "Username: " + this.username + "; Age: " + this.age + "; Gender: " + this.gender;
    }

    /**
     * The purpose of getType method is to get type of user object
     */
    @Override
    public String getType() {
        return "RegisteredUser";
    }

    /**
     * The purpose of setIntroduce method is to set some information about users
     */
    @Override
    public void setIntroduce(String introduction) {
        this.introduce = introduction;
    }
}
