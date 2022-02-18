package block;


import java.io.Serializable;

/**
 * Purpose of this class is to create a block object by gaven number and location or no argument
 *
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class Block implements Serializable {
    public static final long serialVersionUID = 1L;
    public int number = 0;
    public int[] location = null;
    public boolean ifCombine = false;

    public Block(int number, int[] location) {
        this.number = number;
        this.location = location;
    }

    public Block() {

    }

}
