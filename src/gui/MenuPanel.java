package gui;

import logic.PanelManager;

import javax.swing.*;
import java.awt.*;

//this panel is meant to be set as content panel in mainFrame
public class MenuPanel extends ManagedPanel {
    private JButton startBtn;
    private JButton statsBtn;
    private JButton settingsBtn;
    private JPanel buttonsP;
    private TitlePanel titlePanel;

    public MenuPanel(PanelManager manager) {
        super(manager);
        setLayout(new GridLayout(2, 3, 10, 10));

        //main title add
        titlePanel = new TitlePanel();
        titlePanel.setOpaque(false);
        add(titlePanel);

        JPanel helperPanel = new JPanel();
        helperPanel.setOpaque(false);

        buttonsP = new JPanel();
        buttonsP.setOpaque(true);
        buttonsP.setLayout(new GridLayout(3, 1));

        startBtn = new JButton("Start");
        startBtn.addActionListener((e) -> {
            System.out.println("Start was pressed");
            //tell panel manager to change the panels
            panelManager.goToGame();
        });

        statsBtn = new JButton("Stats");
        startBtn.setPreferredSize(new Dimension(300, 50));
        statsBtn.addActionListener((e) -> {
            System.out.println("Stat was pressed");
            panelManager.goToStats();
        });

        settingsBtn = new JButton("Settings");
        settingsBtn.addActionListener((e) -> {
            System.out.println("Settings was pressed");
            panelManager.goToSettings();
        });

        buttonsP.add(startBtn);
        buttonsP.add(statsBtn);
        buttonsP.add(settingsBtn);
        helperPanel.add(buttonsP);
        add(helperPanel);
        setBackground(Color.black);
    }
}
