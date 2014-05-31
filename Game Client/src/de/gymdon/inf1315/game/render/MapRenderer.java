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
    public int tileSize = TILE_SIZE_SMALL;
    private BufferedImage map = null;
    private Tile[][] mapCache = null;
    public Point p;
    private int scrollX = 0;
    private int scrollY = 0;
    private boolean[][] fieldHover;

    @Override
    public void render(Graphics2D g2d, int width, int height) {
	this.width = width;
	this.height = height;
	//AffineTransform tx = g2d.getTransform();
	//tx.translate(-scrollX, -scrollY);
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
	g2d.drawImage(this.map, 0 - tileSize * scrollX, 0 - tileSize * scrollY, null);

	Building[][] buildings = Client.instance.buildings;
	for (int x = 0; x < buildings.length; x++) {
	    for (int y = 0; y < buildings[x].length; y++) {
		if (buildings[x][y] != null) {
		    Texture tex = buildings[x][y].getTexture();
		    if (tex != null)
			g2d.drawImage(tex.getImage(), x * tileSize - tileSize * scrollX, y * tileSize - tileSize * scrollY, tex.getWidth() / (TILE_SIZE_NORMAL / tileSize), tex.getHeight() / (TILE_SIZE_NORMAL / tileSize), tex);
		}
	    }
	}

	try {
	    for (int x = 0; x < fieldHover.length; x++) {
		for (int y = 0; y < fieldHover[x].length; y++) {
		    if (fieldHover[x][y]) {
			g2d.drawImage(new StandardTexture("Hover").getImage(), x * tileSize - tileSize * scrollX, y * tileSize - tileSize * scrollY, tileSize, tileSize, new StandardTexture("Hover"));
		    }
		}
	    }
	} catch (NullPointerException n) {}
	
	if(scrollX != 0)
	{
	    g2d.drawImage(new StandardTexture("Arrow_left").getImage(), tileSize/2, height/2, tileSize, tileSize, new StandardTexture("Arrow_left"));
	}
	if(scrollY !=0)
	{
	    g2d.drawImage(new StandardTexture("Arrow_up").getImage(), width/2, tileSize/2, tileSize, tileSize, new StandardTexture("Arrow_up"));
	}
	if(this.width < this.map.getWidth())
	{
	    g2d.drawImage(new StandardTexture("Arrow_right").getImage(), width - tileSize*3/2, height/2, tileSize, tileSize, new StandardTexture("Arrow_right"));
	}
	if(this.height < this.map.getHeight())
	{
	    g2d.drawImage(new StandardTexture("Arrow_down").getImage(), width/2, height - tileSize*3/2, tileSize, tileSize, new StandardTexture("Arrow_down"));
	}

	//g2d.setTransform(tx);
    }
    
    public BufferedImage getMapBackground()
    {
	return this.map.getSubimage(scrollX*tileSize, scrollY*tileSize, this.map.getWidth() - scrollX*tileSize, this.map.getHeight()- scrollY*tileSize);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
	List<GuiControl> controlList = new ArrayList<GuiControl>();
	controlList.addAll(this.controlList);
	for (GuiControl c : controlList)
	    c.mouseClicked(e);

	int x = (e.getX() / tileSize) + scrollX;
	int y = (e.getY() / tileSize) + scrollY;
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

	fieldHover = new boolean[Client.instance.mapgen.getMapWidth()][new MapGenerator().getMapHeight()];
	int x = (e.getX() / tileSize) + scrollX;
	int y = (e.getY() / tileSize) + scrollY;
	Building[][] buildings = Client.instance.buildings;
	if (0 <= x && x < buildings.length && 0 <= y && y < buildings[x].length) {
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

	// Quite buggy, only scrolls when mouse is moved in this area.

	if (Client.instance.preferences.game.CornerScroll) {
	    if (e.getX() <= 2 * tileSize && scrollX > 0 && scrollX <= mapCache.length / 2)
		scrollX--;
	    if (e.getX() >= width - 2 * tileSize && scrollX >= 0 && scrollX < mapCache.length / 2)
		scrollX++;
	    if (e.getY() <= 2 * tileSize && scrollY > 0 && scrollY <= mapCache[0].length / 2)
		scrollY--;
	    if (e.getY() >= height - 2 * tileSize && scrollY >= 0 && scrollY < mapCache[0].length / 2)
		scrollY++;
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
	    System.out.println(e.getActionCommand());
	}
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
	int key = e.getKeyCode();
	System.out.println(KeyEvent.getKeyText(key));
	if (key == KeyEvent.VK_LEFT && scrollX > 0 && scrollX <= mapCache.length / 2)
	    scrollX--;
	if (key == KeyEvent.VK_RIGHT && scrollX >= 0 && scrollX < mapCache.length / 2)
	    scrollX++;
	if (key == KeyEvent.VK_UP && scrollY > 0 && scrollY <= mapCache[0].length / 2)
	    scrollY--;
	if (key == KeyEvent.VK_DOWN && scrollY >= 0 && scrollY < mapCache[0].length / 2)
	    scrollY++;
	if (key == KeyEvent.VK_ESCAPE)
	    Client.instance.setGuiScreen(new GuiPauseMenu());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
	if (e.getWheelRotation() < 0 && scrollY > 0 && scrollY <= mapCache[0].length / 2)
	    scrollY--;
	if (e.getWheelRotation() > 0 && scrollY >= 0 && scrollY < mapCache[0].length / 2)
	    scrollY++;
    }
}
