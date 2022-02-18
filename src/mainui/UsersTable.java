package mainui;

import game2048_test.App;
import io.OperateDatabases;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * purpose of this class is to create a table which includes usernames and whose best record's taken time
 * <p>
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class UsersTable extends JTable {
    private final String[] title = new String[]{"Username", "Best record"};

    /**
     * purpose of this method is to set the data into the table and style of the table
     */
    public UsersTable() {

        DefaultTableModel model = new DefaultTableModel(dealWithData(), title) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        this.setModel(model);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
        sorter.setComparator(1, new Comparator<Object>() {
            public int compare(Object arg0, Object arg1) {
                try {
                    int a = Integer.parseInt(arg0.toString());
                    int b = Integer.parseInt(arg1.toString());
                    return a - b;
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        });

        this.setRowSorter(sorter);

        this.setRowHeight(25);
        this.setFont(new Font("Times New Roman", Font.PLAIN, 14));

        this.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));

        UsersTable finalThis = this;
        DefaultTableCellRenderer defaultCell = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (App.currentUser != null) {
                    if (finalThis.getValueAt(row, 0).equals(App.currentUser.username)) {
                        setForeground(Color.red);
                    } else {
                        setForeground(Color.BLACK);
                    }
                }
                setHorizontalAlignment(JLabel.CENTER);
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        this.setDefaultRenderer(Object.class, defaultCell);

        ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        this.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setSelectionBackground(new Color(184, 207, 229));
    }

    /**
     * purpose of this method is to deal with the data needed by the table
     */
    private String[][] dealWithData() {
        String[][] data = null;

        try {
            // Get an array for the table
            ResultSet count = App.statement.executeQuery("SELECT COUNT (*) AS rowCount from ProfileTable");
            data = new String[count.getInt("rowCount")][title.length];

            if (count.getInt("rowCount") > 0) {
                ResultSet resultSet = OperateDatabases.selectData(App.statement, null, "bestRecord");
                while (resultSet.next()) {
                    data[resultSet.getRow() - 1][0] = resultSet.getString("username");
                    data[resultSet.getRow() - 1][1] = resultSet.getInt("bestRecord") == 0 ? "null" : resultSet.getInt("bestRecord") + " s";
                }
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }


}
