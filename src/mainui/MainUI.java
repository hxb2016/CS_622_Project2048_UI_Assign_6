package mainui;

import game2048_test.App;
import io.OperateDatabases;
import profileui.ProfilePhoto;
import users.RegisteredUser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * purpose of this class is to create the MainUI
 * <p>
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class MainUI extends JFrame {
    public MainUIBlockLabel[][] blocksArray = new MainUIBlockLabel[App.interfaceSize][App.interfaceSize];
    public JPanel recordPane = null;
    public JPanel buttonPane = null;
    public JPanel blocksArrayPane = null;
    public JPanel lastRecordOutPane;
    public JLabel lastTitleLabel = new JLabel("Last Record: Taken ... s", SwingConstants.CENTER);
    public MainUIBlockLabel[][] lastBlockArray = new MainUIBlockLabel[App.interfaceSize][App.interfaceSize];
    public JPanel lastRecord = new MainUIBlocksArrayPane(lastBlockArray, 10, 2);

    public JPanel bestRecordOutPane;
    public JLabel bestTitleLabel = new JLabel("best Record: Taken ... s", SwingConstants.CENTER);
    public MainUIBlockLabel[][] bestBlockArray = new MainUIBlockLabel[App.interfaceSize][App.interfaceSize];
    public JPanel bestRecord = new MainUIBlocksArrayPane(bestBlockArray, 10, 2);
    public ProfilePhoto profilePhoto = new ProfilePhoto(App.currentUser);
    public GameTimerPane timerPane = new GameTimerPane();
    public UsersScrollPane usersScrollPane = UsersScrollPane.getUsersScrollPane(App.usersData);
    public ChampionPanel ChampionPanel = new ChampionPanel();

    public JButton up = null;
    public JButton left = null;
    public JButton down = null;
    public JButton right = null;
    public JButton newGame = null;

    private static MainUI mainUI;

    /**
     * the purpose of this method is to init JFrame
     */
    private MainUI() throws SQLException {
        ImageIcon logo = new ImageIcon(App.iconsLocation + "2048.png");//get logo image
        this.setTitle("CS_622_Game_2048");
        this.setIconImage(logo.getImage());//set logo image
        this.setSize(720, 720);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setAlwaysOnTop(true);

        this.recordPane = new JPanel();
        this.recordPane.setBorder(new EmptyBorder(0, 0, 10, 0));
        this.recordPane.setLayout(new GridLayout(1, 3, -20, 0));
        this.recordPane.setPreferredSize(new Dimension(this.getWidth(), 220));

////////////////////////////////////////////////////////////////////////////////////
        lastRecordOutPane = new JPanel();
        lastRecordOutPane.setLayout(new BorderLayout());
        lastRecordOutPane.setBorder(new EmptyBorder(0, 20, 0, 20));
        lastRecordOutPane.add(this.lastTitleLabel, BorderLayout.NORTH);
        lastRecordOutPane.add(this.lastRecord, BorderLayout.CENTER);

        bestRecordOutPane = new JPanel();
        bestRecordOutPane.setLayout(new BorderLayout());
        bestRecordOutPane.setBorder(new EmptyBorder(0, 20, 0, 20));
        this.bestTitleLabel.setForeground(new Color(18, 150, 219));
        bestRecordOutPane.add(this.bestTitleLabel, BorderLayout.NORTH);
        bestRecordOutPane.add(this.bestRecord, BorderLayout.CENTER);

        JPanel timerAndPhotoPane = new JPanel();
        timerAndPhotoPane.setLayout(new BorderLayout());
        timerAndPhotoPane.add(profilePhoto, BorderLayout.CENTER);
        timerAndPhotoPane.add(timerPane, BorderLayout.SOUTH);
//////////////////////////////////////////////////////////////////////////////////////////////
        this.recordPane.add(timerAndPhotoPane);
        this.recordPane.add(bestRecordOutPane);
        this.recordPane.add(lastRecordOutPane);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        this.buttonPane = new JPanel();
        this.buttonPane.setPreferredSize(new Dimension(220, this.getHeight()));
        this.buttonPane.setLayout(new BorderLayout());

        this.newGame = new MainUIButton("New Game", null);

        JPanel operationButtonArea = new JPanel();
        operationButtonArea.setBorder(new EmptyBorder(0, 0, 10, 0));
        operationButtonArea.setLayout(new GridLayout(2, 1));
        ImageIcon upArrow = new ImageIcon(App.iconsLocation + "upArrow.png");
        ImageIcon leftArrow = new ImageIcon(App.iconsLocation + "leftArrow.png");
        ImageIcon downArrow = new ImageIcon(App.iconsLocation + "downArrow.png");
        ImageIcon rightArrow = new ImageIcon(App.iconsLocation + "rightArrow.png");
        this.up = new MainUIButton(null, upArrow);
        JPanel upPane = new JPanel();
        upPane.add(this.up);
        this.left = new MainUIButton(null, leftArrow);
        this.down = new MainUIButton(null, downArrow);
        this.right = new MainUIButton(null, rightArrow);
        JPanel leftDownRightPane = new JPanel();
        leftDownRightPane.add(this.left);
        leftDownRightPane.add(this.down);
        leftDownRightPane.add(this.right);
        operationButtonArea.add(upPane);
        operationButtonArea.add(leftDownRightPane);

        JPanel userTableAndChampionPanel = new JPanel();
        userTableAndChampionPanel.setLayout(new BorderLayout());

        userTableAndChampionPanel.add(ChampionPanel, BorderLayout.NORTH);
        ResultSet resultSet = OperateDatabases.selectBestData(App.statement, "bestRecord");
        ChampionPanel.setUserToPanel(resultSet.getString("username"), resultSet.getInt("bestRecord"));
        resultSet.close();

        userTableAndChampionPanel.add(usersScrollPane, BorderLayout.CENTER);

        this.buttonPane.add(this.newGame, BorderLayout.NORTH);
        this.buttonPane.add(userTableAndChampionPanel, BorderLayout.CENTER);
        this.buttonPane.add(operationButtonArea, BorderLayout.SOUTH);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        blocksArrayPane = new MainUIBlocksArrayPane(blocksArray, 25, 5);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.add(this.blocksArrayPane, BorderLayout.CENTER);
        this.add(this.buttonPane, BorderLayout.EAST);
        this.add(this.recordPane, BorderLayout.NORTH);
        this.setVisible(true);//window is visible
    }

    /**
     * Purpose of this method is to update the lastBlockArray and bestBlockArray
     */
    public void updateLastBestRecord(boolean ifInit) {
        if(!ifInit) {
            MainUIBlocksArrayPaneUpdate.updateUI(this.lastBlockArray, ((RegisteredUser) App.currentUser).lastBlocksArrayData, this.lastRecord);
            this.lastTitleLabel.setText("Last Record: Taken " + ((RegisteredUser) App.currentUser).lastTakeTime + " s");

            MainUIBlocksArrayPaneUpdate.updateUI(this.bestBlockArray, ((RegisteredUser) App.currentUser).bestBlocksArrayData, this.bestRecord);
            this.bestTitleLabel.setText("Best Record: Taken " + ((RegisteredUser) App.currentUser).bestTakeTime + " s");
        }else{
            this.lastTitleLabel.setText("Last Record: Taken ... s");
            this.bestTitleLabel.setText("Last Record: Taken ... s");

            lastRecordOutPane.remove(lastRecord);
            lastBlockArray = new MainUIBlockLabel[App.interfaceSize][App.interfaceSize];
            lastRecord = new MainUIBlocksArrayPane(lastBlockArray, 10, 2);
            lastRecordOutPane.add(lastRecord,BorderLayout.CENTER);
            lastRecordOutPane.updateUI();

            bestRecordOutPane.remove(bestRecord);
            bestBlockArray = new MainUIBlockLabel[App.interfaceSize][App.interfaceSize];
            bestRecord = new MainUIBlocksArrayPane(bestBlockArray, 10, 2);
            bestRecordOutPane.add(bestRecord,BorderLayout.CENTER);
            bestRecordOutPane.updateUI();
        }
    }

    /**
     * Purpose of this method is to return a single mainUI
     */
    public static MainUI getMainUI() throws SQLException {
        if (mainUI == null) {
            mainUI = new MainUI();
            MainUIController.setUIController(mainUI);
        }
        return mainUI;
    }


}
