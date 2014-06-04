package de.gymdon.inf1315.game;

import de.gymdon.inf1315.game.render.StandardTexture;
import de.gymdon.inf1315.game.render.Texture;

public class Castle extends Building {

    public Castle(Player owner, int x, int y) {
	this.owner = owner;
	this.x = x;
	this.y = y;
	this.hp = 10000;
	this.defense = 80;
    }

    @Override
    public void occupy(Player p) {
	// cannot be occupied
	// sure can it be occupied, if occupied the game is over ;)
    }
    

    @Override
    public Texture getTexture() {
	return owner == null ? new StandardTexture("castle_neutral_big") : new StandardTexture("castle_" + owner.color.name().toLowerCase() + "_small");
    }
    
    public int getSizeX() {
	return 2;
    }
    
    public int getSizeY() {
	return 2;
    }

}
