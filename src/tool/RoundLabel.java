package tool;

import profileui.ImageIconForPhoto;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Purpose of the class is to creat a round label to contain profile photo
 *
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class RoundLabel extends JLabel {
    public static int photoSize = 128;

    public RoundLabel(String path) {
        this.setPreferredSize(new Dimension(photoSize, photoSize));
        this.setLayout(new BorderLayout());
        this.setHorizontalAlignment(CENTER);
        this.setVerticalAlignment(CENTER);
        ImageIconForPhoto icon = new ImageIconForPhoto(path);
        this.setIcon(icon);
    }

    public void paint(Graphics g) {
        RoundRectangle2D.Double rect = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), photoSize, photoSize);
        g.setClip(rect);
        super.paint(g);
    }
}
