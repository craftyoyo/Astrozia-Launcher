package net.craftyoyo.astrozia;

import java.awt.Font;
import java.io.File;

import fr.litarvan.openauth.AuthPoints;
import fr.litarvan.openauth.AuthenticationException;
import fr.litarvan.openauth.Authenticator;
import fr.litarvan.openauth.model.AuthAgent;
import fr.litarvan.openauth.model.response.AuthResponse;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;
import fr.theshark34.openlauncherlib.minecraft.GameInfos;
import fr.theshark34.openlauncherlib.minecraft.GameTweak;
import fr.theshark34.openlauncherlib.minecraft.GameType;
import fr.theshark34.openlauncherlib.minecraft.GameVersion;
import fr.theshark34.openlauncherlib.minecraft.MinecraftLauncher;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.application.integrated.FileDeleter;
import fr.theshark34.swinger.Swinger;

@SuppressWarnings("deprecation")
public class Launcher {
	
	public static final GameVersion MC_VERSION = new GameVersion("1.13.2", GameType.V1_8_HIGHER);	
	public static final GameInfos MC_INFOS = new GameInfos("astrozia", MC_VERSION, new GameTweak[] {});
	public static final File MC_DIR = MC_INFOS.getGameDir();
	public static final Saver MC_SAVER = new Saver(new File(MC_DIR, "astrozia.properties"));
	
	public static Font FONT_BOLD;
	private static AuthInfos authinfos;
	private static Thread updateThread;
	
	public static void auth(String username,String password) throws AuthenticationException{
		Authenticator authenticator = new Authenticator(Authenticator.MOJANG_AUTH_URL,AuthPoints.NORMAL_AUTH_POINTS);
		AuthResponse response = authenticator.authenticate(AuthAgent.MINECRAFT, username, password, "");
		authinfos = new AuthInfos(response.getSelectedProfile().getName(),response.getAccessToken(), response.getSelectedProfile().getId());
	}
	
	public static void update() throws Exception
	{
		SUpdate su = new SUpdate("http://astrozia-launcher.cf/m/", MC_DIR);
		su.getServerRequester().setRewriteEnabled(true);
		su.addApplication(new FileDeleter());
		
		updateThread = new Thread() {
			 private int val= 0;
			 private int max = 0;
			@Override
			public void run() {
				while(!this.isInterrupted()) {
					
					if(BarAPI.getNumberOfDownloadedFiles() == 0)
					{
						LauncherFrame.getInstance().getLauncherPanel().setInfolabel("Vérification des fichiers");
						continue;
					}
					
					val =(int) (BarAPI.getNumberOfTotalDownloadedBytes()/1000);
					max = (int) (BarAPI.getNumberOfTotalBytesToDownload()/1000);
					LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setMaximum(max);
					LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setValue(val);
					
					LauncherFrame.getInstance().getLauncherPanel().setInfolabel("Téléchargement des fichiers : " + BarAPI.getNumberOfDownloadedFiles() +" sur " + BarAPI.getNumberOfFileToDownload() +" " + Swinger.percentage(val, max)+"%" );
				}
			}
		};
		updateThread.start();
		
		su.start();
		updateThread.isInterrupted();
	}
	
	public static void Launch() throws LaunchException	
	{
		LauncherFrame.getInstance().getLauncherPanel().setInfolabel("Lancement du jeu...");
		ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(MC_INFOS, GameFolder.BASIC, authinfos);
		profile.getVmArgs().add("-Xmx3072M");
		profile.getArgs().add("--server 127.0.0.1 --port 25565");
		//profile.setParameters((Object[])(((String)"-Xmx2G")));
		ExternalLauncher launcher = new ExternalLauncher(profile);
		Process p = launcher.launch();
		LauncherFrame.getInstance().getLauncherPanel().setInfolabel("Jeu lancer ! Fermeture du launcher...");
		
		try {
			Thread.sleep(5000L);
			LauncherFrame.getInstance().setVisible(false);
			p.waitFor();
			
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}

		System.exit(0);
	}
	
	public static void InterruptThread()
	{
		updateThread.isInterrupted();
	}
}
