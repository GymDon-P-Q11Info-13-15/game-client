package de.gymdon.inf1315.game.render;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import de.gymdon.inf1315.game.client.Client;

public class GameCanvas extends JPanel {
    private static final long serialVersionUID = 1L;
    public MapRenderer mapRenderer = null;
    public int scrollX = 0;
    public int scrollY = 0;

    @Override
    public void paint(Graphics g) {
	int width = getWidth();
	int height = getHeight();
	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	Graphics2D g2d = (Graphics2D)image.createGraphics();
	g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g2d.setColor(Color.BLACK);
	g2d.fillRect(0, 0, getWidth(), getHeight());
	if (mapRenderer != null)
	    mapRenderer.render(g2d, width, height, scrollX, scrollY);
	if (Client.instance.currentScreen != null)
	    Client.instance.currentScreen.render(g2d, width, height, scrollX,  scrollY);
	g2d.dispose();
	
	g.drawImage(image, 0, 0, null);
    }
}
