package test;

import game2048_test.App;
import io.OperateDatabases;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.sql.*;

/**
 * Purpose of the class is to test the methods in OperateDatabases class
 *
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class OperateDatabasesTest {

    @Test
    public void insertDataAndSelectDataTest() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "src" + File.separator + "userdata" + File.separator + "ProfileDatabaseTest.db");
        Statement statement = connection.createStatement(); //Create a connection object
        statement.executeUpdate("DROP TABLE if EXISTS usernameAndPwd");
        statement.executeUpdate("DROP TABLE if EXISTS ProfileTable");

        String sql1 = "create table usernameAndPwd(id INTEGER PRIMARY KEY autoincrement, username char(64), password char(64))";
        StringBuilder sql2 = new StringBuilder("create table ProfileTable(id INTEGER PRIMARY KEY autoincrement,");
        for (int i = 0; i < App.profileDatabaseTitle.length; i++) {
            if (i != App.profileDatabaseTitle.length - 1) {
                sql2.append(App.profileDatabaseTitle[i]).append(" ").append(App.profileDatabaseType[i]).append(",");
            } else {
                sql2.append(App.profileDatabaseTitle[i]).append(" ").append(App.profileDatabaseType[i]).append(")");
            }
        }
        statement.executeUpdate(sql1); //Create table
        statement.executeUpdate(sql2.toString()); //Create table

        //Test for insertData()
        OperateDatabases.insertData(statement, "xiao", new char[]{'1', '2', '3'}, 23, "male", "hello every one", 20);
        OperateDatabases.insertData(statement, "doug", new char[]{'1', '2', '3', '4'}, 25, "female", "hello every one", 15);

        // Get data and order by bestRecord column (up)
        ResultSet resultSet = OperateDatabases.selectData(statement,null, "bestRecord");

        resultSet.next();
        Assert.assertEquals("doug", resultSet.getString("username"));
        Assert.assertEquals("1234", resultSet.getString("password"));
        resultSet.next();
        Assert.assertEquals("xiao", resultSet.getString("username"));
        Assert.assertEquals("123", resultSet.getString("password"));

        resultSet.close();
        connection.close();
        statement.close();
    }

    @Test
    public void selectBestDataTest() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "src" + File.separator + "userdata" + File.separator + "ProfileDatabaseTest.db");
        Statement statement = connection.createStatement(); //Create a connection object
        statement.executeUpdate("DROP TABLE if EXISTS usernameAndPwd");
        statement.executeUpdate("DROP TABLE if EXISTS ProfileTable");

        String sql1 = "create table usernameAndPwd(id INTEGER PRIMARY KEY autoincrement, username char(64), password char(64))";
        StringBuilder sql2 = new StringBuilder("create table ProfileTable(id INTEGER PRIMARY KEY autoincrement,");
        for (int i = 0; i < App.profileDatabaseTitle.length; i++) {
            if (i != App.profileDatabaseTitle.length - 1) {
                sql2.append(App.profileDatabaseTitle[i]).append(" ").append(App.profileDatabaseType[i]).append(",");
            } else {
                sql2.append(App.profileDatabaseTitle[i]).append(" ").append(App.profileDatabaseType[i]).append(")");
            }
        }
        statement.executeUpdate(sql1); //Create table
        statement.executeUpdate(sql2.toString()); //Create table

        //insert data
        OperateDatabases.insertData(statement, "xiao", new char[]{'1', '2', '3'}, 23, "male", "hello every one", 20);
        OperateDatabases.insertData(statement, "doug", new char[]{'1', '2', '3', '4'}, 25, "female", "hello every one", 15);
        OperateDatabases.insertData(statement, "alse", new char[]{'1', '2', '3', '4'}, 25, "female", "hello every one", 30);

        // test for selectBestData method
        ResultSet resultSet = OperateDatabases.selectBestData(statement,"bestRecord");

        Assert.assertEquals("doug", resultSet.getString("username"));

        resultSet.close();
        connection.close();
        statement.close();
    }

    @Test
    public void changeBestRecordTest() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "src" + File.separator + "userdata" + File.separator + "ProfileDatabaseTest.db");
        Statement statement = connection.createStatement(); //Create a connection object
        statement.executeUpdate("DROP TABLE if EXISTS usernameAndPwd");
        statement.executeUpdate("DROP TABLE if EXISTS ProfileTable");

        String sql1 = "create table usernameAndPwd(id INTEGER PRIMARY KEY autoincrement, username char(64), password char(64))";
        StringBuilder sql2 = new StringBuilder("create table ProfileTable(id INTEGER PRIMARY KEY autoincrement,");
        for (int i = 0; i < App.profileDatabaseTitle.length; i++) {
            if (i != App.profileDatabaseTitle.length - 1) {
                sql2.append(App.profileDatabaseTitle[i]).append(" ").append(App.profileDatabaseType[i]).append(",");
            } else {
                sql2.append(App.profileDatabaseTitle[i]).append(" ").append(App.profileDatabaseType[i]).append(")");
            }
        }
        statement.executeUpdate(sql1); //Create table
        statement.executeUpdate(sql2.toString()); //Create table

        //insert data
        OperateDatabases.insertData(statement, "xiao", new char[]{'1', '2', '3'}, 23, "male", "hello every one", 20);
        OperateDatabases.insertData(statement, "doug", new char[]{'1', '2', '3', '4'}, 25, "female", "hello every one", 15);
        OperateDatabases.insertData(statement, "alse", new char[]{'1', '2', '3', '4'}, 25, "female", "hello every one", 30);

        // test for changeBestRecord method
        OperateDatabases.changeBestRecord(statement,"bestRecord","alse",9);

        // select the data changed
        ResultSet resultSet = OperateDatabases.selectData(statement,"alse",null);

        Assert.assertEquals(9, resultSet.getInt("bestRecord"));

        resultSet.close();
        connection.close();
        statement.close();
    }

    @Test
    public void deleteDataTest() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "src" + File.separator + "userdata" + File.separator + "ProfileDatabaseTest.db");//Connect database, if not exist create
        Statement statement = connection.createStatement(); //Create a connection object
        statement.executeUpdate("DROP TABLE if EXISTS usernameAndPwd");
        statement.executeUpdate("DROP TABLE if EXISTS ProfileTable");

        String sql1 = "create table usernameAndPwd(id INTEGER PRIMARY KEY autoincrement, username char(64), password char(64))";
        StringBuilder sql2 = new StringBuilder("create table ProfileTable(id INTEGER PRIMARY KEY autoincrement,");
        for (int i = 0; i < App.profileDatabaseTitle.length; i++) {
            if (i != App.profileDatabaseTitle.length - 1) {
                sql2.append(App.profileDatabaseTitle[i]).append(" ").append(App.profileDatabaseType[i]).append(",");
            } else {
                sql2.append(App.profileDatabaseTitle[i]).append(" ").append(App.profileDatabaseType[i]).append(")");
            }
        }
        statement.executeUpdate(sql1); //Create table
        statement.executeUpdate(sql2.toString()); //Create table

        //insert data
        OperateDatabases.insertData(statement, "xiao", new char[]{'1', '2', '3'}, 23, "male", "hello every one", 20);
        OperateDatabases.insertData(statement, "doug", new char[]{'1', '2', '3', '4'}, 25, "female", "hello every one", 15);
        OperateDatabases.insertData(statement, "alse", new char[]{'1', '2', '3', '4'}, 25, "female", "hello every one", 30);

        // test for deleteData method
        OperateDatabases.deleteData(statement,"doug");

        // count the row
        ResultSet count = statement.executeQuery("SELECT COUNT (*) AS rowCount from ProfileTable");

        Assert.assertEquals(2, count.getInt("rowCount"));

        // test for deleteData method
        OperateDatabases.deleteData(statement,"xiao");

        // count the row
        count = statement.executeQuery("SELECT COUNT (*) AS rowCount from ProfileTable");

        Assert.assertEquals(1, count.getInt("rowCount"));

        count.close();
        connection.close();
        statement.close();
    }
}
