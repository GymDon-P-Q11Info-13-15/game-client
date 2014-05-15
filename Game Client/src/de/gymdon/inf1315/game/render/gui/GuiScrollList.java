package de.gymdon.inf1315.game.render.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Shape;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

import de.gymdon.inf1315.game.render.Texture;
import de.gymdon.inf1315.game.render.gui.GuiButton.ButtonState;

public class GuiScrollList extends GuiControl {

    protected GuiScreen parent;
    protected int id;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected List<ActionListener> actionListeners = new ArrayList<ActionListener>();
    protected Font font;
    protected int bgColor = 0x7B5C3D;
    protected int textColor = 0xFFFFFF;
    protected int borderColor = 0x6C4824;
    protected int borderWidth = 5;
    protected int borderRadius = 20;
    protected GuiAdapter adapter;
    protected int scroll = 0;
    protected int totalHeight;

    public GuiScrollList(GuiScreen parent, GuiAdapter adapter, int id, int x, int y) {
	this(parent, adapter, id, x, y, 100, 20);
    }

    public GuiScrollList(GuiScreen parent, GuiAdapter adapter, int id, int x, int y, int width,
	    int height) {
	this.parent = parent;
	this.id = id;
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;
	this.adapter = adapter;
	addActionListener(parent);
    }

    @Override
    public void render(Graphics2D g2d, int width, int height, int scrollX, int scrollY) {
	if(borderWidth > 0) {
	    g2d.setColor(new Color(borderColor));
	    if(borderRadius == 0) {
		g2d.fillRect(this.x - borderWidth, this.y - borderWidth, this.width + 2*borderWidth, this.height + 2*borderWidth);
	    }else {
		g2d.fillRoundRect(this.x - borderWidth, this.y - borderWidth, this.width + 2*borderWidth, this.height + 2*borderWidth, borderRadius, borderRadius);
	    }
	}
	Shape clip = g2d.getClip();
	RoundRectangle2D clipRight = new RoundRectangle2D.Float(this.x + this.width - 40 + 5, this.y, 40 - 5, this.height, borderRadius - borderWidth, borderRadius - borderWidth);
	RoundRectangle2D clipMain = new RoundRectangle2D.Float(this.x, this.y, this.width - 40, this.height, borderRadius - borderWidth, borderRadius - borderWidth);
	g2d.setClip(clipRight);
	drawBackground(g2d, width, height);
	g2d.setClip(clipMain);
	drawBackground(g2d, width, height);
	//g2d.fillRect(0, 0, width, height);
	AffineTransform tx = g2d.getTransform();
	totalHeight = 0;
	g2d.translate(0, -scroll);
	for(int i = 0; i < adapter.getLength(this); i++) {
	    adapter.get(i, this).render(g2d, this.width, this.height, 0, 0);
	    int h = adapter.getHeight(i, this);
	    g2d.translate(0, h);
	    totalHeight += h;
	}
	g2d.setTransform(tx);
	g2d.setClip(clip);
    }
    
    protected void drawBackground(Graphics2D g2d, int width, int height) {
        LinearGradientPaint gradient = new LinearGradientPaint(width*0.7F, 0, width*0.3F, height, 
        	new float[]{0,1}, 
        	new Color[]{new Color(0xc69c6d), new Color(0x754c24)});
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);
    }
    
    public int getId() {
	return id;
    }

    public GuiScreen getParent() {
	return parent;
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public int getBackgroundColor() {
	return bgColor;
    }

    public int getTextColor() {
	return textColor;
    }

    public Font getFont() {
	return font;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public int getBorderRadius() {
        return borderRadius;
    }

    public GuiScrollList setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public GuiScrollList setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        return this;
    }

    public GuiScrollList setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
        return this;
    }

    public GuiScrollList setParent(GuiScreen parent) {
	this.parent = parent;
	return this;
    }

    public GuiScrollList setX(int x) {
	this.x = x;
	return this;
    }

    public GuiScrollList setY(int y) {
	this.y = y;
	return this;
    }

    public GuiScrollList setWidth(int width) {
	this.width = width;
	return this;
    }

    public GuiScrollList setHeight(int height) {
	this.height = height;
	return this;
    }

    public GuiScrollList setBackgroundColor(int bgColor) {
	this.bgColor = bgColor;
	return this;
    }

    public GuiScrollList setTextColor(int textColor) {
	this.textColor = textColor;
	return this;
    }

    public GuiScrollList setFont(Font font) {
	this.font = font;
	return this;
    }

    public enum ButtonState {
	NORMAL, HOVER, ACTIVE;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
	int x = e.getX();
	int y = e.getY();
	/*if (x >= this.x && y >= this.y && x <= this.x + this.width && y <= this.y + this.height)
	    setState(ButtonState.HOVER);
	else if(currentState == ButtonState.HOVER)
	    setState(ButtonState.NORMAL);*/
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
	int x = e.getX();
	int y = e.getY();
	/*if (x >= this.x && y >= this.y && x <= this.x + this.width && y <= this.y + this.height)
	    setState(ButtonState.ACTIVE);*/
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
	int x = e.getX();
	int y = e.getY();
	/*if (x >= this.x && y >= this.y && x <= this.x + this.width && y <= this.y + this.height)
	    setState(ButtonState.HOVER);
	else
	    setState(ButtonState.NORMAL);*/
    }

    @Override
    public void addActionListener(ActionListener l) {
	actionListeners .add(l);
    }

    @Override
    public void removeActionListener(ActionListener l) {
	actionListeners.remove(l);
	
    }
}
