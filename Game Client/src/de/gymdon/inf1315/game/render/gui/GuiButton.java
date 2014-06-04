package de.gymdon.inf1315.game.render.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import de.gymdon.inf1315.game.client.Client;
import de.gymdon.inf1315.game.render.Texture;

public class GuiButton extends GuiControl {
    protected final int id;
    protected GuiScreen parent;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected String text;
    protected Object[] textData = new Object[0];
    protected Texture texture;
    protected boolean drawBackground = true;
    protected int bgColor = 0x7B5C3D;
    protected int textColor = 0xFFFFFF;
    protected int borderColor = 0x6C4824;
    protected int borderWidth = 5;
    protected int borderRadius = 20;
    protected Font font = Font.decode("Helvetica Bold 22");
    protected ButtonState lastState = ButtonState.NORMAL;
    protected ButtonState currentState = ButtonState.NORMAL;
    protected boolean enabled = true;
    private List<ActionListener> actionListeners = new ArrayList<ActionListener>();

    public GuiButton(GuiScreen parent, int id, int x, int y, String text) {
	this(parent, id, x, y, 100, 20, text);
    }

    public GuiButton(GuiScreen parent, int id, int x, int y, int width,
	    int height, String text) {
	this(parent, id, x, y, width, height, text, null);
    }

    public GuiButton(GuiScreen parent, int id, int x, int y, int width,
	    int height, String text, Texture texture) {
	this.parent = parent;
	this.id = id;
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;
	this.text = text;
	this.texture = texture;
	addActionListener(parent);
    }

    @Override
    public void render(Graphics2D g2d, int width, int height) {
	if(borderWidth > 0) {
	    int c = borderColor;
	    if(!enabled) {
		float[] hsb = Color.RGBtoHSB((borderColor>>16)&0xFF, (borderColor>>8)&0xFF, borderColor&0xFF, null);
		c = Color.HSBtoRGB(hsb[0], hsb[1]/2, Math.min(hsb[2]*1.2F, 1));
	    }
	    g2d.setColor(new Color(c));
	    if(borderRadius == 0) {
		g2d.fillRect(this.x - borderWidth, this.y - borderWidth, this.width + 2*borderWidth, this.height + 2*borderWidth);
	    }else {
		g2d.fillRoundRect(this.x - borderWidth, this.y - borderWidth, this.width + 2*borderWidth, this.height + 2*borderWidth, borderRadius, borderRadius);
	    }
	}
	if (texture != null) {
	    if (drawBackground)
		g2d.drawImage(texture.getImage(), x, y, x + this.width, y
			+ this.height, texture.getX(), texture.getY(),
			texture.getX() + texture.getWidth(), texture.getY()
				+ texture.getHeight(), new Color(bgColor),
			texture);
	    else
		g2d.drawImage(texture.getImage(), x, y, x + this.width, y
			+ this.height, texture.getX(), texture.getY(),
			texture.getX() + texture.getWidth(), texture.getY()
				+ texture.getHeight(), texture);
	} else if (drawBackground) {
	    int c = bgColor;
	    if(!enabled) {
		float[] hsb = Color.RGBtoHSB((bgColor>>16)&0xFF, (bgColor>>8)&0xFF, bgColor&0xFF, null);
		c = Color.HSBtoRGB(hsb[0], hsb[1]/2, Math.min(hsb[2]*1.2F, 1));
	    }
	    g2d.setColor(new Color(c));
	    if(borderRadius == 0)
		g2d.fillRect(x, y, this.width, this.height);
	    else
		g2d.fillRoundRect(this.x, this.y, this.width, this.height, borderRadius - borderWidth, borderRadius - borderWidth);
	}

	g2d.setFont(font);
	String translatedText = Client.instance.translation.translate(text, textData);
	Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(translatedText, g2d);
	g2d.setColor(new Color(textColor));
	g2d.drawString(translatedText,
		(float) (x + (this.width - bounds.getWidth()) / 2),
		(float) (y + (this.height + bounds.getHeight()) / 2));
    }

    protected void stateChanged() {
	if(lastState == ButtonState.ACTIVE && currentState == ButtonState.HOVER) {
	    ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, this.text);
	    for(ActionListener l : actionListeners)
		l.actionPerformed(e);
	}
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

    public String getText() {
	return text;
    }
    
    public Object[] getTextData() {
	return textData;
    }

    public Texture getTexture() {
	return texture;
    }

    public boolean isDrawBackground() {
	return drawBackground;
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

    public ButtonState getState() {
	return currentState;
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

    public boolean isEnabled() {
        return enabled;
    }

    public GuiButton setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public GuiButton setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        return this;
    }

    public GuiButton setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
        return this;
    }

    public GuiButton setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public GuiButton setParent(GuiScreen parent) {
	this.parent = parent;
	return this;
    }

    public GuiButton setX(int x) {
	this.x = x;
	return this;
    }

    public GuiButton setY(int y) {
	this.y = y;
	return this;
    }

    public GuiButton setWidth(int width) {
	this.width = width;
	return this;
    }

    public GuiButton setHeight(int height) {
	this.height = height;
	return this;
    }

    public GuiButton setText(String text) {
	this.text = text;
	return this;
    }
    
    public GuiButton setTextData(Object... data) {
	this.textData = data;
	return this;
    }

    public GuiButton setTexture(Texture texture) {
	this.texture = texture;
	return this;
    }

    public GuiButton setDrawBackground(boolean drawBackground) {
	this.drawBackground = drawBackground;
	return this;
    }

    public GuiButton setBackgroundColor(int bgColor) {
	this.bgColor = bgColor;
	return this;
    }

    public GuiButton setTextColor(int textColor) {
	this.textColor = textColor;
	return this;
    }

    public GuiButton setFont(Font font) {
	this.font = font;
	return this;
    }

    public GuiButton setState(ButtonState state) {
	if(!enabled)
	    return this;
	this.lastState = currentState;
	this.currentState = state;
	if (lastState != currentState)
	    stateChanged();
	return this;
    }

    public enum ButtonState {
	NORMAL, HOVER, ACTIVE;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
	int x = e.getX();
	int y = e.getY();
	if (x >= this.x && y >= this.y && x <= this.x + this.width && y <= this.y + this.height)
	    setState(ButtonState.HOVER);
	else if(currentState == ButtonState.HOVER)
	    setState(ButtonState.NORMAL);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
	int x = e.getX();
	int y = e.getY();
	if (x >= this.x && y >= this.y && x <= this.x + this.width && y <= this.y + this.height)
	    setState(ButtonState.ACTIVE);
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
	int x = e.getX();
	int y = e.getY();
	if (x >= this.x && y >= this.y && x <= this.x + this.width && y <= this.y + this.height)
	    setState(ButtonState.HOVER);
	else
	    setState(ButtonState.NORMAL);
    }

    @Override
    public void addActionListener(ActionListener l) {
	actionListeners.add(l);
    }

    @Override
    public void removeActionListener(ActionListener l) {
	actionListeners.remove(l);
	
    }
}
