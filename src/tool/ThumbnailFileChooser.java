package tool;

import javax.swing.*;
import javax.swing.filechooser.FileView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

/**
 * Purpose of this class is to make sure that user can see icon of picture in file chooser interface
 *
 * Author: Xiaobing Hou
 * Date: 02/12/2022
 * Course: CS-622
 */
public class ThumbnailFileChooser extends JFileChooser {
    // All preview icons will be this width and height
    private static final int ICON_SIZE = 20;

    // This blank icon will be used while previews are loading
    private static final Image LOADING_IMAGE = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);

    // Edit this to determine what file types will be previewed.
    private final Pattern imageFilePattern = Pattern.compile(".*\\.(png|jpe?g|gif)$", Pattern.CASE_INSENSITIVE);

    // Use a weak hash map to cache images until the next garbage collection (saves memory)
    private final Map<File, ImageIcon> imageCache = new WeakHashMap<>();

    public ThumbnailFileChooser() {
        super();
        this.setFileView(new ThumbnailView());
    }

    private class ThumbnailView extends FileView {
        // This thread pool is where the thumbnail icon loaders run
        private final ExecutorService executor = Executors.newCachedThreadPool();

        public Icon getIcon(File file) {
            if (!imageFilePattern.matcher(file.getName()).matches()) {
                return null;
            }
            // Our cache makes browsing back and forth lightning-fast! :D
            synchronized (imageCache) {
                ImageIcon icon = imageCache.get(file);
                if (icon == null) {
                    // Create a new icon with the default image
                    icon = new ImageIcon(LOADING_IMAGE);
                    // Add to the cache
                    imageCache.put(file, icon);
                    // Submit a new task to load the image and update the icon
                    executor.submit(new ThumbnailIconLoader(icon, file));
                }
                return icon;
            }
        }
    }

    private class ThumbnailIconLoader implements Runnable {
        private final ImageIcon icon;
        private final File file;

        public ThumbnailIconLoader(ImageIcon i, File f) {
            icon = i;
            file = f;
        }

        public void run() {
            // Load the image.
            ImageIcon newIcon = new ImageIcon(file.getAbsolutePath());

            // Make sure that the icon has the same ratio as the original image
            int iconWidth = (int) ((double) newIcon.getIconWidth() / newIcon.getIconHeight() * ICON_SIZE);

            // scale the image down, then replace the icon's old image with the new one.
            Image img = newIcon.getImage().getScaledInstance(iconWidth, ICON_SIZE, Image.SCALE_SMOOTH);
            icon.setImage(img);

            // Repaint the dialog so we see the new icon.
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    repaint();
                }
            });
        }
    }

}
