package mainui;

import tool.KeyBoardListener;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * purpose of this class is to create single label by given round size and text size
 *
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class MainUIBlockLabel extends JLabel {

    private final int roundSize;
    private final Color color2 = new Color(238, 228, 218);
    private final Color color4 = new Color(238, 225, 201);
    private final Color color8 = new Color(243, 178, 122);
    private final Color color16 = new Color(246, 153, 101);
    private final Color color32 = new Color(247, 124, 95);
    private final Color color64 = new Color(247, 95, 59);
    private final Color color128 = new Color(237, 208, 115);
    private final Color color256 = new Color(237, 204, 98);
    private final Color color512 = new Color(237, 201, 80);
    private final Color color1024 = new Color(237, 197, 63);
    private final Color color2048 = new Color(237, 190, 50);

    private final Color numberColor1 = new Color(119, 110, 101);
    private final Color numberColor2 = Color.WHITE;

    public MainUIBlockLabel(int roundSize, int textSize) {
        this.roundSize = roundSize;
        this.setHorizontalAlignment(CENTER);
        this.setOpaque(true);
        this.setForeground(numberColor1);
        this.setFont(new Font("Times New Roman", Font.BOLD, textSize));

        KeyBoardListener<MainUIBlockLabel> keyBoardListener = new KeyBoardListener<>(this);
        keyBoardListener.setListener();
    }

    public void paint(Graphics g) {
        RoundRectangle2D.Double rect = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), roundSize, roundSize);
        g.setClip(rect);
        super.paint(g);
    }

    /**
     * purpose of this method is to set background and text color basis the number of the label
     */
    public void setColor() {
        int blockNum = this.getText().equals("") ? 0 : Integer.parseInt(this.getText());
        switch (blockNum) {
            case 2 -> {
                this.setBackground(color2);
                this.setForeground(numberColor1);
            }
            case 4 -> {
                this.setBackground(color4);
                this.setForeground(numberColor1);
            }
            case 8 -> {
                this.setBackground(color8);
                this.setForeground(numberColor1);
            }
            case 16 -> {
                this.setBackground(color16);
                this.setForeground(numberColor2);
            }
            case 32 -> {
                this.setBackground(color32);
                this.setForeground(numberColor2);
            }
            case 64 -> {
                this.setBackground(color64);
                this.setForeground(numberColor2);
            }
            case 128 -> {
                this.setBackground(color128);
                this.setForeground(numberColor2);
            }
            case 256 -> {
                this.setBackground(color256);
                this.setForeground(numberColor2);
            }
            case 512 -> {
                this.setBackground(color512);
                this.setForeground(numberColor2);
            }
            case 1024 -> {
                this.setBackground(color1024);
                this.setForeground(numberColor2);
            }
            case 2048 -> {
                this.setBackground(color2048);
                this.setForeground(numberColor2);
            }
            default -> {
                this.setBackground(new Color(205, 193, 180));
            }
        }
    }
}
