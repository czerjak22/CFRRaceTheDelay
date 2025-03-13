package logic;

import gui.GamePanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


//az egesz jatek objektumot egybe foglalja

public class Game {
    //game OBJ's
    private GamePanel gamePanel;
    private GameTimer gameTimer;

    private String text;
    private int remainingCharacters;
    private int charactersNum;
    private ArrayList<String> iratlanSzavak;
    private ArrayList<String> irottSzavak;
    private Random r;
    private SFXManager sfxManager;
    private PanelManager panelManager;

    public Game(PanelManager pm) {

        panelManager = pm;
        sfxManager = SFXManager.getinstance();
        r = new Random();
        gamePanel = new GamePanel(this);
        gameTimer = new GameTimer();
        text = getRandomText();
        gamePanel.textArenaSetText(text);
        gamePanel.setBestWpn(getBestWpm());

        //parsing text to words;
        iratlanSzavak = new ArrayList<>(Arrays.stream(text.split(" ")).toList());
        //stream api hasznalat
        remainingCharacters = iratlanSzavak.stream().mapToInt(x -> x.length()).sum();

        charactersNum = remainingCharacters;
        irottSzavak = new ArrayList<>();
        System.out.println(text);

    }

    public boolean checkSpelling(String input) {
        String substr;
        if (input.isEmpty()) {
            return false;
        }
        if (input.length() < iratlanSzavak.getFirst().length()) {
            substr = iratlanSzavak.getFirst().substring(0, input.length());
        } else if (input.length() > iratlanSzavak.getFirst().length()) {
            return false;
        } else {
            substr = iratlanSzavak.getFirst();
        }
        if (substr.equals(input)) {
            return true;
        }
        return false;
    }

    public boolean chekSpellingEndOfTheWord(String input) {
        if (input.isEmpty() || input.length() < iratlanSzavak.getFirst().length()) {
            return false;
        }
        if (input.equals(iratlanSzavak.getFirst())) {
            return true;
        }
        return false;
    }

    public void deleteWord() {
        irottSzavak.add(iratlanSzavak.getFirst());
        iratlanSzavak.removeFirst();
        remainingCharacters -= irottSzavak.getLast().length();

        //precent calculate to move train
        float szazalek = 1 - (remainingCharacters) / (float) charactersNum;
        gamePanel.moveTrain(szazalek);
        if (iratlanSzavak.isEmpty()) {
            gameOver();

        } else {

            String tmp = String.join(" ", irottSzavak);
            tmp += " ";
            tmp = tmp + String.join(" ", iratlanSzavak);
            gamePanel.textArenaSetText(tmp);

        }
    }

    private String getRandomText() {
        int allkeys = FileManager.getKeysNum(FileManager.DATA_PROP);
        int kivalasztott = r.nextInt(1, allkeys + 1);
        return FileManager.getData(FileManager.DATA_PROP, "sz" + kivalasztott, "ERROR GETTING TEXT");
    }

    public JPanel getPanel() {
        return gamePanel;
    }

    public void start() {
        sfxManager.startSfx();
        gameTimer.start();
    }

    public void restart() {
        panelManager.goToGame();
    }

    private void gameOver() {
        sfxManager.endSfx();
        gamePanel.gameOver();
        gameTimer.stopTimer();
        double time = gameTimer.getTime();
        System.out.println("Enyi ido alatt irtad le: " + time);
        int wpm = (int) Math.floor((irottSzavak.size()) / (time / (60000)));
        System.out.println("WPM: " + wpm);
        gamePanel.textArenaSetText("Nicely done!Your WPM: "+wpm);
        //save wpm
        int dbszam = FileManager.getKeysNum(FileManager.USER_PROP);
        FileManager.setData(FileManager.USER_PROP, "wpm" + (dbszam + 1), wpm + "");
    }

    public void goToMainMenu() {
        panelManager.goToMainMenu();
    }

    private int getBestWpm() {
        int getkeysNum = FileManager.getKeysNum(FileManager.USER_PROP);
        ArrayList<String> wpms = new ArrayList<>();
        for (int i = 0; i < getkeysNum; i++) {
            wpms.add(FileManager.getData(FileManager.USER_PROP, "wpm" + (i + 1), "0"));
        }
        return (int) wpms.stream().mapToDouble(Double::parseDouble).max().orElse(0);
    }

}
