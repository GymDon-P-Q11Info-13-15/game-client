package de.gymdon.inf1315.game.render;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.MouseInputListener;

import de.gymdon.inf1315.game.*;
import de.gymdon.inf1315.game.render.gui.GuiControl;

public class MapRenderer implements Renderable, ActionListener, MouseInputListener {

    public List<GuiControl> controlList = new ArrayList<GuiControl>();
    protected int width;
    protected int height;
    public int pixelSize = 32;
    public int pixelSizeBig = pixelSize * 2;
    private MapGenerator mapgen = new MapGenerator();

    public MapRenderer() {

	mapgen.generateAll();
    }

    @Override
    public void render(Graphics2D g2d, int width, int height, int scrollX, int scrollY) {
	this.width = width;
	this.height = height;
	for (GuiControl c : controlList)
	    c.render(g2d, width, height, scrollX, scrollY);

	Tile[][] map = mapgen.getMap();
	Building[][] buildings = mapgen.getBuildings();
	
	// Maps
	for (int x = 0; x < map.length; x++) {
	    for (int y = 0; y < map[x].length; y++) {
		Tile tile = map[x][y];
		Texture tex = TileRenderMap.getTexture(tile);
		if(tex != null)
		g2d.drawImage(tex.getImage(), x * pixelSize, y * pixelSize, pixelSize, pixelSize, tex);
	    }
	}
	// Buildings
	for (int x = 0; x < map.length; x++) {
	    for (int y = 0; y < map[x].length; y++) {
		if (buildings[x][y] != null) {
		    Texture tex = buildings[x][y].getTexture();
		    if(tex != null)
		    g2d.drawImage(tex.getImage(), x * pixelSize, y * pixelSize, tex.getWidth()/(pixelSizeBig/pixelSize), tex.getHeight()/(pixelSizeBig/pixelSize), tex);
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
	mapgen = new MapGenerator();
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

	}
    }
}
