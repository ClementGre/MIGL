package fr.themsou.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import fr.themsou.panel.Navbar;

public class RenderClass {
	
	private int x = 0;
	private int y = 0;
	public static int width = 0;
	public static int canvas = 8;
	public static double squareSize = 30;
	private int mode = 0;
	private int mouseX = 0;
	private int mouseY = 0;
	public static String currentCases[][];
	public static int imagesResolution = 64;
	
	@SuppressWarnings("static-access")
	public RenderClass(int x, int y, int width, int canvas, int mode, int mouseX, int mouseY) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.canvas = canvas;
		this.squareSize = (double) width / (double) canvas;
		this.mode = mode;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		if(this.currentCases == null) this.currentCases = new String[256][256];
	}
	@SuppressWarnings("static-access")
	public RenderClass(int x, int y, int width, int canvas, int mode, int mouseX, int mouseY, int imagesResolution){
		this.x = x;
		this.y = y;
		this.width = width;
		this.canvas = canvas;
		this.squareSize = (double) width / (double) canvas;
		this.mode = mode;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		if(this.currentCases == null) this.currentCases = new String[canvas][canvas];
		this.imagesResolution = imagesResolution;
	}
	
	@SuppressWarnings("static-access")
	public void addPBSquare(int x, int y, Graphics2D g, String value){
		
		int originX = (int) (this.x + x * this.squareSize);
		int originY = (int) (this.y + y * this.squareSize);
		
		g.setColor(Color.GRAY);
		g.drawRect(originX, originY, (int) this.squareSize, (int) this.squareSize);
		
		if(mode == 1){
			
			currentCases[x][y] = value;
			
			
			
			if(!value.equals("null")){
				File file = new File(System.getProperty("user.home") + "/.MIGL/IMG/" + value);
				try{
					Image image = ImageIO.read(file);
					Image imageRes = image.getScaledInstance(imagesResolution, imagesResolution, Image.SCALE_SMOOTH);
					g.drawImage(imageRes, originX, originY, (int) squareSize, (int) squareSize, null);
				}catch(IOException e){ e.printStackTrace(); }
				
			}
			
			if(mouseX > originX && mouseX < originX + squareSize && mouseY > originY && mouseY < originY + squareSize){
				
				if(Main.click){
					
					Main.click = false;
					Main.addFileX = x;
					Main.addFileY = y;
					Main.getFile = true;
					
				}
				int space = (int) (squareSize / 6 / 1.6);
				g.drawImage(new ImageIcon(Navbar.class.getResource("/more.png")).getImage(), originX + space, originY + space, (int) squareSize - space * 2, (int) squareSize - space * 2, null);
				
				
			}else{
				
				if(value.equals("null")){
					int space = (int) (squareSize / 6);
					g.drawImage(new ImageIcon(Navbar.class.getResource("/more.png")).getImage(), originX + space, originY + space, (int) squareSize - space * 2, (int) squareSize - space * 2, null);
					
				}
				
			}
			
		}else if(mode == 2){
			Color color = new Color(Integer.parseInt(value.split(",")[0]), Integer.parseInt(value.split(",")[1]), Integer.parseInt(value.split(",")[2]));
			Color currentColor = Main.currentColor;
			
			currentCases[x][y] = value;
			
			g.setColor(color);
			g.fillRect(originX + 1, originY + 1, (int) this.squareSize - 1, (int) this.squareSize - 1);
			
			if(mouseX > originX && mouseX < originX + squareSize && mouseY > originY && mouseY < originY + squareSize){
				
				if(Main.click){
					
					currentCases[x][y] = currentColor.getRed() + "," + currentColor.getGreen() + "," + currentColor.getBlue();
					Main.hasChange = true;
					
				}
				
				currentColor = new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), 150);
				g.setColor(currentColor);
				g.fillRect(originX + 1, originY + 1, (int) this.squareSize - 1, (int) this.squareSize - 1);
				
				
			}
		}
		
	}
	

}
