package fr.themsou.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import fr.themsou.main.Main;
import fr.themsou.main.RenderClass;

@SuppressWarnings("serial")
public class Render extends JPanel{

	public void paintComponent(Graphics g){
		
		int mouseX = MouseInfo.getPointerInfo().getLocation().x - getLocationOnScreen().x;
		int mouseY = MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().y;
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g.setColor(new Color(102, 102, 102));
		int minWidth = Main.sPaneRender.getHeight();
		if(Main.sPaneRender.getHeight() > Main.sPaneRender.getWidth()) minWidth = Main.sPaneRender.getWidth();
		int width = (int) (((double)((double) Main.zoom) / ((double) 100.0)) * ((double) minWidth) - minWidth / 30);
		int mode = 1;
		if(Main.mode.equals("MI")) mode = 2;
		g.fillRect(0, 0, getWidth(), getHeight());
		
		
		setPreferredSize(new Dimension(width + minWidth / 40, width + minWidth / 40));
		Main.sPaneRender.updateUI();
		
		if(Main.current != null){
			
			g.setColor(Color.WHITE);
			g.fillRect(getWidth() / 2 - width / 2, getHeight() / 2 - width / 2, width, width);
			
			
			if(Main.hasChange){
				if(Main.mode.equals("PB")) savePictureBoardFile();
				else saveMappingImageFile();
				Main.hasChange = false;
			}
			
			try{
				
				File file = new File(System.getProperty("user.home") + "/.MIGL/" + Main.mode + "/" + Main.current);
				
				BufferedReader in;
				if(System.getProperty("os.name").equals("Linux") || System.getProperty("os.name").equals("Mac OS X")) in = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
				else in = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1));
				
				String line = in.readLine();
				if(line == null) return;
				String[] param = line.split(";");
				
				RenderClass render = new RenderClass(getWidth() / 2 - width / 2, getHeight() / 2 - width / 2, width, Integer.parseInt(param[0]), mode, mouseX, mouseY);
				line = in.readLine();
				int x = 0;
				while(line != null){
					
					if(!line.isEmpty()){
						int y = 0;
						for(String value : line.split(";")){
							if(!value.isEmpty()){
								render.addPBSquare(x, y, g2d, value);
								y++;
							}
						}
						x++;
					}
					line = in.readLine();
				}
				in.close();
				
				
				
				if(Main.hasChange){
					if(Main.mode.equals("PB")) savePictureBoardFile();
					else saveMappingImageFile();
					Main.hasChange = false;
				}
				
				
			}catch(IOException e){ e.printStackTrace(); }
			
		}else{
			g.setColor(Color.WHITE);
			fullCenterString(g, 0, getWidth(), 0, getHeight(), "Click in the left menu to open a project.", new Font("FreeSans", 1, 20));
		}
			
		
		
	}
	
	public void mouseClick(int x, int y) {
		
		
		
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

	public void export(String path){
		try{
			
			File file = new File(System.getProperty("user.home") + "/.MIGL/" + Main.mode + "/" + Main.current);
			
			BufferedReader in;
			if(System.getProperty("os.name").equals("Linux") || System.getProperty("os.name").equals("Mac OS X")) in = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
			else in = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1));
			
			String line = in.readLine();
			if(line == null) return;
			String[] param = line.split(";");
			
			File fileTE = new File(path + Main.current + ".png");
			fileTE.createNewFile();
			
			
			BufferedImage img;
			
			if(Main.mode.equals("MI")) img = new BufferedImage(Integer.parseInt(param[0]), Integer.parseInt(param[0]), BufferedImage.TYPE_INT_RGB);
			else img = new BufferedImage(Integer.parseInt(param[0]) * Integer.parseInt(param[1]), Integer.parseInt(param[0]) * Integer.parseInt(param[1]), BufferedImage.TYPE_INT_RGB);
			
			Graphics2D g = img.createGraphics();
			g.fillRect(0, 0, img.getHeight(), img.getHeight());
			
			line = in.readLine();
			int x = 0;
			while(line != null){
				
				if(!line.isEmpty()){
					int y = 0;
					for(String value : line.split(";")){
						if(!value.isEmpty()){
							
							if(Main.mode.equals("MI")){
								g.setColor(new Color(Integer.parseInt(value.split(",")[0]), Integer.parseInt(value.split(",")[1]), Integer.parseInt(value.split(",")[2])));
								g.fillRect(x, y, 1, 1);
							}
							else if(!value.equals("null")){
								File fileTP = new File(System.getProperty("user.home") + "/.MIGL/IMG/" + value);
								Image image = ImageIO.read(fileTP);
								Image imageRes = image.getScaledInstance(Integer.parseInt(param[1]), Integer.parseInt(param[1]), Image.SCALE_SMOOTH);
								g.drawImage(imageRes, x * Integer.parseInt(param[1]), y * Integer.parseInt(param[1]), null);
							}else{
								g.setColor(Color.GRAY);
								g.drawRect(x * Integer.parseInt(param[1]), y * Integer.parseInt(param[1]), Integer.parseInt(param[1]) - 1, Integer.parseInt(param[1]) - 1);
							}
							
							
							y++;
						}
					}
					x++;
				}
				line = in.readLine();
			}
			in.close();
			
			
			ImageIO.write((RenderedImage) img, "png", fileTE);
			
		}catch(IOException e){ e.printStackTrace(); }
	}
	
	public static void savePictureBoardFile(){
		
		try{
			File project = new File(System.getProperty("user.home") + "/.MIGL/PB/" + Main.current);
			
			FileWriter writer = new FileWriter(project, false);
			BufferedWriter out = new BufferedWriter(writer);
			
			
			out.write(RenderClass.canvas + ";" + RenderClass.imagesResolution + ";");
			out.newLine();
			
			for(int x = 0; x < RenderClass.canvas; x++){
				for(int y = 0; y < RenderClass.canvas; y++){
					if(RenderClass.currentCases[x][y] == null){
						RenderClass.currentCases[x][y] = "null";
					}
					out.write(RenderClass.currentCases[x][y] + ";");
				}
				out.newLine();
			}
			out.close();
			
			
		}catch(IOException e){ e.printStackTrace(); }
		
	}

	public void saveMappingImageFile(){
		
		try{
			
			File project = new File(System.getProperty("user.home") + "/.MIGL/MI/" + Main.current);
			
			FileWriter writer = new FileWriter(project, false);
			BufferedWriter out = new BufferedWriter(writer);
			
			out.write(RenderClass.canvas + ";");
			out.newLine();
			
			for(int x = 0; x < RenderClass.canvas; x++){
				for(int y = 0; y < RenderClass.canvas; y++){
					if(RenderClass.currentCases[x][y] == null){
						RenderClass.currentCases[x][y] = "255,255,255";
					}
					out.write(RenderClass.currentCases[x][y] + ";");
				}
				out.newLine();
			}
			out.close();
			
			
		}catch(IOException e){ e.printStackTrace(); }
		
		
	}
	
	public void addFile(File file, int x, int y){
		
		int id = new Random().nextInt(100000000);
		try{
			Files.copy(file.toPath(), new File(System.getProperty("user.home") + "/.MIGL/IMG/" + id + "." + getFileExtension(file)).toPath());
		}catch(IOException e){ e.printStackTrace(); }
		RenderClass.currentCases[x][y] = id + "." + getFileExtension(file);
		Main.addFile = null;
		Main.hasChange = true;
		
	}
	
	
	private String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
	

}
