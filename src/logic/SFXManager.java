package logic;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class SFXManager {

    private File BtnFile;
    private File startFile;
    private File endFile;
    private FloatControl fcBtn;
    private FloatControl fcStart;
    private FloatControl fcEnd;
    private float currentDB;
    private AudioInputStream streamBtn;
    private AudioInputStream streamStart;
    private AudioInputStream streamEnd;
    private Clip btn;
    private Clip start;
    private Clip end;
    private static SFXManager manager;

    //singleton
    public static SFXManager getinstance() {
        if (manager == null) {
            manager = new SFXManager();
        }
        return manager;
    }

    private SFXManager() {

        currentDB = Float.parseFloat(FileManager.getData(FileManager.SETTINGS_PROP, "sfxdb", "0"));
        BtnFile = new File(FileManager.getData(FileManager.SETTINGS_PROP, "buttonPress", "./textures/buttonPress.wav"));
        startFile = new File(FileManager.getData(FileManager.SETTINGS_PROP, "trainStart", "./textures/trainStart.wav"));
        endFile = new File(FileManager.getData(FileManager.SETTINGS_PROP, "trainEnd", "./textures/trainEnd.wav"));

        try {
            streamBtn = AudioSystem.getAudioInputStream(BtnFile);
            btn = AudioSystem.getClip();
            btn.open(streamBtn);
            fcBtn = (FloatControl) btn.getControl(FloatControl.Type.MASTER_GAIN);
            fcBtn.setValue(currentDB);

            streamStart = AudioSystem.getAudioInputStream(startFile);
            start = AudioSystem.getClip();
            start.open(streamStart);
            fcStart = (FloatControl) start.getControl(FloatControl.Type.MASTER_GAIN);
            fcStart.setValue(currentDB);
            streamEnd = AudioSystem.getAudioInputStream(endFile);
            end = AudioSystem.getClip();
            end.open(streamEnd);
            fcEnd = (FloatControl) end.getControl(FloatControl.Type.MASTER_GAIN);
            fcEnd.setValue(currentDB);

        } catch (UnsupportedAudioFileException e) {
            JOptionPane.showMessageDialog(null, "Sfx must be in .waw format");
        } catch (IOException e) {
            System.out.println("IO error loading sfx");
        } catch (LineUnavailableException e) {
            System.out.println("Line Unavailable Error");
        }
    }

    public void setVolume(int vol) {

        //  fc.setValue( Math.min(fc.getMaximum(), Math.max(fc.getMinimum(), vol)));
        float db = (float) (Math.log(vol / 100.0) / Math.log(10.0) * 20.0); //malefic vol to db conversion muhahahahah
        fcBtn.setValue(db);
        fcStart.setValue(db);
        fcEnd.setValue(db);
        FileManager.setData(FileManager.SETTINGS_PROP, "sfxdb", db + "");
    }

    public void buttonSfx() {
        if (btn.isRunning()) {
            btn.stop();
        }
        btn.setFramePosition(0);
        btn.start();
    }

    public void startSfx() {
        if (start.isRunning()) {
            start.stop();
        }
        start.setFramePosition(0);
        start.start();
    }

    public void endSfx() {
        if (end.isRunning()) {
            end.stop();
        }
        end.setFramePosition(0);
        end.start();
    }
}

