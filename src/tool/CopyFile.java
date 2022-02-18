package tool;

import game2048_test.App;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Purpose of the class is to copy file from one place to another
 *
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class CopyFile {
    public static void copyFile(String oldPath) {
        int dotLocation = oldPath.lastIndexOf(".");
        String end = oldPath.substring(dotLocation);
        File oldName = new File(oldPath);
        File newName = new File(App.photosLocation + App.currentUser.username + end);
        try {
            if (newName.exists()) {
                if (newName.delete()) {
                    Files.copy(oldName.toPath(), newName.toPath());
                }
            } else {
                Files.copy(oldName.toPath(), newName.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
