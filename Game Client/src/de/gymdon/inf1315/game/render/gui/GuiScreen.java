package de.gymdon.inf1315.game.render.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;

public class GuiScreen extends Gui {
    public List<GuiControl> controlList = new ArrayList<GuiControl>();
    protected int width;
    protected int height;
    
    @Override
    public void render(Graphics2D g2d, int width, int height, int scrollX,
            int scrollY) {
	this.width = width;
	this.height = height;
	for(GuiControl c : controlList)
	    c.render(g2d, width, height, scrollX, scrollY);
    }
    
    public void tick() {
	
    }
    
    protected void drawBackground(Graphics2D g2d) {
	g2d.setColor(Color.BLACK);
	g2d.fillRect(0, 0, width, height);
    }
}
