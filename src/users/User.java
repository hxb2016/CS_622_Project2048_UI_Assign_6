package users;

import block.Block;

import java.io.Serializable;

/**
 * This class is an abstract class
 *
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public abstract class User implements Serializable {
    public static final long serialVersionUID = 1L;
    public String username;
    public char[] password;
    public int age;
    public String gender;
    public String introduce;
    public Block[][] currentBlocksArrayData;//it is used to creat number block interface
    public int currentTakeTime;// it is used to record the timer users take
    public String currentResult;// it is used to record win or fail

    public User() {//this constructor is used to creat Unregistered user
    }

    public User(String username, char[] password, int age, String gender) {//this constructor is used to creat Registered user
        this.username = username;
        this.password = password;
        this.age = age;
        this.gender = gender == null ? " " : gender;
    }

    public abstract String getInformation();//this method will return you name, age and gender as a String data type

    public abstract String getType();

    public abstract void setIntroduce(String introduce);
}
