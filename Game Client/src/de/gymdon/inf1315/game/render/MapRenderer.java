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

import de.gymdon.inf1315.game.*;
import de.gymdon.inf1315.game.MapGenerator;
import de.gymdon.inf1315.game.Tile;
import de.gymdon.inf1315.game.client.Client;
import de.gymdon.inf1315.game.render.gui.GuiControl;

public class MapRenderer implements Renderable, ActionListener,
	MouseInputListener {

    public List<GuiControl> controlList = new ArrayList<GuiControl>();
    protected int width;
    protected int height;
    public int pixelSize = 32;
    public int x = 48;
    public int y = 32;
    public Tile map[][] = new Tile[x][y];
    public Building buildings[][] = new Building[x][y];
    public BufferedImage imageMap = new BufferedImage(pixelSize * x, pixelSize
	    * y, BufferedImage.TYPE_INT_RGB);
    MapGenerator mapgen = new MapGenerator();

    public MapRenderer() {

	mapgen.generateAll();
	map = mapgen.getMap();

    }

    @Override
    public void render(Graphics2D g2d, int width, int height, int scrollX,
	    int scrollY) {
	this.width = width;
	this.height = height;
	for (GuiControl c : controlList)
	    c.render(g2d, width, height, scrollX, scrollY);

	for (int i = 0; i < x; i++) {
	    for (int j = 0; j < y; j++) {
		if (map[i][j].groundFactor == 1) {
		    g2d.drawImage(mapgen.grass.texture.getImage(), i
			    * pixelSize, j * pixelSize, pixelSize, pixelSize,
			    mapgen.grass.texture);
		} if (map[i][j].groundFactor == 2) {
		    g2d.drawImage(mapgen.water.texture.getImage(), i
			    * pixelSize, j * pixelSize, pixelSize, pixelSize,
			    mapgen.water.texture);
		} if (map[i][j].groundFactor == 3) {
		    g2d.drawImage(mapgen.sand.texture.getImage(), i
			    * pixelSize, j * pixelSize, pixelSize, pixelSize,
			    mapgen.sand.texture);
		}
	    }
	}
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
	List<GuiControl> controlList = new ArrayList<GuiControl>();
	controlList.addAll(this.controlList);
	for (GuiControl c : controlList)
	    c.mouseClicked(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
	List<GuiControl> controlList = new ArrayList<GuiControl>();
	controlList.addAll(this.controlList);
	for (GuiControl c : controlList)
	    c.mouseDragged(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
	List<GuiControl> controlList = new ArrayList<GuiControl>();
	controlList.addAll(this.controlList);
	for (GuiControl c : controlList)
	    c.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
	List<GuiControl> controlList = new ArrayList<GuiControl>();
	controlList.addAll(this.controlList);
	for (GuiControl c : controlList)
	    c.mouseExited(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
	List<GuiControl> controlList = new ArrayList<GuiControl>();
	controlList.addAll(this.controlList);
	for (GuiControl c : controlList)
	    c.mouseMoved(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
	List<GuiControl> controlList = new ArrayList<GuiControl>();
	controlList.addAll(this.controlList);
	for (GuiControl c : controlList)
	    c.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
	List<GuiControl> controlList = new ArrayList<GuiControl>();
	controlList.addAll(this.controlList);
	for (GuiControl c : controlList)
	    c.mouseReleased(e);
    }

    public void actionPerformed(ActionEvent e) {
	if (e.getID() == ActionEvent.ACTION_PERFORMED) {
	    // GuiButton button = (GuiButton)e.getSource();
	}
    }
}
