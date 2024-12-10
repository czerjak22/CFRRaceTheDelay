import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private MenuPanel menuPanel;


    public MainFrame() {
        menuPanel = new MenuPanel();
        setContentPane(menuPanel);
    }

    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        mf.setVisible(true);
        mf.setBounds(100, 100, 600, 400);
        mf.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
