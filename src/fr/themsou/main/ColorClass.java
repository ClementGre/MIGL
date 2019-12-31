package fr.themsou.main;

import java.awt.Color;
import java.awt.Graphics2D;

public class ColorClass {
	
	private Color[] colors;
	private int minX;
	
	public ColorClass(Color[] colors, int minX){
		
		this.colors = colors;
		this.minX = minX;
	}
	
	public void repaint(Graphics2D g){
		
		int i = 0;
		int x = 7;
		boolean top = false;
		for(Color color : colors){
			
			g.setColor(color);
			
			if(top){
				g.fillRect(minX + i * 20 + x, 7, 20, 20);
				if(Main.currentColor == color){
					g.setColor(Color.BLACK);
					g.drawRect(minX + i * 20 + x, 7, 20, 20);
				}
				x += 7; i ++;
				if(i == 4) x += 10;
			}
			else{
				g.fillRect(minX + i * 20 + x, 7 + 20 + 6, 20, 20);
				if(Main.currentColor == color){
					g.setColor(Color.BLACK);
					g.drawRect(minX + i * 20 + x, 7 + 20 + 6, 20, 20);
				}
			}
			
			top = !top;
			
			
		}
		
	}
	
	public void clic(int mX, int mY){
		
		int i = 0;
		int x = 7;
		boolean top = false;
		for(Color color : colors){
			
			if(top){
				
				if(mY > 7 && mY < 7 + 20){
					if(mX > minX + i * 20 + x && mX < minX + i * 20 + x + 20){
						Main.currentColor = color;
					}
				}
				
				x += 7; i ++;
				if(i == 4) x += 10;
			}else{
				if(mY > 7 + 20 + 6 && mY < 7 + 20 + 6 + 20){
					if(mX > minX + i * 20 + x && mX < minX + i * 20 + x + 20){
						Main.currentColor = color;
					}
				}
			}
			
			top = !top;
			
			
		}
		
	}

}
