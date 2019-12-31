package fr.themsou.panel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.themsou.main.Main;
import fr.themsou.main.RenderClass;

@SuppressWarnings("serial")
public class Toolbar extends JPanel{
	
	public void paintComponent(Graphics g){
		
		int x = MouseInfo.getPointerInfo().getLocation().x - getLocationOnScreen().x;
		int y = MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().y;
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		
		if(x < getWidth() && x > getWidth() - 60 && y > 0 && y < 60){
			g2d.drawImage(new ImageIcon(Navbar.class.getResource("/toolbar/bin.png")).getImage(), getWidth() - 60, 0, 60, 60, null);
		}else{
			g2d.drawImage(new ImageIcon(Navbar.class.getResource("/toolbar/bin.png")).getImage(), getWidth() - 60 + 5, 0 + 5, 50, 50, null);
		}
		
		if(x < getWidth() - 60 && x > getWidth() - 120 && y > 0 && y < 60){
			g2d.drawImage(new ImageIcon(Navbar.class.getResource("/toolbar/export.png")).getImage(), getWidth() - 120, 0, 60, 60, null);
		}else{
			g2d.drawImage(new ImageIcon(Navbar.class.getResource("/toolbar/export.png")).getImage(), getWidth() - 120 + 5, 0 + 5, 50, 50, null);
		}
		
		if(x < getWidth() - 120 && x > getWidth() - 180 && y > 0 && y < 60){
			g2d.drawImage(new ImageIcon(Navbar.class.getResource("/toolbar/crop.png")).getImage(), getWidth() - 180, 0, 60, 60, null);
		}else{
			g2d.drawImage(new ImageIcon(Navbar.class.getResource("/toolbar/crop.png")).getImage(), getWidth() - 180 + 5, 0 + 5, 50, 50, null);
		}
		
		if(Main.mode.equals("PB")){
			if(x < getWidth() - 180 && x > getWidth() - 240 && y > 0 && y < 60){
				g2d.drawImage(new ImageIcon(Navbar.class.getResource("/toolbar/resize.png")).getImage(), getWidth() - 240, 0, 60, 60, null);
			}else{
				g2d.drawImage(new ImageIcon(Navbar.class.getResource("/toolbar/resize.png")).getImage(), getWidth() - 240 + 5, 0 + 5, 50, 50, null);
			}
		}else{
			
			Main.colorClass.repaint(g2d);
			
		}
		
		
	}
	
	public void mouseClick(int x, int y){
		
		Main.colorClass.clic(x, y);
		
		Main.click = false;
		int width = Main.fenetre.getWidth();
		if(x > width - 60 && y > 0 && y < 60){
			if(Main.current != null){
				int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the project \"" + Main.current + "\"");
				if(i == 0){
					File file = new File(System.getProperty("user.home") + "/.MIGL/" + Main.mode + "/" + Main.current);
					Main.current = null;
					Main.fileDrop.setActive(false);
					file.delete();
				}
			}else{
				JOptionPane.showMessageDialog(null, "You must already select a project");
			}
			
		}else if(x > width - 60 * 2 && y > 0 && y < 60){
			if(Main.current != null){
				String input = JOptionPane.showInputDialog(null, "Select the export folder.", System.getProperty("user.home") + "/");
				if(input == null){
				}else if(input.isEmpty()){
					JOptionPane.showMessageDialog(null, "You must enter an URI");
				}else if(input != null){
					Main.render.export(input);
				}
			}else{
				JOptionPane.showMessageDialog(null, "You must already select a project");
			}
			
		}else if(x > width - 60 * 3 && y > 0 && y < 60){
			if(Main.current != null){
				String[] sizes = {"8 x 8", "16 x 16", "32 x 32", "64 x 64", "128 x 128"};
				String input = (String)JOptionPane.showInputDialog(null, "Select a new canvas résolution.", "Crop", JOptionPane.QUESTION_MESSAGE, null, sizes, sizes[1]);
				if(input != null){
					int size = Integer.parseInt(input.split(" x")[0]);
					RenderClass.canvas = size;
					Main.hasChange = true;
				}
			}else{
				JOptionPane.showMessageDialog(null, "You must already select a project");
			}
			
		}else if(Main.mode.equals("PB")){
			if(x > width - 60 * 4 && y > 0 && y < 60){
				if(Main.current != null){
					String[] sizes = {"16 x 16", "32 x 32", "64 x 64", "128 x 128", "256 x 256"};
					String input = (String)JOptionPane.showInputDialog(null, "Select a new images résolution", "Resize", JOptionPane.QUESTION_MESSAGE, null, sizes, sizes[2]);
					if(input != null){
						int size = Integer.parseInt(input.split(" x")[0]);
						RenderClass.imagesResolution = size;
						Main.hasChange = true;
					}
				}else{
					JOptionPane.showMessageDialog(null, "You must already select a project");
				}
				
			}
		}
		
	}

}
