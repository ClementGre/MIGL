package fr.themsou.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;

import fr.themsou.devices.Devices;
import fr.themsou.devices.FileDrop;
import fr.themsou.panel.Footerbar;
import fr.themsou.panel.Mainscreen;
import fr.themsou.panel.Navbar;
import fr.themsou.panel.Render;
import fr.themsou.panel.Toolbar;


public class Main {
	
	public static JFrame fenetre;
	public static Devices devices = new Devices();
	
	
	public static String mode = "PB";
	public static boolean getFile = false;
	public static String current = null;
	public static int zoom = 100;
	public static int reload = 10;
	public static Color currentColor = Color.GREEN;
	public static boolean click = false;
	public static File addFile = null;
	public static int addFileX = 0;
	public static int addFileY = 0;
	public static boolean hasChange = false;
	
	public static Panel panel = new Panel();
	public static Mainscreen mainScreen = new Mainscreen();
	public static Toolbar toolBar = new Toolbar();
	public static Render render = new Render();
	public static Navbar navBar = new Navbar();
	public static Footerbar footerBar = new Footerbar();
    public static JScrollPane sPaneNav = new JScrollPane(navBar);
    public static JScrollPane sPaneRender = new JScrollPane(render);
	public static ColorClass colorClass;
    
    public static DropTarget fileDrop = new DropTarget(mainScreen, new FileDrop());
	
	public static void main(String[] args){
		fileDrop.setActive(false);
		fileDrop.setDefaultActions(DnDConstants.ACTION_COPY);
		
		fenetre = new JFrame("MIGL");
		fenetre.setSize(1200, 700);
		fenetre.setResizable(true);
		fenetre.setLocationRelativeTo(null);
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.addMouseListener(devices);
		fenetre.setContentPane(panel);
		
		panel.setLayout(new BorderLayout());
		panel.add("North", toolBar);
		panel.add("Center", mainScreen);
		panel.add("West", sPaneNav);
		
		mainScreen.setLayout(new BorderLayout());
		mainScreen.add("Center", sPaneRender);
		mainScreen.add("South", footerBar);
		
		navBar.addMouseListener(devices);
		toolBar.addMouseListener(devices);
		render.addMouseListener(devices);
		render.addMouseWheelListener(devices);
		
		Color colors[] = {Color.WHITE, Color.BLACK, Color.RED, Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.BLUE, Color.CYAN,
				new Color(128, 0, 0), new Color(255, 255, 128), new Color(0, 128, 0), new Color(255, 128, 255), new Color(0, 0, 128), new Color(128, 255, 255)};
		colorClass = new ColorClass(colors, 0);
		
		fenetre.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {
		    }
		});
		
		
		
		while(true){
			fenetre.repaint();
			
			try{
				Thread.sleep(20);
			}catch(InterruptedException e){ e.printStackTrace(); }
			
			
			if(reload > 0){
				reload --;
				
			}else if(reload == 0){
				
				toolBar.setPreferredSize(new Dimension(toolBar.getWidth(), 60));
				footerBar.setPreferredSize(new Dimension(footerBar.getWidth(), 20));
				sPaneNav.setPreferredSize(new Dimension(251, 5000));
				
				reload --;
				fenetre.setSize(1201, 701);
				
			}
			
			if(Main.getFile){
				Main.getFile = false;
				
		  		JFileChooser chooser = new JFileChooser(new File(""));
		  		chooser.setFileFilter(new FileFilter() {
					@Override
					public String getDescription(){ return "Images (jpg, png, jpeg, tif)"; }
					@Override
					public boolean accept(File file) {
						if((!file.isFile()) || getFileExtension(file).equals("jpg") || getFileExtension(file).equals("png") || getFileExtension(file).equals("jpeg") || getFileExtension(file).equals("tif")){
							return true;
		                }else return false;
					}
				});
		  		int result = chooser.showOpenDialog(Main.render);
		  		if(result == JFileChooser.APPROVE_OPTION){
					render.addFile(chooser.getSelectedFile(), Main.addFileX, Main.addFileY);
		  		}
			}
			
			
		}
		
		
		
	}
	
	private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

}
