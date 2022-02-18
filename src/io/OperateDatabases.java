package io;

import java.sql.*;
/**
 * Purpose of the class is to provide some methods to create table, insert, change, delete and select data in the databases
 *
 * Author: Xiaobing Hou
 * Date: 02/18/2022
 * Course: CS-622
 */
public class OperateDatabases {
    /**
     * Purpose of the method is to get connection and Statement
     */
    public static Statement getStatement(String[] cols, String[] types, String path) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + path);//Connect database, if not exist create
        Statement statement = connection.createStatement(); //Create a connection object

        //if there is no table in the databases create the tables below
        if (!ifTableExist(statement)) {
            String sql1 = "create table usernameAndPwd(id INTEGER PRIMARY KEY autoincrement, username char(64), password char(64))";

            StringBuilder sql2 = new StringBuilder("create table ProfileTable(id INTEGER PRIMARY KEY autoincrement,");
            for (int i = 0; i < cols.length; i++) {
                if (i != cols.length - 1) {
                    sql2.append(cols[i]).append(" ").append(types[i]).append(",");
                } else {
                    sql2.append(cols[i]).append(" ").append(types[i]).append(")");
                }
            }
            statement.executeUpdate(sql1); //Create table
            statement.executeUpdate(sql2.toString()); //Create table
        }
        return statement;
    }
    /**
     * Purpose of the method is to insert data into databases
     */
    public static void insertData(Statement statement, String username, char[] password, int age, String gender, String intro, int bestRecord) throws SQLException {

        String passwordStr = new String(password);

        String sql1 = "INSERT INTO usernameAndPwd VALUES(null,'" + username + "','" + passwordStr + "')";
        String sql2 = "INSERT INTO ProfileTable VALUES(null, " + age + ",'" + gender + "','" + intro + "'," + bestRecord + ")";

        statement.executeUpdate(sql1);//insert data
        statement.executeUpdate(sql2);//insert data
    }
    /**
     * Purpose of the method is to select data from databases
     */
    public static ResultSet selectData(Statement statement, String where, String orderBy) throws SQLException {
        String sql = null;
        if (where != null) {
            sql = "SELECT username, password, age, gender, intro, bestRecord FROM usernameAndPwd INNER JOIN ProfileTable ON usernameAndPwd.id = ProfileTable.id WHERE username = '" + where + "'";
        } else {
            sql = "SELECT username, password, age, gender, intro, bestRecord FROM usernameAndPwd INNER JOIN ProfileTable ON usernameAndPwd.id = ProfileTable.id ORDER BY " + orderBy;
        }
        return statement.executeQuery(sql);
    }
    /**
     * Purpose of the method is to select special data from databases
     */
    public static ResultSet selectBestData(Statement statement, String where) throws SQLException {
        String sql = "SELECT username, password, age, gender, intro, bestRecord, MIN(" + where + ") FROM usernameAndPwd INNER JOIN ProfileTable ON usernameAndPwd.id = ProfileTable.id WHERE bestRecord != 0";
        return statement.executeQuery(sql);
    }
    /**
     * Purpose of the method is to change data in databases
     */
    public static void changeBestRecord(Statement statement, String col, String where, int values) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT id FROM usernameAndPwd WHERE username = '" + where + "'");
        int id = resultSet.getInt("id");
        resultSet.close();

        String sql = "UPDATE ProfileTable SET " + col + " = " + values + " WHERE id = '" + id + "'";
        statement.executeUpdate(sql);//insert data
    }
    /**
     * Purpose of the method is to delete data from databases
     */
    public static void deleteData(Statement statement, String where) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT id FROM usernameAndPwd WHERE username = '" + where + "'");
        int id = resultSet.getInt("id");
        resultSet.close();

        String sql1 = "DELETE FROM usernameAndPwd WHERE id = " + id;
        String sql2 = "DELETE FROM ProfileTable WHERE id = " + id;
        statement.executeUpdate(sql1);//delete data
        statement.executeUpdate(sql2);//delete data
    }
    /**
     * Purpose of the method is to judge that whether tables exist in databases
     */
    public static boolean ifTableExist(Statement statement) {
        try {
            String sql = "SELECT * FROM ProfileTable";
            statement.executeQuery(sql);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
