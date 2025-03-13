package logic;

import gui.MainFrame;
import gui.MenuPanel;
import gui.OptionsPanel;
import gui.StatPanel;

import javax.swing.*;

public class PanelManager {

    private MainFrame mainFrame;
    private MenuPanel menuPanel;
    private OptionsPanel optionsPanel;
    private JPanel contentPanel;
    private SFXManager sfx;


    public PanelManager(MainFrame mf) {
        mainFrame = mf;
        menuPanel = new MenuPanel(this);
        contentPanel = menuPanel;
        mainFrame.setContentPanel(contentPanel);
        sfx = SFXManager.getinstance();
        BackgroundMusic.getInstance(); //int bgmusik ,nincs referencia mert nem kell

    }

    public void goToMainMenu() {
        contentPanel = menuPanel;
        mainFrame.setContentPanel(contentPanel);
        sfx.buttonSfx();
    }

    public void goToGame() {
        contentPanel = new Game(this).getPanel();
        mainFrame.setContentPanel(contentPanel);
        sfx.buttonSfx();
    }

    public void goToStats() {
        contentPanel = new StatPanel(this);
        mainFrame.setContentPanel(contentPanel);
        sfx.buttonSfx();
    }

    public void goToSettings() {
        if (optionsPanel == null) {
            optionsPanel = new OptionsPanel(this);
        }
        contentPanel = optionsPanel;
        mainFrame.setContentPanel(contentPanel);
        sfx.buttonSfx();
    }
}
