package fr.themsou.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.themsou.main.Main;
import fr.themsou.main.RenderClass;

@SuppressWarnings("serial")
public class Navbar extends JPanel{
	
	public void paintComponent(Graphics g){
		
		int x = MouseInfo.getPointerInfo().getLocation().x - getLocationOnScreen().x;
		int y = MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().y;
		int width = getWidth();
		
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		if(Main.mode.equals("PB")){
			g.setColor(new Color(255, 150, 150));
			g.fillRect(0, 0, width, getHeight());
			
			g.setColor(new Color(100, 150, 255));
			g.fillRect(width / 2, 0, width / 2, 30);
			
		}else{
			g.setColor(new Color(100, 150, 255));
			g.fillRect(0, 0, width, getHeight());
			
			g.setColor(new Color(255, 150, 150));
			g.fillRect(0, 0, width / 2, 30);
			
		}
		
		if(y > 0 && y < 30 && x >= 0 && x < 250){
			if(x > 125){
				g.setColor(new Color(66, 135, 255));
				g.fillRect(125, 0, 125, 30);
			}else{
				g.setColor(new Color(255, 66, 66));
				g.fillRect(0, 0, 125, 30);
			}
		}
		
		
		g.setColor(Color.BLACK);
		fullCenterString(g, 0, width / 2, 0, 30, "Picture Board", new Font("FreeSans", 1, 15));
		
		fullCenterString(g, width / 2, width, 0, 30, "Mapping Image", new Font("FreeSans", 1, 15));
		
		File imgs = new File(System.getProperty("user.home") + "/.MIGL/IMG/");
		if(!imgs.exists()) imgs.mkdirs();
		File file = new File(System.getProperty("user.home") + "/.MIGL/" + Main.mode + "/");
		if(!file.exists()) file.mkdirs();
		int i = 30;
		for(File children : file.listFiles()){
			
			if(x < 250 && x >= 0 && y > i && y < i + 30){
				g.setColor(new Color(255, 210, 170));
				g.fillRect(0, i, 250, 30);
			}
			g.setColor(Color.BLACK);
			centerString(g, 10, i, i + 30, children.getName(), new Font("FreeSans", 0, 15));
			i += 30;
		}
		i += 10;
		if(x < 250 && x >= 0 && y > i && y < i + 60){
			g.setColor(new Color(255, 210, 170));
			g2d.drawImage(new ImageIcon(Navbar.class.getResource("/more.png")).getImage(), 95, i, 60, 60, null);
		}else{
			g2d.drawImage(new ImageIcon(Navbar.class.getResource("/more.png")).getImage(), 100, i + 5, 50, 50, null);
		}
		setPreferredSize(new Dimension(0, i + 90));
		Main.sPaneNav.updateUI();
		
		
		
		
		
	}
	
	public void mouseClick(int x, int y) {
		Main.click = false;
		
		if(x < 250 && x >= 125 && y > 0 && y < 30){
			
			Main.mode = "MI";
			Main.current = null;
			Main.fileDrop.setActive(false);
			
		}else if(x < 125 && x > 0 && y > 0 && y < 30){
				
			Main.mode = "PB";
			Main.current = null;
			Main.fileDrop.setActive(false);
			
		}
		
		File file = new File(System.getProperty("user.home") + "/.MIGL/" + Main.mode + "/");
		if(!file.exists()) return;
		int i = 30;
		for(File children : file.listFiles()){
			if(y > i && y < i + 30){
				RenderClass.currentCases = null;
				Main.current = children.getName();
				if(Main.mode.equals("PB")) Main.fileDrop.setActive(true);
				else Main.fileDrop.setActive(false);
			}
			i += 30;
		}
		if(y > i && y < i + 60){
			
			String input = JOptionPane.showInputDialog(null, "Enter the name of the project.", "My project");
			if(input == null){
			}else if(input.isEmpty()){
				JOptionPane.showMessageDialog(null, "You must enter a name.");
			}else if(input != null){
				
				String[] sizes = {"8 x 8", "16 x 16", "32 x 32", "64 x 64", "128 x 128"};
				String input2 = (String)JOptionPane.showInputDialog(null, "Select a canvas résolution.", "Create a project.", JOptionPane.QUESTION_MESSAGE, null, sizes, sizes[1]);
				if(input2 != null){
					
					if(Main.mode.equals("PB")){
						
						String[] sizes2 = {"16 x 16", "32 x 32", "64 x 64", "128 x 128", "256 x 256"};
						String input3 = (String)JOptionPane.showInputDialog(null, "Select a new images résolution", "Resize", JOptionPane.QUESTION_MESSAGE, null, sizes2, sizes2[2]);
						if(input3 != null){
							try{
								File project = new File(System.getProperty("user.home") + "/.MIGL/" + Main.mode + "/" + input);
								project.createNewFile();
								
								FileWriter writer = new FileWriter(project, false);
								BufferedWriter out = new BufferedWriter(writer);
								
								int size = Integer.parseInt(input2.split(" x")[0]);
								
								out.write(size + ";" + input3.split(" x")[0] + ";");
								out.newLine();
								
								for(int b = 0; b < size; b++){
									for(int a = 0; a < size; a++){
										out.write("null;");
									}
									out.newLine();
								}
								out.close();
								RenderClass.currentCases = null;
								Main.current = input;
								Main.fileDrop.setActive(true);
								
							}catch(IOException e){ e.printStackTrace(); }
						}
						
						
					}else{
						try{
							File project = new File(System.getProperty("user.home") + "/.MIGL/" + Main.mode + "/" + input);
							project.createNewFile();
							
							FileWriter writer = new FileWriter(project, false);
							BufferedWriter out = new BufferedWriter(writer);
							
							int size = Integer.parseInt(input2.split(" x")[0]);
								
							out.write(size + ";");
							out.newLine();
							
							for(int b = 0; b < size; b++){
								for(int a = 0; a < size; a++){
									out.write("255,255,255;");
								}
								out.newLine();
							}
							out.close();
							RenderClass.currentCases = null;
							Main.current = input;
							Main.fileDrop.setActive(false);
							
						}catch(IOException e){ e.printStackTrace(); }
					}
				}
				
			}
			
		}
		
	}
	
	public int[] centerString(Graphics g, int X, int minY, int maxY, String s, Font font) {
		
		
	    FontRenderContext frc = new FontRenderContext(null, true, true);

	    Rectangle2D r2D = font.getStringBounds(s, frc);
	    int rWidth = (int) Math.round(r2D.getWidth());
	    int rHeight = (int) Math.round(r2D.getHeight());
	    int rY = (int) Math.round(r2D.getY());

	    int b = ((maxY - minY) / 2) - (rHeight / 2) - rY;

	    g.setFont(font);
	    g.drawString(s, X, minY + b);
	    
	    int retur[] = { rWidth, rHeight };
	    
	    return retur;
	}
	
	public int[] fullCenterString(Graphics g, int minX, int maxX, int minY, int maxY, String s, Font font) {
		
		
	    FontRenderContext frc = new FontRenderContext(null, true, true);

	    Rectangle2D r2D = font.getStringBounds(s, frc);
	    int rWidth = (int) Math.round(r2D.getWidth());
	    int rHeight = (int) Math.round(r2D.getHeight());
	    int rX = (int) Math.round(r2D.getX());
	    int rY = (int) Math.round(r2D.getY());

	    int a = ((maxX - minX) / 2) - (rWidth / 2) - rX;
	    int b = ((maxY - minY) / 2) - (rHeight / 2) - rY;

	    g.setFont(font);
	    g.drawString(s, minX + a, minY + b);
	    
	    int retur[] = { rWidth, rHeight };
	    return retur;
	}
}
