package gui;

import logic.FileManager;
import logic.PanelManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class StatPanel extends ManagedPanel {

    private JPanel btnPanel;
    private JButton backbtn;
    private JButton resetStatBtn;
    private JLabel hany;
    private JLabel wpm;


    public StatPanel(PanelManager p) {
        super(p);
        setLayout(new GridLayout(10, 1));
        btnPanel = new JPanel();
        btnPanel.setOpaque(false);

        backbtn = new JButton("Back to Menu");
        resetStatBtn = new JButton("Reset Stats");
        btnPanel.add(backbtn);
        btnPanel.add(resetStatBtn);
        add(btnPanel);

        hany = new JLabel();
        wpm = new JLabel();

        add(hany);
        add(wpm);
        recalculateStats();

        //listeners
        backbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.goToMainMenu();
            }
        });
        resetStatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showOptionDialog(null, "Do you really want to reset the stats file, all will be lost?", "Reset stats", 0, JOptionPane.PLAIN_MESSAGE, null, null, null) == JOptionPane.OK_OPTION) {
                    File f = new File(FileManager.getData(FileManager.SETTINGS_PROP, "emptyFile", "./src/userScores.czn"));
                    FileManager.importData(FileManager.USER_PROP, f);
                    recalculateStats();
                }
            }
        });
    }

    private void recalculateStats() {
        int keysNum = FileManager.getKeysNum(FileManager.USER_PROP);
        hany.setText("C.F.R total train lags: " + keysNum + "\n");

        ArrayList<String> wpms = new ArrayList<>();
        for (int i = 0; i < keysNum; i++) {
            wpms.add(FileManager.getData(FileManager.USER_PROP, "wpm" + (i + 1), "0"));
        }
        //stream api hasznalata
        double atlagWpm = wpms.stream().mapToInt(Integer::parseInt).average().orElse(0);

        wpm.setText("Average delayed arrivals in minits (wpm): " + (int) atlagWpm + "\n");
    }
}
