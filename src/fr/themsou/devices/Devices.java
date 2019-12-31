package fr.themsou.devices;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import fr.themsou.main.Main;
import fr.themsou.panel.Navbar;
import fr.themsou.panel.Render;
import fr.themsou.panel.Toolbar;

public class Devices implements MouseListener, KeyListener, MouseWheelListener{

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Main.click = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		if(e.getComponent() == Main.navBar) new Navbar().mouseClick(e.getX(), e.getY());
		if(e.getComponent() == Main.toolBar) new Toolbar().mouseClick(e.getX(), e.getY());
		if(e.getComponent() == Main.render) new Render().mouseClick(e.getX(), e.getY());
		
		Main.click = false;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		if(Main.current != null){
			
			if(e.getWheelRotation() == 1) Main.zoom -= 5;
			
			else Main.zoom += 5;
			
			if(Main.zoom <= 0) Main.zoom = 5;
			else if(Main.zoom >= 499) Main.zoom = 500;
			
		}
		
	}

}
