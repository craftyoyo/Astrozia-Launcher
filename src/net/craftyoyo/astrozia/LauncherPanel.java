package net.craftyoyo.astrozia;

import static fr.theshark34.swinger.Swinger.getResource;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.html.HTMLEditorKit;

import fr.litarvan.openauth.AuthenticationException;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;





@SuppressWarnings("serial")
public class LauncherPanel extends JPanel implements SwingerEventListener {
    private final JScrollPane scrollPane = new JScrollPane();
    private final JTextPane browser = new JTextPane();
	
	private Image backGround = Swinger.getResource("fond.jpg");
	private Saver saver = new Saver(new File(Launcher.MC_DIR,"launcher.properties"));
	private JTextField usernameField =new JTextField(this.saver.get("username"));
	private JPasswordField passwordField =new JPasswordField(this.saver.get("password"));
	private STexturedButton copyrightButton = new STexturedButton(getResource("copyright.png"), getResource("copyright_hover.png"));
	private STexturedButton playButton = new STexturedButton(Swinger.getResource("play.png"));
	private STexturedButton quitButton = new STexturedButton(getResource("close.png"));
	//private STexturedButton hideButton = new STexturedButton(getResource("hide.png"));
	private STexturedButton ramButton = new STexturedButton(getResource("settings.png"));
	
    private STexturedButton discordButton = new STexturedButton(getResource("discord.png"));
    private STexturedButton twitterButton = new STexturedButton(getResource("twitter.png"));
    private STexturedButton webButton = new STexturedButton(getResource("web.png"));
    private JEditorPane jep = new JEditorPane();
    private RamSelector ramSelector = new RamSelector(new File(Launcher.MC_DIR, "launcher.properties"));
	private SColoredBar progressBar = new SColoredBar(Swinger.getTransparentWhite(100),Swinger.getTransparentInstance(new Color(0,190,0), 175));
	private JLabel infoLabel = new JLabel("Cliquez sur Play ! ", SwingConstants.CENTER);
	

	public  LauncherPanel() {
		System.out.println("==========DEBUG INFO==========");
		System.out.println("RAM: "+saver.get("ram")+" Not working set by default 3Gb");
		System.out.println("USERNAME: "+saver.get("username"));
		System.out.println("INSTANCE FOLDER: "+Launcher.MC_DIR);
		System.out.println("==============================");
		this.setLayout(null);
		//Champ nom de compte 
		usernameField.setOpaque(false);
		usernameField.setBorder(null);
		usernameField.setForeground(Color.black);
		usernameField.setCaretColor(Color.black);
		usernameField.setFont(getCustomFont().deriveFont(18F));
		usernameField.setBounds(65, 252, 330, 31);
		this.add(usernameField);
		
		//Champ Mots de passe
		
		passwordField.setOpaque(false);
		passwordField.setBorder(null);
		passwordField.setForeground(Color.black);
		passwordField.setCaretColor(Color.black);
		passwordField.setFont(getCustomFont().deriveFont(18F));
		passwordField.setEchoChar('#');
		passwordField.setBounds(65, 325, 330, 31);
		this.add(passwordField);
		
		//Boutton play
		
		playButton.setBounds(65,380, 330, 76);
		playButton.addEventListener(this);
		this.add(playButton);
		
		//Boutton fermer
		
		this.quitButton.setBounds(1240, 6, 31, 31);
		this.quitButton.addEventListener(this);
		add(quitButton);
		
		//Boutton RAM
		
		this.ramButton.addEventListener(this);
		this.ramButton.setBounds(1200, 6, 31, 31);
		//add(ramButton);
		
		//Boutton social
		
		this.twitterButton.addEventListener(this);
		this.twitterButton.setBounds(1080, 640);
		add(twitterButton);
		
		this.discordButton.addEventListener(this);
		this.discordButton.setBounds(1140, 640);
		add(discordButton);
		
		this.webButton.addEventListener(this);
		this.webButton.setBounds(1200, 640);
		add(webButton);
		
		//Boutton Copyright
		
		copyrightButton.addEventListener(this);
	    copyrightButton.setBounds(1160, 6, 31, 31);
	    this.add(copyrightButton);
		
	    //Progress bar
	    
		progressBar.setBounds(0,700,1280,20);
		this.add(progressBar);
		//Label
		
		
		    MineStat ms = new MineStat("mc.hypixel.net", 25565);
		    System.out.println("Minecraft server status of " + ms.getAddress() + " on port " + ms.getPort() + ":");
		    if(ms.isServerUp())
		    {
		     System.out.println("Server is online running version " + ms.getVersion() + " with " + ms.getCurrentPlayers() + " out of " + ms.getMaximumPlayers() + " players.");
		     System.out.println("Message of the day: " + ms.getMotd());
		     System.out.println("Latency: " + ms.getLatency() + "ms");
		    }
		    else
		      System.out.println("Server is offline!");
		  
		
		
		infoLabel.setForeground(Color.WHITE);
		infoLabel.setFont(getCustomFont().deriveFont(12F));
		infoLabel.setBounds(0,700 , 1280,20 );
		this.add(infoLabel);
		
		//Label
		
		 if(ms.isServerUp())
		    { 
			 JLabel mcs = new JLabel("<html><p style=\"font-size: 15px;\">Server status: <b style=\"color: green;\">ONLINE!</b><br>"
		    
		+ "Online players: " + ms.getCurrentPlayers() + 
		"<br>Max players: " + ms.getMaximumPlayers() + 
		"<br>Ping: " + ms.getLatency() + "Ms</p></html>");
			 mcs.setForeground(Color.white);
		        mcs.setBounds(65,580 , 400,100 );
		        mcs.setFont(getCustomFont().deriveFont(12F));
		        this.add(mcs);}
		 else 
		 {
			 JLabel mcs = new JLabel("<html><p style=\"font-size: 15px;\">Server status: <b style=\"color: red;\">OFFLINE ;(</b>");
			 mcs.setForeground(Color.white);
		        mcs.setBounds(65,580 , 400,100 );
		        mcs.setFont(getCustomFont().deriveFont(12F));
		        this.add(mcs);
		 };
        
		
		JLabel info = new JLabel("<html><center><font size=6><b>Adresse mail mojang:</b></font></center><br></font></html>" );
        info.setForeground(Color.white);
        info.setBounds(65,220 , 400,50 );
        info.setFont(getCustomFont().deriveFont(12F));
        this.add(info);
        JLabel info1 = new JLabel("<html><center><font size=6><b>Mot de passe:</b></font></center><br></font></html>" );
        info1.setForeground(Color.white);
        info1.setBounds(65,290 , 400,50 );
        info1.setFont(getCustomFont().deriveFont(12F));
        this.add(info1);
        JLabel title = new JLabel("<html><p style=\"font-size: 50px;\"><b>ASTROZIA</b></p></html>" );
        title.setForeground(Color.white);
        title.setBounds(60,10, 600,100 );
        title.setFont(getCustomFont().deriveFont(12F));
        this.add(title);
        
        //Navigateur web
        
        JLabel news = new JLabel("<html><center><font size=10><b>News</b></font></center><br></font></html>" );
        news.setForeground(Color.white);
        news.setBounds(412,130, 400,50 );
        news.setFont(getCustomFont().deriveFont(12F));
        this.add(news);
        
        this.jep.setBounds(411, 171, 777, 390);
		this.jep.setOpaque(false);
        this.jep.setBackground(Color.DARK_GRAY);
        this.jep.setContentType("text/html");
        this.jep.setText("<html><body><font color=\"#808080\"><br><br><br><br><br><br><br><center><h1>Loading page..</h1></center></font></body></html>");
		this.jep.setBorder(null);
		this.jep.setBackground( new Color(255, 0, 0, 50) );
		this.jep.setFont(getCustomFont().deriveFont(20F));
		this.jep.setEditable(true);
        HTMLEditorKit kit = new HTMLEditorKit();
        this.scrollPane.setViewportView(this.browser);
        this.jep.setEditorKit(kit);
        try {     
        	this.jep.setPage(new URL("http://ysavary.fr/index.html"));
        	this.add(jep);
        	System.out.println(kit);
        	System.out.println(jep);
        }
        catch (IOException e) {
        	this.jep.setContentType("text/css;  charset=UTF-8");
        	this.jep.setContentType("text/html; charset=UTF-8");
        	this.jep.setText("imposible de visualiser la page");
        }
        
 }
	
	
	
	public SColoredBar getProgressBar()
	{
		return this.progressBar;
	}
	
	public void setInfolabel(String text)
	{
		this.infoLabel.setText(text);
	}
	
	private void setFieldEnabled(boolean enabled)
	{
		usernameField.setEnabled(enabled);
		passwordField.setEnabled(enabled);
		playButton.setEnabled(enabled);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.drawImage(backGround, 0, 0, this.getWidth(), this.getHeight(), this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onEvent(SwingerEvent e) {
		if (e.getSource() == playButton){
			setFieldEnabled(false);
			
			if(usernameField.getText().replaceAll(" ","").length() == 0 || passwordField.getText().length() == 0)
			{
				JOptionPane.showMessageDialog(this, "Erreur, Veuillez rentrez un pseudo et un mots de passe","Erreur",JOptionPane.ERROR_MESSAGE);
				setFieldEnabled(true);
				return;
			}
			
			Thread t = new Thread()
					{
				@Override
				public void run() {
					try {
						Launcher.auth(usernameField.getText(),passwordField.getText());
					} catch (AuthenticationException e) {
						JOptionPane.showMessageDialog(LauncherPanel.this, "Erreur, Impossible de se connecter :" + e.getErrorModel().getErrorMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
						setFieldEnabled(true);
						return;
					}
					saver.set("username",usernameField.getText());
					saver.set("password",passwordField.getText());
					try {
						Launcher.update();
					} catch (Exception e) {
						Launcher.InterruptThread();
						JOptionPane.showMessageDialog(LauncherPanel.this, "Erreur, Impossible de mettre le jeu à jour :" + e,"Erreur",JOptionPane.ERROR_MESSAGE);
						setFieldEnabled(true);
						return;
					}
					
					try {
						Launcher.Launch();
					} catch (LaunchException e) {
						JOptionPane.showMessageDialog(LauncherPanel.this, "Erreur, Impossible de lancer le jeu :" + e,"Erreur",JOptionPane.ERROR_MESSAGE);
						setFieldEnabled(true);
						return;
					}
					
					System.out.println("connection reussite !");
					setFieldEnabled(true);
				}
				
					};
					t.start();
		}
		if(e.getSource() == quitButton)
			Animator.fadeOutFrame(LauncherFrame.getInstance(), 3, new Runnable() {
				public void run() {
					System.exit(0);
				}
			});
		 else if (e.getSource() == this.copyrightButton)
	            InfosFrame.getInstance().setVisible(true);
		 else if(e.getSource() == this.ramButton)
				ramSelector.display();
		 else if(e.getSource() == twitterButton)
	            try {
	                Desktop.getDesktop().browse(new URI("https://twitter.com/craftyoyo"));
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            } catch (URISyntaxException e1) {
	                e1.printStackTrace();
	            }
	        else if(e.getSource() == discordButton)
	            try {
	                Desktop.getDesktop().browse(new URI("https://discord.gg/k8NHNAT"));
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            } catch (URISyntaxException e1) {
	                e1.printStackTrace();
	            }
	        else if(e.getSource() == webButton)
	            try {
	                Desktop.getDesktop().browse(new URI("http://google.com"));
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            } catch (URISyntaxException e1) {
	                e1.printStackTrace();
	            }
		
	}
	public static Font getCustomFont(){
        try {
            InputStream inputStream = new BufferedInputStream(LauncherFrame.class.getResourceAsStream(Swinger.getResourcePath() + "/font/SlateForOnePlus-Black.ttf"));
            return Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
	