package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * The purpose of this class is to get data from local file (Data.dat)
 *
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class GetUsersData {
    /**
     * In the method, It uses Generic, because the type of the object call the method can be different
     */
    public static <T> T getUsersData(String path) throws IOException, ClassNotFoundException {
        if (new File(path).exists()) {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path));
            T o = (T) inputStream.readObject();
            inputStream.close();
            return o;
        } else {
            return null;
        }
    }

}
