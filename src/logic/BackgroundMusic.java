package logic;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class BackgroundMusic {
    private FloatControl fc;
    private float currentDB;
    private AudioInputStream audioInputStream;
    private Clip bgMusik;
    private static BackgroundMusic manager;

    public static BackgroundMusic getInstance() {
        if (manager == null) {
            manager = new BackgroundMusic();
        }
        return manager;
    }

    private BackgroundMusic() {

        File f = new File(FileManager.getData(FileManager.SETTINGS_PROP, "bgMusik", "./textures/fishmusic.wav"));
        currentDB = Float.parseFloat(FileManager.getData(FileManager.SETTINGS_PROP, "bgMusikdb", "0"));
        try {
            audioInputStream = AudioSystem.getAudioInputStream(f);
            bgMusik = AudioSystem.getClip();
            bgMusik.open(audioInputStream);
            fc = (FloatControl) bgMusik.getControl(FloatControl.Type.MASTER_GAIN);
            fc.setValue(currentDB);
            bgMusik.start();


        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.out.println("Background Music Initialization Error: "+e);
        }

    }

    public void setVolume(int vol) {

        //  fc.setValue( Math.min(fc.getMaximum(), Math.max(fc.getMinimum(), vol)));
        currentDB = (float) (Math.log(vol / 100.0) / Math.log(10.0) * 20.0); //malefic vol to db conversion muhahahahah
        fc.setValue(currentDB);

        FileManager.setData(FileManager.SETTINGS_PROP, "bgMusikdb", currentDB + "");

    }


    public void changeMusic(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                JOptionPane.showMessageDialog(null, "File does not exist");
                return;
            }

            FileManager.setData(FileManager.SETTINGS_PROP, "bgMusik", path);
            if (bgMusik != null) {
                bgMusik.stop();
                audioInputStream.close();

            }
            audioInputStream = AudioSystem.getAudioInputStream(f);
            bgMusik = AudioSystem.getClip();
            bgMusik.open(audioInputStream);
            fc = (FloatControl) bgMusik.getControl(FloatControl.Type.MASTER_GAIN);
            fc.setValue(currentDB);
            bgMusik.start();

        } catch (IOException | LineUnavailableException e) {
            System.out.println("Error managing sound volume :"+e);
        } catch (UnsupportedAudioFileException e) {
            JOptionPane.showMessageDialog(null, "Music file is not waw format or damaged!");
        }


    }

}

