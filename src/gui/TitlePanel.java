package gui;

import logic.FileManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TitlePanel extends JPanel {
    private BufferedImage img;

    public TitlePanel() {
        try {
            img = ImageIO.read(new File(FileManager.getData(FileManager.SETTINGS_PROP, "titleImage", "./textures/title.png")));
        } catch (IOException e) {
            img = null;
            System.out.println("ERROR: Failed to load Main menu title.png");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null) {
            g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        }
    }
}
