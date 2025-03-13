package gui;

import logic.FileManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TrainAnimPanel extends JPanel {

    BufferedImage train;
    BufferedImage track;
    private float szazalek;
    private int mozgoter;

    public TrainAnimPanel() {
        szazalek = 0;
        try {
            train = ImageIO.read(new File(FileManager.getData(FileManager.SETTINGS_PROP, "trainChar", "./textures/train1.png")));
            track = ImageIO.read(new File(FileManager.getData(FileManager.SETTINGS_PROP, "trainTrack", "./textures/sin.png")));
        } catch (IOException e) {
            System.out.println("Error loading player train!");
        }
        mozgoter = 600 - train.getWidth();
        setPreferredSize(new Dimension(600, track.getHeight() + train.getHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(track, 0, getHeight() - track.getHeight(), null);
        g.drawImage(train, (int) (mozgoter * szazalek), getHeight() - train.getHeight() - 12, null);
    }

    public void setSzazalek(float val) {
        szazalek = val;
        repaint();
    }

}
