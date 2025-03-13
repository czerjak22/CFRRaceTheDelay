package gui;

import logic.PanelManager;
import javax.swing.*;

public class MainFrame extends JFrame {

    private PanelManager panelManager;

    public MainFrame() {
        panelManager = new PanelManager(this);
        setVisible(true);
        setBounds(100, 100, 600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("C.F.R. Race The Delay");
    }

    public void setContentPanel(JPanel p) {
        setContentPane(p);
        revalidate();
    }

    public static void main(String[] args) {

        MainFrame mf = new MainFrame();
    }
}
