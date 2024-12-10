import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//this panel is meant to be set as content panel in mainFrame
public class MenuPanel extends JPanel {
    private JButton startbtn;
    private JButton hostbtn;
    private JButton joinbtn;
    private JButton settingsbtn;
    private JPanel buttonsp;
    private BufferedImage mainTitle;
    private TitlePanel titlepanel;
    public MenuPanel() {

        //setLayout(new GridLayout(2,3,10,10));

        //main title add
        titlepanel=new TitlePanel();
        add(titlepanel);


        buttonsp=new JPanel();
        startbtn = new JButton("Start");
        startbtn.addActionListener((e) -> {
            System.out.println("Start was pressed");
        });

        hostbtn = new JButton("Host");
        hostbtn.addActionListener((e) -> {
            System.out.println("Hostbtn was pressed");
        });

        joinbtn = new JButton("Join");
        joinbtn.addActionListener((e) -> {
            System.out.println("joinbtn was pressed");
        });

        settingsbtn = new JButton("Settings");
        settingsbtn.addActionListener((e) -> {
            System.out.println("Settings was pressed");
            setBackground(Color.CYAN);
        });

        buttonsp.setLayout(new GridLayout(4, 1));

        buttonsp.add(startbtn);
        buttonsp.add(hostbtn);
        buttonsp.add(joinbtn);
        buttonsp.add(settingsbtn);
        add(buttonsp);
    }

}
