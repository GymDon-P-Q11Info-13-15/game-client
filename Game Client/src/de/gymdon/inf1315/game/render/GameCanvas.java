package de.gymdon.inf1315.game.render;

import java.awt.*;

import javax.swing.*;

import de.gymdon.inf1315.game.render.gui.GuiScreen;

public class GameCanvas extends JPanel {
    private static final long serialVersionUID = 1L;
    public MapRenderer mapRenderer = null;
    public GuiScreen currentScreen = null;
    public int scrollX = 0;
    public int scrollY = 0;

    @Override
    public void paint(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;
	int width = getWidth();
	int height = getHeight();
	g2d.setColor(Color.BLACK);
	g2d.fillRect(0, 0, getWidth(), getHeight());
	if (mapRenderer != null) {
	    mapRenderer.render(g2d, width, height, scrollX, scrollY);
	}
	if (currentScreen != null) {
	    currentScreen.render(g2d, width, height, scrollX, scrollY);
	}
    }
}
