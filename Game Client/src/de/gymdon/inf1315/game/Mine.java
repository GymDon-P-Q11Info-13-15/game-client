package de.gymdon.inf1315.game;

import de.gymdon.inf1315.game.render.StandardTexture;
import de.gymdon.inf1315.game.render.Texture;

public class Mine extends Building {

    int income;
    public boolean superior = true;

    public Mine(int x, int y) {
	this.x = x;
	this.y = y;

    }

    @Override
    public void occupy(Player p) {
	this.owner = p;

    }

    @Override
    public Texture getTexture() {
	return superior ? new StandardTexture("mine_superior") : owner == null ? new StandardTexture("mine_neutral") : new StandardTexture("mine_" + owner.color.name().toLowerCase());
    }

}
