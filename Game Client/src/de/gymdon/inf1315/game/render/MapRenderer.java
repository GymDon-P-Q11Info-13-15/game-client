package de.gymdon.inf1315.game.render;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.MouseInputListener;

import de.gymdon.inf1315.game.*;
import de.gymdon.inf1315.game.client.*;
import de.gymdon.inf1315.game.render.gui.GuiControl;
import de.gymdon.inf1315.game.render.gui.GuiMainMenu;
import de.gymdon.inf1315.game.render.gui.GuiPauseMenu;

public class MapRenderer implements Renderable, ActionListener, MouseInputListener, KeyListener {

    public List<GuiControl> controlList = new ArrayList<GuiControl>();
    protected int width;
    protected int height;
    public static final int TILE_SIZE_SMALL = 32;
    public static final int TILE_SIZE_NORMAL = 64;
    public static final int TILE_SIZE_BIG = 128;
    public int tileSize = TILE_SIZE_SMALL;
    private BufferedImage map = null;
    private Tile[][] mapCache = null;
    public Point p;
    private int hoverX = 0;
    private int hoverY = 0;
    private int scrollX = 0;
    private int scrollY = 0;
    private StandardTexture[][] hover = new StandardTexture[Client.instance.mapgen.getMapWidth()][Client.instance.mapgen.getMapHeight()];

    @Override
    public void render(Graphics2D g2d, int width, int height) {
	this.width = width;
	this.height = height;
	AffineTransform tx = g2d.getTransform();
	tx.translate(-scrollX, -scrollY);
	for (GuiControl c : controlList)
	    c.render(g2d, width, height);

	Tile[][] map = Client.instance.map;
	if (this.map == null || !map.equals(mapCache)) {
	    this.map = new BufferedImage(map.length * tileSize, map[0].length * tileSize, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = this.map.createGraphics();
	    for (int x = 0; x < map.length; x++) {
		for (int y = 0; y < map[x].length; y++) {
		    Tile tile = map[x][y];
		    Texture tex = TileRenderMap.getTexture(tile);
		    if (tex == null)
			continue;
		    g.drawImage(tex.getImage(), x * tileSize, y * tileSize, tileSize, tileSize, tex);
		}
	    }
	    g.dispose();
	    mapCache = map;
	}
	g2d.drawImage(this.map, 0, 0, null);

	Building[][] buildings = Client.instance.buildings;
	for (int x = 0; x < buildings.length; x++) {
	    for (int y = 0; y < buildings[x].length; y++) {
		if (buildings[x][y] != null) {
		    Texture tex = buildings[x][y].getTexture();
		    if (tex != null)
			g2d.drawImage(tex.getImage(), x * tileSize, y * tileSize, tex.getWidth() / (TILE_SIZE_NORMAL / tileSize), tex.getHeight() / (TILE_SIZE_NORMAL / tileSize), tex);
		}
	    }
	}

	for (int x = 0; x < hover.length; x++) {
	    for (int y = 0; y < hover[x].length; y++) {
		if (hover[x][y] != null) {
		    g2d.drawImage(hover[x][y].getImage(), x * tileSize, y * tileSize + tileSize / 4, tileSize / 2, tileSize / 2, hover[x][y]);
		}
	    }
	}
	g2d.setTransform(tx);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
	List<GuiControl> controlList = new ArrayList<GuiControl>();
	controlList.addAll(this.controlList);
	for (GuiControl c : controlList)
	    c.mouseClicked(e);

	int x = e.getX() / tileSize;
	int y = e.getY() / tileSize;
	if (x >= 0 && x < Client.instance.mapgen.getMapWidth() && y >= 0 && y < Client.instance.mapgen.getMapHeight()) {
	    p = new Point(x, y);
	    actionPerformed(new ActionEvent(e, ActionEvent.ACTION_PERFORMED, ("Punkt (" + x + "|" + y + ")")));
	} else {
	    p = new Point(-1, -1);
	}
    }

    @Override
    public void mouseMoved(MouseEvent e) {
	List<GuiControl> controlList = new ArrayList<GuiControl>();
	controlList.addAll(this.controlList);
	for (GuiControl c : controlList)
	    c.mouseMoved(e);

	int x = e.getX() / tileSize;
	int y = e.getY() / tileSize;
	Building[][] buildings = Client.instance.buildings;
	if (0 < x && x < buildings.length && 0 < y && y < buildings[x].length) {
	    if (buildings[x][y] != null) {
		if (hoverX != x || hoverY != y) {
		    System.out.println(buildings[x][y].getClass().getSimpleName() + " an Punkt (" + x + "|" + y + ")");
		    hoverX = x;
		    hoverY = y;
		}
		hover[x + 1][y - 1] = new StandardTexture("sand");
		hover[x + 1][y + 1] = new StandardTexture("water");
	    } else {
		hoverX = x;
		hoverY = y;
		for (int a = 0; a < hover.length; a++) {
		    for (int b = 0; b < hover[a].length; b++) {
			hover[a][b] = null;
		    }
		}
	    }
	}
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
	    //System.out.println(e.getActionCommand());
	}
    }

    @Override
    public void keyReleased(KeyEvent e) {
	actionPerformed(new ActionEvent(e, ActionEvent.ACTION_PERFORMED, (KeyEvent.getKeyText(e.getKeyCode()))));
	int key = e.getKeyCode();

	if (key == KeyEvent.VK_ESCAPE) {
	    Client.instance.setGuiScreen(new GuiPauseMenu());
	}
    }

    @Override
    public void keyPressed(KeyEvent e) {
	int key = e.getKeyCode();
	System.out.println(e);
	if(key == KeyEvent.VK_LEFT)
	    scrollX -= 10;
	if(key == KeyEvent.VK_RIGHT)
	    scrollX += 10;
	if(key == KeyEvent.VK_UP)
	    scrollY -= 10;
	if(key == KeyEvent.VK_DOWN)
	    scrollY += 10;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
