package de.gymdon.inf1315.game.render.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.*;

import javax.swing.event.MouseInputListener;

public abstract class GuiScreen extends Gui implements ActionListener,
	MouseInputListener {
    public List<GuiControl> controlList = new ArrayList<GuiControl>();
    protected int width;
    protected int height;

    @Override
    public void render(Graphics2D g2d, int width, int height, int scrollX,
	    int scrollY) {
	this.width = width;
	this.height = height;
	for (GuiControl c : controlList)
	    c.render(g2d, width, height, scrollX, scrollY);
    }

    public void tick() {

    }

    protected void drawBackground(Graphics2D g2d) {
	g2d.setColor(Color.BLACK);
	g2d.fillRect(0, 0, width, height);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
	for (GuiControl c : controlList)
	    c.mouseClicked(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
	for (GuiControl c : controlList)
	    c.mouseDragged(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
	for (GuiControl c : controlList)
	    c.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
	for (GuiControl c : controlList)
	    c.mouseExited(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
	for (GuiControl c : controlList)
	    c.mouseMoved(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
	for (GuiControl c : controlList)
	    c.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
	for (GuiControl c : controlList)
	    c.mouseReleased(e);
    }
}
