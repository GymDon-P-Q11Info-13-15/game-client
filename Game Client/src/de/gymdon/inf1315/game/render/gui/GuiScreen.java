package de.gymdon.inf1315.game.render.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
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
    public void render(Graphics2D g2d, int width, int height) {
	this.width = width;
	this.height = height;
	for (GuiControl c : controlList)
	    c.render(g2d, width, height);
    }

    public void tick() {

    }
    
    public void rebuild() {
	
    }

    protected void drawBackground(Graphics2D g2d, int width, int height) {
        LinearGradientPaint gradient = new LinearGradientPaint(width*0.7F, 0, width*0.3F, height, 
        	new float[]{0,1}, 
        	new Color[]{new Color(0xc69c6d), new Color(0x754c24)});
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);
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
}
