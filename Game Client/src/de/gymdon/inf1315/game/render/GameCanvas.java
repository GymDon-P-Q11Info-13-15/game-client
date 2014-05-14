package de.gymdon.inf1315.game.render;

import java.awt.*;

import javax.swing.*;

import de.gymdon.inf1315.game.client.Client;

public class GameCanvas extends JPanel {
    private static final long serialVersionUID = 1L;
    public MapRenderer mapRenderer = null;
    public int scrollX = 0;
    public int scrollY = 0;

    @Override
    public void paint(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;
	g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	int width = getWidth();
	int height = getHeight();
	g2d.setColor(Color.BLACK);
	g2d.fillRect(0, 0, getWidth(), getHeight());
	if (mapRenderer != null)
	    mapRenderer.render(g2d, width, height, scrollX, scrollY);
	if (Client.instance.currentScreen != null)
	    Client.instance.currentScreen.render(g2d, width, height, scrollX,  scrollY);
    }
}
