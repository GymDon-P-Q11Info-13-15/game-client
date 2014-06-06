package de.gymdon.inf1315.game.render;

import java.awt.Graphics2D;
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
    public double zoom = 0.1;
    private BufferedImage map = null;
    private BufferedImage cache = null;
    private Tile[][] mapCache = null;
    public boolean firstClick = false;
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
		    Building b = buildings[x][y];
		    g2d.drawImage(tex.getImage(), x * tileSize, y * tileSize, tileSize * b.getSizeX(), tileSize * b.getSizeY(), tex);
		}

		if (field[x][y]) {
		    Texture tex = StandardTexture.get("hover_clicked");
		    Building b = buildings[x][y];
		    g2d.drawImage(tex.getImage(), x * tileSize, y * tileSize, tileSize * b.getSizeX(), tileSize * b.getSizeY(), tex);
		}
	    }
	}

	g2d.setTransform(tx);

	int[] x = new int[] { width / 2, width - tileSize / 2, width / 2, tileSize / 2 };
	int[] y = new int[] { tileSize / 2, height / 2, height - tileSize / 2, height / 2 };
	Texture tex = StandardTexture.get("arrow_" + Client.instance.preferences.game.arrow);
	for (int i = 0; i < 4; i++) {
	    if (i == 0 && scrollY <= 0)
		continue;
	    if (i == 1 && scrollX >= (int) (mapWidth * tileSize * zoom - width))
		continue;
	    if (i == 2 && scrollY >= (int) (mapHeight * tileSize * zoom - height))
		continue;
	    if (i == 3 && scrollX <= 0)
		continue;
	    g2d.translate(x[i], y[i]);
	    g2d.rotate(Math.toRadians(90 * i));
	    g2d.drawImage(tex.getImage(), -tileSize / 2, -tileSize / 2, tex);
	    g2d.setTransform(tx);
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

	if (e.getButton() == MouseEvent.BUTTON1 && !firstClick) {
	    int x = (int) (((e.getX() + scrollX) / zoom) / tileSize);
	    int y = (int) (((e.getY() + scrollY) / zoom) / tileSize);

	    if (x < 0 || x >= field.length || y < 0 || y >= field[x].length)
		return;
	    Building[][] buildings = Client.instance.buildings;

	    field = new boolean[mapWidth][mapHeight];
	    for (int x1 = x; x1 > x1 - 6 && x1 >= 0; x1--) {
		for (int y1 = y; y1 > y1 - 6 && y1 >= 0; y1--) {
		    Building b = buildings[x1][y1];
		    if (b != null && b.getSizeX() + x1 > x && b.getSizeY() + y1 > y) {
			field[x1][y1] = true;
			return;
		    }
		}
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

	if (mapCache == null)
	    return;
	int mapWidth = mapCache.length;
	int mapHeight = mapCache[0].length;

	int x = (int) (((e.getX() + scrollX) / zoom) / tileSize);
	int y = (int) (((e.getY() + scrollY) / zoom) / tileSize);

	if (x < 0 || x >= fieldHover.length || y < 0 || y >= fieldHover[x].length)
	    return;
	Building[][] buildings = Client.instance.buildings;

	fieldHover = new boolean[mapWidth][mapHeight];
	for (int x1 = x; x1 > x1 - 6 && x1 >= 0; x1--) {
	    for (int y1 = y; y1 > y1 - 6 && y1 >= 0; y1--) {
		Building b = buildings[x1][y1];
		if (b != null && b.getSizeX() + x1 > x && b.getSizeY() + y1 > y) {
		    fieldHover[x1][y1] = true;
		    return;
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
	double z = zoom;

	zoom *= Math.pow(1.1, (Client.instance.preferences.game.invertZoom ? 1 : -1) * e.getWheelRotation());
	if (zoom < 0.2)
	    zoom = 0.2;
	if (zoom > 5)
	    zoom = 5;
	double d = zoom - z;
	scrollX += d * e.getX() * 2;
	scrollY += d * e.getY() * 2;
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
}
