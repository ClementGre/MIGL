package fr.themsou.panel;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import fr.themsou.main.Main;

@SuppressWarnings("serial")
public class Footerbar extends JPanel{
	
	public void paintComponent(Graphics g){
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		
		String mode = "Picture Board";
		if(!Main.mode.equals("PB")){
			mode = "Mapping Image";
			fullCenterString(g, 0, getWidth(), 0, getHeight(), "R : " + Main.currentColor.getRed() + "   G : " + Main.currentColor.getGreen() + "   B : " + Main.currentColor.getBlue(), new Font("FreeSans", 0, 15));
		}
		
		
		
		if(Main.current != null){
			alignRightString(g, getWidth() - 4, 0, getHeight(), mode + " - " + Main.current, new Font("FreeSans", 0, 15));
			alignLeftString(g, 4, 0, getHeight(), "zoom : " + Main.zoom + "%", new Font("FreeSans", 0, 15));
		}else{
			alignRightString(g, getWidth() - 4, 0, getHeight(), mode + " - no open file", new Font("FreeSans", 0, 15));
		}
		
	}
	
	public int[] alignRightString(Graphics g, int maxX, int minY, int maxY, String s, Font font) {
		
		
	    FontRenderContext frc = new FontRenderContext(null, true, true);

	    Rectangle2D r2D = font.getStringBounds(s, frc);
	    int rWidth = (int) Math.round(r2D.getWidth());
	    int rHeight = (int) Math.round(r2D.getHeight());
	    int rY = (int) Math.round(r2D.getY());

	    int b = ((maxY - minY) / 2) - (rHeight / 2) - rY;

	    g.setFont(font);
	    g.drawString(s, maxX - rWidth, minY + b);
	    
	    int retur[] = { rWidth, rHeight };
	    
	    return retur;
	}
	public int[] alignLeftString(Graphics g, int minX, int minY, int maxY, String s, Font font) {
		
		
	    FontRenderContext frc = new FontRenderContext(null, true, true);

	    Rectangle2D r2D = font.getStringBounds(s, frc);
	    int rWidth = (int) Math.round(r2D.getWidth());
	    int rHeight = (int) Math.round(r2D.getHeight());
	    int rY = (int) Math.round(r2D.getY());

	    int b = ((maxY - minY) / 2) - (rHeight / 2) - rY;

	    g.setFont(font);
	    g.drawString(s, minX, minY + b);
	    
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
