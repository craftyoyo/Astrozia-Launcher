package net.craftyoyo.astrozia;

import fr.theshark34.swinger.Swinger;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InfosFrame extends JDialog{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static InfosFrame instance;

    public static InfosFrame getInstance() {
        if (instance == null)
            instance = new InfosFrame();
        return instance;
    }

    public InfosFrame() {
        super(LauncherFrame.getInstance(), "Infos", true);
        setSize(280, 200);
        setResizable(false);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLocationRelativeTo(LauncherFrame.getInstance());
        setContentPane(buildContentPane());
        //setVisible(true);

    }

    public JPanel buildContentPane(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(new java.awt.Color(50, 50, 50));

       JLabel info = new JLabel("<html><center><font size=6><b>© Astrozia - 2019</b></font></center><br>"
                + "Launcher créer par craftyoyo.<br>"
                + "Toute copie intégrale ou partielle est interdite sous <br>peine de poursuites.<br>"
                + "<br>Version Launcher : <font color=#31C43F>0.0.1 BETA</font>"
                + "<br>Version Client : <font color=#31C43F>NOPE :D</font>"
                + "<br>Version Serveur : <font color=#31C43F>NOPE :D</font></html>" );
        info.setForeground(Color.white);
        info.setFont(getCustomFont().deriveFont(12F));
        panel.add(info);

        return panel;
    }
    public static Font getCustomFont(){
        try {
            InputStream inputStream = new BufferedInputStream(LauncherFrame.class.getResourceAsStream(Swinger.getResourcePath() + "/font/LouisGeorgeCafe.ttf"));
            return Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

}