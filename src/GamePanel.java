import javax.swing.*;
import java.awt.*;

public abstract class GamePanel extends JPanel {
    private JTextArea ta;
    private JTextField tf;
    public GamePanel()
    {
        ta=new JTextArea();
        tf=new JTextField();
    }
}
