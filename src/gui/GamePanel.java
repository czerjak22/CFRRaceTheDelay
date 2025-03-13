package gui;

import logic.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends ManagedPanel {

    private JTextArea ta;
    private JTextField tf;
    private JPanel textP;

    private JPanel buttonsPanel;
    private JButton backBtn;
    private JButton restartBtn;
    private JLabel bestWpn;
    private TrainAnimPanel animPanel;
    private Game game;
    private boolean helyes;
    private boolean started;

    public GamePanel(Game gm) {
        super(null); //nem kell a panel manager mert ezt a game objektum menegeli
        game = gm;
        helyes = true;
        started = false;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        buttonsPanel = new JPanel();
        buttonsPanel.setPreferredSize(new Dimension(600, 130));
        buttonsPanel.setOpaque(false);
        backBtn = new JButton("back");
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.goToMainMenu();
            }
        });

        restartBtn = new JButton("new Game");
        restartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.restart();
            }
        });

        buttonsPanel.add(backBtn);
        buttonsPanel.add(restartBtn);
        buttonsPanel.add(new JLabel("Best wpm:"));
        bestWpn = new JLabel("99");
        buttonsPanel.add(bestWpn);
        add(buttonsPanel);

        //animation Slider
        animPanel = new TrainAnimPanel();
        animPanel.setOpaque(false);
        add(animPanel);

        //texts panel
        textP = new JPanel();
        textP.setOpaque(false);
        textP.setLayout(new BoxLayout(textP, BoxLayout.Y_AXIS));

        ta = new JTextArea("Lorem Ipsum sim dolor");
        ta.setLineWrap(true);
        ta.setCursor(Cursor.getDefaultCursor());
        ta.setMaximumSize(new Dimension(580, 500));
        ta.setRows(2);
        ta.setEditable(false);
        ta.setFont(ta.getFont().deriveFont(Font.BOLD, 12f));

        tf = new JTextField();
        tf.setMaximumSize(new Dimension(400, 60));
        tf.setForeground(Color.WHITE);
        tf.setFont(tf.getFont().deriveFont(Font.BOLD, 12f));
        tf.setOpaque(false);
        tf.setFocusable(true);
        SwingUtilities.invokeLater(tf::requestFocus);
        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (!started) {
                    started = true;
                    game.start();
                }
                if (e.getKeyChar() == ' ') {
                    helyes = game.chekSpellingEndOfTheWord(tf.getText().substring(0, tf.getText().length() - 1));
                    if (helyes) {
                        game.deleteWord();
                        ta.setBackground(Color.green);
                        tf.setText("");
                    } else {
                        ta.setBackground(Color.RED);
                    }

                } else {
                    helyes = game.checkSpelling(tf.getText());
                    if (helyes) {
                        ta.setBackground(Color.green);
                    } else {
                        ta.setBackground(Color.RED);
                    }
                }
            }
        });
        textP.add(Box.createVerticalGlue());
        textP.add(ta);
        textP.add(Box.createVerticalGlue());
        textP.add(tf);
        textP.add(Box.createVerticalGlue());

        add(textP, BorderLayout.SOUTH);
        setOpaque(false);
    }

    public void textArenaSetText(String t) {
        ta.setText(t);
        ta.setCaretPosition(4);
    }

    public void gameOver() {
        tf.setVisible(false);
    }

    public void moveTrain(float val) {
        animPanel.setSzazalek(val);
    }

    public void setBestWpn(int val) {
        bestWpn.setText(val + " ");
    }

}
