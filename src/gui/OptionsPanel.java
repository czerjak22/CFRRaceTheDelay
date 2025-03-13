package gui;

import logic.BackgroundMusic;
import logic.FileManager;
import logic.PanelManager;
import logic.SFXManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class OptionsPanel extends ManagedPanel {

    private final JPanel sliderPanel;
    private final JButton backBtn;
    private final JSlider soundSlider;
    private final JSlider sfxSlider;

    private final JPanel comboBoxPanel;
    private final JComboBox musicSelect;
    private final JComboBox trainSelect;

    //adding text to file
    private final JPanel addPanel;
    private final JButton sendBtn;
    private final JTextArea ta;
    private final BackgroundMusic bgMusik;
    private final SFXManager sfxManager;
    private final JLabel remainingLettersLabel;
    private final int maxChars = 200;

    //import settings export settings
    private final JPanel portingPanel;
    private final JButton exportBtn;
    private final JButton importBtn;
    private final JComboBox settingCb;

    public OptionsPanel(PanelManager pm) {
        super(pm);
        bgMusik = BackgroundMusic.getInstance();
        sfxManager = SFXManager.getinstance();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //slider panel
        sliderPanel = new JPanel();
        sliderPanel.setLayout(new GridLayout(2, 3));
        sliderPanel.setOpaque(false);

        soundSlider = new JSlider(0, 100, 20);
        soundSlider.setOpaque(false);
        soundSlider.setMajorTickSpacing(100 / 5);
        soundSlider.setMinorTickSpacing(100 / 10);
        soundSlider.setPaintTicks(true);
        soundSlider.setSnapToTicks(true);
        soundSlider.setPaintLabels(true);

        sfxSlider = new JSlider(0, 100, 20);
        sfxSlider.setOpaque(false);
        sfxSlider.setMajorTickSpacing(100 / 5);
        sfxSlider.setMinorTickSpacing(100 / 10);
        sfxSlider.setPaintTicks(true);
        sfxSlider.setSnapToTicks(true);
        sfxSlider.setPaintLabels(true);

        backBtn = new JButton("Back to Menu");
        sliderPanel.add(new JLabel("Sound Volume:"));
        sliderPanel.add(soundSlider);
        JPanel segedPanel=new JPanel();
        segedPanel.add(backBtn);
        segedPanel.setOpaque(false);
        sliderPanel.add(segedPanel);
        sliderPanel.add(new JLabel("SFX Volume:"));
        sliderPanel.add(sfxSlider);
        add(sliderPanel);

        //combo panel
        comboBoxPanel = new JPanel();
        comboBoxPanel.setOpaque(false);
        comboBoxPanel.add(new JLabel("Background Music: "));
        String[] s1 = {"1", "2"};
        musicSelect = new JComboBox(s1);
        comboBoxPanel.add(musicSelect);
        comboBoxPanel.add(new JLabel("Train select: "));
        String[] s2 = {"1", "2", "3"};
        trainSelect = new JComboBox(s2);
        comboBoxPanel.add(trainSelect);
        add(comboBoxPanel);

        //text manual entry panel
        addPanel = new JPanel();
        addPanel.setOpaque(false);
        sendBtn = new JButton("Set");
        ta = new JTextArea();
        ta.setOpaque(false);
        ta.setLineWrap(true);
        ta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ta.setCaretColor(Color.GREEN);
        ta.setForeground(Color.WHITE);
        ta.setPreferredSize(new Dimension(450, 70));
        addPanel.add(sendBtn);
        addPanel.add(ta);
        add(addPanel);
        remainingLettersLabel = new JLabel("Remaining chars: " + maxChars);
        remainingLettersLabel.setOpaque(false);
        remainingLettersLabel.setForeground(Color.WHITE);
        addPanel.add(remainingLettersLabel);

        //file manipulate panle
        portingPanel = new JPanel();
        portingPanel.setOpaque(false);
        exportBtn = new JButton("Export");
        importBtn = new JButton("Import");
        String[] s3 = {"Settings", "Texts", "User Scores"};
        settingCb = new JComboBox(s3);
        portingPanel.add(exportBtn);
        portingPanel.add(importBtn);
        portingPanel.add(settingCb);
        add(portingPanel);

        //initializates everyithing to the correct saved value
        loadStatesFromSave();

        //listeners
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.goToMainMenu();
            }
        });
        soundSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                FileManager.setData(FileManager.SETTINGS_PROP, "bgmusicSliderVal", soundSlider.getValue() + "");
                bgMusik.setVolume(soundSlider.getValue());
            }
        });
        musicSelect.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    FileManager.setData(FileManager.SETTINGS_PROP, "bgMusicIndex", musicSelect.getSelectedIndex() + "");
                    switch (musicSelect.getSelectedIndex()) {
                        case 0:
                            bgMusik.changeMusic("./textures/back1.wav");

                            break;
                        case 1:
                            bgMusik.changeMusic("./textures/fishmusic.wav");
                            break;
                    }
                }
            }
        });
        trainSelect.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    FileManager.setData(FileManager.SETTINGS_PROP, "trainSelect", trainSelect.getSelectedIndex() + "");
                    switch (trainSelect.getSelectedIndex()) {
                        case 0:
                            FileManager.setData(FileManager.SETTINGS_PROP, "trainChar", "./textures/train1.png");
                            break;
                        case 1:
                            FileManager.setData(FileManager.SETTINGS_PROP, "trainChar", "./textures/train2.png");
                            break;
                        case 2:
                            FileManager.setData(FileManager.SETTINGS_PROP, "trainChar", "./textures/train3.png");
                            break;
                    }
                }
            }
        });
        ta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                //minor bugg control+v nel -de nem erint semmit
                if (ta.getText().length() >= maxChars) {
                    e.consume();
                    remainingLettersLabel.setText("Remaining chars: 0");
                } else {
                    remainingLettersLabel.setText("Remaining chars: " + (maxChars - ta.getText().length() - 1));
                }
            }
        });
        exportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser k = new JFileChooser();
                k.setCurrentDirectory(new File("/home/czn/Desktop"));
                if (k.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    FileManager.exportData(settingCb.getSelectedIndex() + 1, k.getSelectedFile());
                }
            }
        });
        importBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser k = new JFileChooser();
                k.setCurrentDirectory(new File("/home/czn/Desktop"));
                if (k.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    FileManager.importData(settingCb.getSelectedIndex() + 1, k.getSelectedFile());
                }
            }
        });
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!ta.getText().isEmpty()) {
                    //if control+V bugg us used , crop text to 200 chars
                    if (ta.getText().length() > 200) {
                        ta.setText(ta.getText().substring(0, 200));
                    }
                    int k = FileManager.getKeysNum(FileManager.DATA_PROP);
                    FileManager.setData(FileManager.DATA_PROP, "sz" + (k + 1), ta.getText());
                    ta.setText("");
                }
            }
        });
        sfxSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                FileManager.setData(FileManager.SETTINGS_PROP, "bgmusicSliderVal", sfxSlider.getValue() + "");
                sfxManager.setVolume(sfxSlider.getValue());
            }
        });
    }

    private void loadStatesFromSave() {
        soundSlider.setValue(Integer.parseInt(FileManager.getData(FileManager.SETTINGS_PROP, "bgmusicSliderVal", "20")));
        musicSelect.setSelectedIndex(Integer.parseInt(FileManager.getData(FileManager.SETTINGS_PROP, "bgMusicIndex", "1")));
        sfxSlider.setValue(Integer.parseInt(FileManager.getData(FileManager.SETTINGS_PROP, "sfxSluderVal", "20")));
        trainSelect.setSelectedIndex(Integer.parseInt(FileManager.getData(FileManager.SETTINGS_PROP, "trainSelect", "0")));
    }
}
