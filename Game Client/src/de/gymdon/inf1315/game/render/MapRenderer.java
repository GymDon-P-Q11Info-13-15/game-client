package de.gymdon.inf1315.game.render;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.MouseInputListener;

import de.gymdon.inf1315.game.*;
import de.gymdon.inf1315.game.MapGenerator;
import de.gymdon.inf1315.game.Tile;
import de.gymdon.inf1315.game.render.gui.GuiControl;

public class MapRenderer implements Renderable, ActionListener,
	MouseInputListener {

    public List<GuiControl> controlList = new ArrayList<GuiControl>();
    protected int width;
    protected int height;
    public int pixelSize = 32;
    public int pixelSizeBig = pixelSize * 2;
    public Tile map[][];
    public Building buildings[][];
    MapGenerator mapgen = new MapGenerator();

    public MapRenderer() {

	mapgen.generateAll();
	map = mapgen.getMap();
	buildings = mapgen.getBuildings();
    }

    @Override
    public void render(Graphics2D g2d, int width, int height, int scrollX,
	    int scrollY) {
	this.width = width;
	this.height = height;
	for (GuiControl c : controlList)
	    c.render(g2d, width, height, scrollX, scrollY);

	// Maps
	for (int i = 0; i < mapgen.getMapWidth(); i++) {
	    for (int j = 0; j < mapgen.getMapHeight(); j++) {
		if (map[i][j].groundFactor == 1) {
		    g2d.drawImage(mapgen.grass.texture.getImage(), i
			    * pixelSize, j * pixelSize, pixelSize, pixelSize,
			    mapgen.grass.texture);
		}
		if (map[i][j].groundFactor == 2) {
		    g2d.drawImage(mapgen.water.texture.getImage(), i
			    * pixelSize, j * pixelSize, pixelSize, pixelSize,
			    mapgen.water.texture);
		}
		if (map[i][j].groundFactor == 3) {
		    g2d.drawImage(mapgen.sand.texture.getImage(),
			    i * pixelSize, j * pixelSize, pixelSize, pixelSize,
			    mapgen.sand.texture);
		}
	    }
	}
	// Buildings
	for (int i = 0; i < mapgen.getMapWidth(); i++) {
	    for (int j = 0; j < mapgen.getMapHeight(); j++) {
		if (buildings[i][j] != null) {
		    // Castles
		    if (buildings[i][j].getClass() == Castle.class) {
			g2d.drawImage(new StandardTexture("Castle_neutral_big")
				.getImage(), i * pixelSize, j * pixelSize,
				pixelSizeBig, pixelSizeBig,
				new StandardTexture("Castle_neutral_big"));
		    }
		    // Mines
		    if (buildings[i][j].getClass() == Mine.class) {
			g2d.drawImage(new StandardTexture("mine_neutral")
				.getImage(), i * pixelSize, j * pixelSize,
				pixelSizeBig, pixelSizeBig,
				new StandardTexture("mine_neutral"));
		    }
		    // Barracks
		    if (buildings[i][j].getClass() == Barracks.class) {
			g2d.drawImage(new StandardTexture("mine_superior")
				.getImage(), i * pixelSize, j * pixelSize,
				pixelSizeBig, pixelSizeBig,
				new StandardTexture("mine_superior"));
		    }
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

	}
    }
}
