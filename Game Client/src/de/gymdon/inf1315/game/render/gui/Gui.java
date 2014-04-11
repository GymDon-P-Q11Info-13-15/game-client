package de.gymdon.inf1315.game.render.gui;

import java.awt.Graphics2D;

import de.gymdon.inf1315.game.render.Renderable;

public abstract class Gui implements Renderable {

    @Override
    public abstract void render(Graphics2D g2d, int width, int height,
	    int scrollX, int scrollY);
}
