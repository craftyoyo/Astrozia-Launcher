package net.craftyoyo.astrozia;

import java.io.File;

import javax.swing.JFrame;

import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.util.WindowMover;

@SuppressWarnings("serial")
public class LauncherFrame extends JFrame {

	private static LauncherFrame instance;
	private LauncherPanel launcherPanel;

	public LauncherFrame()
	{
		this.setTitle("Astrozia");
		this.setSize(1280, 720);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setIconImage(Swinger.getResource("logo.png"));
		this.setContentPane(launcherPanel = new LauncherPanel());


		WindowMover mover = new WindowMover(this);
		this.addMouseListener(mover);
		this.addMouseMotionListener(mover);

		this.setVisible(true);
	}

	public static void main(String[] args) {

		//new LoadWebPage().setVisible(true);
		new File(GameDirGenerator.createGameDir("astrozia"), "Launcher"); // Version
		//System.out.println();

		System.out.println("Bonjour :)"+
                "\n**************************************************"+
                "\n          Created by craftyoyo                 "+
                "\n Si tu est un dev contact nous sur notre Discord :)"+
                "\n Le lien https://discord.gg/k8NHNAT"+
                "\n**************************************************");
		Swinger.setSystemLookNFeel();
		Swinger.setResourcePath("/net/craftyoyo/ressources/");
		instance = new LauncherFrame();
		new AePlayWave("HungerGames1.wav").start();
	}

	public static  LauncherFrame getInstance() {
		return instance;
	}
	public LauncherPanel getLauncherPanel(){
		return this.launcherPanel;
	}

}
