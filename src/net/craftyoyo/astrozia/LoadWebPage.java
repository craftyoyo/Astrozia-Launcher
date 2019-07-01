package net.craftyoyo.astrozia;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;

public class LoadWebPage extends JFrame
{

public LoadWebPage()
{

setSize(620,440);
JScrollPane sp=new JScrollPane();
JTextPane tp=new JTextPane();
tp.setEditable(false);
URL myurl = null;
try {
    myurl=new URL("http://astrozia-launcher.cf/");
    } catch (MalformedURLException ex) {ex.printStackTrace();}
sp.setViewportView(tp);
add(sp);
try {
    tp.setPage(myurl);
    } catch (IOException ex) {ex.printStackTrace();}

}



}