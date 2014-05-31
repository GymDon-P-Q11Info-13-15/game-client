package de.gymdon.inf1315.game.render;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.MouseInputListener;

import de.gymdon.inf1315.game.*;
import de.gymdon.inf1315.game.client.*;
import de.gymdon.inf1315.game.render.gui.GuiControl;
import de.gymdon.inf1315.game.render.gui.GuiPauseMenu;

public class MapRenderer implements Renderable, ActionListener, MouseInputListener, MouseWheelListener, KeyListener {

    public List<GuiControl> controlList = new ArrayList<GuiControl>();
    protected int width;
    protected int height;
    public static final int TILE_SIZE_SMALL = 32;
    public static final int TILE_SIZE_NORMAL = 64;
    public static final int TILE_SIZE_BIG = 128;
    public int tileSize = TILE_SIZE_NORMAL;
    public double zoom = 1;
    private BufferedImage map = null;
    private BufferedImage cache = null;
    private Tile[][] mapCache = null;
    public Point p;
    private boolean firstClick = false;
    private int scrollX = 0;
    private int scrollY = 0;
    private int diffX = 0;
    private int diffY = 0;
    private boolean[][] fieldHover;
    private boolean[][] field;

    @Override
    public void render(Graphics2D g2do, int width, int height) {
	this.width = width;
	this.height = height;
	cache = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	Graphics2D g2d = cache.createGraphics();
	for (GuiControl c : controlList)
	    c.render(g2d, width, height);

	Tile[][] map = Client.instance.map;
	int mapWidth = map.length;
	int mapHeight = map[0].length;
	double w = (mapWidth * tileSize * zoom);
	if (w < width)
	    zoom /= w / width;
	double h = (mapHeight * tileSize * zoom);
	if (h < height)
	    zoom /= h / height;
	AffineTransform tx = g2d.getTransform();
	g2d.translate(-scrollX, -scrollY);
	g2d.scale(zoom, zoom);
	// Rendering Map
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

	// Rendering Buildings
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

	if (fieldHover == null || fieldHover.length != mapWidth || fieldHover[0].length != mapHeight)
	    fieldHover = new boolean[mapWidth][mapHeight];
	if (field == null || field.length != mapWidth || field[0].length != mapHeight)
	    field = new boolean[mapWidth][mapHeight];
	// Rendering Click and Hover
	for (int x = 0; x < fieldHover.length; x++) {
	    for (int y = 0; y < fieldHover[x].length; y++) {
		if (fieldHover[x][y]) {
		    Texture tex = StandardTexture.get("hover");
		    g2d.drawImage(tex.getImage(), x * tileSize, y * tileSize, tileSize, tileSize, tex);
		}

		if (field[x][y]) {
		    Texture tex = StandardTexture.get("hover_clicked");
		    g2d.drawImage(tex.getImage(), x * tileSize, y * tileSize, tileSize, tileSize, tex);
		}
	    }
	}

	g2d.setTransform(tx);

	// Rendering Arrows to show where unseen map is
	if (scrollX != 0) {
	    Texture tex = StandardTexture.get("arrow_left");
	    g2d.drawImage(tex.getImage(), tileSize / 2, height / 2, tileSize, tileSize, tex);
	}
	if (scrollY != 0) {
	    Texture tex = StandardTexture.get("arrow_up");
	    g2d.drawImage(tex.getImage(), width / 2, tileSize / 2, tileSize, tileSize, tex);
	}
	if (scrollX < (int) (mapWidth * tileSize * zoom - width)) {
	    Texture tex = StandardTexture.get("arrow_right");
	    g2d.drawImage(tex.getImage(), width - tileSize * 3 / 2, height / 2, tileSize, tileSize, tex);
	}
	if (scrollY < (int) (mapHeight * tileSize * zoom - height)) {
	    Texture tex = StandardTexture.get("arrow_down");
	    g2d.drawImage(tex.getImage(), width / 2, height - tileSize * 3 / 2, tileSize, tileSize, tex);
	}
	g2d.dispose();
	g2do.drawImage(cache, 0, 0, null);
    }

    public BufferedImage getMapBackground() {
	return cache;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
	List<GuiControl> controlList = new ArrayList<GuiControl>();
	controlList.addAll(this.controlList);
	for (GuiControl c : controlList)
	    c.mouseClicked(e);

	if (mapCache == null)
	    return;
	int mapWidth = mapCache.length;
	int mapHeight = mapCache[0].length;
	if (field == null || field.length != mapWidth || field[0].length != mapHeight)
	    field = new boolean[mapWidth][mapHeight];
	if (e.getButton() == MouseEvent.BUTTON1 && !firstClick) {
	    int x = (int) (((e.getX() + scrollX) / zoom) / tileSize);
	    int y = (int) (((e.getY() + scrollY) / zoom) / tileSize);
	    Building[][] buildings = Client.instance.buildings;

	    if (0 <= x && x < field.length && 0 <= y && y < field[x].length) {
		p = new Point(x, y);
		actionPerformed(new ActionEvent(e, ActionEvent.ACTION_PERFORMED, ("(" + x + "|" + y + ")")));

		for (int a = 0; a < field.length; a++) {
		    for (int b = 0; b < field[a].length; b++) {
			field[a][b] = false;
		    }
		}
		if (buildings[x][y] != null) {
		    field[x][y] = true;
		}
	    } else {
		p = new Point(-1, -1);
	    }
	}
	firstClick = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
	List<GuiControl> controlList = new ArrayList<GuiControl>();
	controlList.addAll(this.controlList);
	for (GuiControl c : controlList)
	    c.mouseMoved(e);

	int mapWidth = mapCache.length;
	int mapHeight = mapCache[0].length;
	if (fieldHover == null || fieldHover.length != mapWidth || fieldHover[0].length != mapHeight)
	    fieldHover = new boolean[mapWidth][mapHeight];
	int x = (int) (((e.getX() + scrollX) / zoom) / tileSize);
	int y = (int) (((e.getY() + scrollY) / zoom) / tileSize);
	Building[][] buildings = Client.instance.buildings;

	if (0 <= x && x < fieldHover.length && 0 <= y && y < fieldHover[x].length) {
	    if (buildings[x][y] != null) {
		fieldHover[x][y] = true;
	    } else {
		for (int a = 0; a < fieldHover.length; a++) {
		    for (int b = 0; b < fieldHover[a].length; b++) {
			fieldHover[a][b] = false;
		    }
		}
	    }
	}
    }

    @Override
    public void mousePressed(MouseEvent e) {
	List<GuiControl> controlList = new ArrayList<GuiControl>();
	controlList.addAll(this.controlList);
	for (GuiControl c : controlList)
	    c.mousePressed(e);

	if (e.getButton() == MouseEvent.BUTTON3) {
	    diffX = e.getX();
	    diffY = e.getY();
	}
    }

    @Override
    public void mouseDragged(MouseEvent e) {
	List<GuiControl> controlList = new ArrayList<GuiControl>();
	controlList.addAll(this.controlList);
	for (GuiControl c : controlList)
	    c.mouseDragged(e);
	if (e.getModifiersEx() == MouseEvent.BUTTON3_DOWN_MASK) {
	    scrollX -= e.getX() - diffX;
	    scrollY -= e.getY() - diffY;
	    if (scrollX < 0)
		scrollX = 0;
	    if (scrollY < 0)
		scrollY = 0;
	    int mapWidth = mapCache.length;
	    int mapHeight = mapCache[0].length;
	    if (scrollX > (int) (mapWidth * tileSize * zoom - width))
		scrollX = (int) (mapWidth * tileSize * zoom - width);
	    if (scrollY > (int) (mapHeight * tileSize * zoom - height))
		scrollY = (int) (mapHeight * tileSize * zoom - height);
	    diffX = e.getX();
	    diffY = e.getY();
	}
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
    public void mouseReleased(MouseEvent e) {
	List<GuiControl> controlList = new ArrayList<GuiControl>();
	controlList.addAll(this.controlList);
	for (GuiControl c : controlList)
	    c.mouseReleased(e);
    }

    public void actionPerformed(ActionEvent e) {
	if (e.getID() == ActionEvent.ACTION_PERFORMED) {
	    // System.out.println(e.getActionCommand());
	}
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
	int key = e.getKeyCode();
	System.out.println(KeyEvent.getKeyText(key));
	if (key == KeyEvent.VK_LEFT)
	    scrollX -= tileSize / 4;
	else if (key == KeyEvent.VK_RIGHT)
	    scrollX += tileSize / 4;
	else if (key == KeyEvent.VK_UP)
	    scrollY -= tileSize / 4;
	else if (key == KeyEvent.VK_DOWN)
	    scrollY += tileSize / 4;
	else if (key == KeyEvent.VK_ESCAPE) {
	    Client.instance.setGuiScreen(new GuiPauseMenu());
	    firstClick = true;
	}
	if (scrollX < 0)
	    scrollX = 0;
	if (scrollY < 0)
	    scrollY = 0;
	int mapWidth = mapCache.length;
	int mapHeight = mapCache[0].length;
	if (scrollX > (int) (mapWidth * tileSize * zoom - width))
	    scrollX = (int) (mapWidth * tileSize * zoom - width);
	if (scrollY > (int) (mapHeight * tileSize * zoom - height))
	    scrollY = (int) (mapHeight * tileSize * zoom - height);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
	zoom *= Math.pow(1.1, e.getWheelRotation());
	if (zoom < 0.2)
	    zoom = 0.2;
	if (zoom > 5)
	    zoom = 5;
    }
}
