package gui;

import logic.FileManager;
import logic.PanelManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class ManagedPanel extends JPanel {
    //package private mert el kell erjem ezt a leszarmazottakban
    PanelManager panelManager;
    BufferedImage img;

    public ManagedPanel(PanelManager p) {
        panelManager = p;
        setOpaque(false);
        try {
            img = ImageIO.read(new File(FileManager.getData(FileManager.SETTINGS_PROP, "bgImage", "./textures/backgroundImage.jpg")));

        } catch (IOException e) {
            System.out.println("Failed to load bgImage du to :" + e);
            img = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null) {
            g.drawImage(img, 0, 0, null);
        }
    }
}
