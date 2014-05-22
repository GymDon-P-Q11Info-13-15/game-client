package de.gymdon.inf1315.game.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.*;

import javax.swing.event.MouseInputListener;

import de.gymdon.inf1315.game.Tile;
import de.gymdon.inf1315.game.client.Client;

public class MapRenderer implements Renderable,ActionListener,MouseInputListener {

    public List<MapControl> controlList = new ArrayList<MapControl>();
    protected int width;
    protected int height;
    public int pixelSize = 32;
    public int x = 48;
    public int y = 32;
    public Tile Koordinate[] [] = new Tile[x] [y];
    public Tile grass = new Tile("grass");
    //public Tile water = new Tile("water");
    public BufferedImage map = new BufferedImage(pixelSize*x, pixelSize*y, BufferedImage.TYPE_INT_RGB);
    
    public abstract class MapControl implements MouseInputListener,Renderable{
	    public abstract int getId();
	    
	    @Override
	    public void mouseEntered(MouseEvent e) {
	    }
	    
	    @Override
	    public void mouseExited(MouseEvent e) {
	    }
	    
	    @Override
	    public void mouseDragged(MouseEvent e) {
	    }
	    
	    @Override
	    public void mouseClicked(MouseEvent e) {
	    }
	    
	    public abstract void addActionListener(ActionListener l);
	    public abstract void removeActionListener(ActionListener l);
	}

    
    @Override
    public void render(Graphics2D g2d, int width, int height, int scrollX,
	    int scrollY) {
	this.width = width;
	this.height = height;
	for (MapControl c : controlList)
	    c.render(g2d, width, height, scrollX, scrollY);
	g2d.setColor(Color.YELLOW);
	grass.groundFactor = 1;
	//water.groundFactor = 0;
	for(int i = 0; i < x; i++)
	{
	    for(int j = 0; j < y; j++)
	    {
		Koordinate[i][j] = grass;
	    }
	}
	
	for(int i = 0; i < x; i++)
	{
	    for(int j = 0; j < y; j++)
	    {
		if(true/*Koordinate[i] [j].groundFactor == 1*/)
		{
		    g2d.drawImage(grass.texture.getImage(), i*pixelSize, j*pixelSize, pixelSize, pixelSize, grass.texture);
		}
	    }
	}
	g2d.setFont(Font.decode("Helvetica Bold 22"));
    }
    
    public void tick()
    {
	
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
	List<MapControl> controlList = new ArrayList<MapControl>();
	controlList.addAll(this.controlList);
	for (MapControl c : controlList)
	    c.mouseClicked(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
	List<MapControl> controlList = new ArrayList<MapControl>();
	controlList.addAll(this.controlList);
	for (MapControl c : controlList)
	    c.mouseDragged(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
	List<MapControl> controlList = new ArrayList<MapControl>();
	controlList.addAll(this.controlList);
	for (MapControl c : controlList)
	    c.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
	List<MapControl> controlList = new ArrayList<MapControl>();
	controlList.addAll(this.controlList);
	for (MapControl c : controlList)
	    c.mouseExited(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
	List<MapControl> controlList = new ArrayList<MapControl>();
	controlList.addAll(this.controlList);
	for (MapControl c : controlList)
	    c.mouseMoved(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
	List<MapControl> controlList = new ArrayList<MapControl>();
	controlList.addAll(this.controlList);
	for (MapControl c : controlList)
	    c.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
	List<MapControl> controlList = new ArrayList<MapControl>();
	controlList.addAll(this.controlList);
	for (MapControl c : controlList)
	    c.mouseReleased(e);
    }
    
    public void actionPerformed(ActionEvent e) {
	if(e.getID() == ActionEvent.ACTION_PERFORMED) {
	    //GuiButton button = (GuiButton)e.getSource();
	}
    }
}
