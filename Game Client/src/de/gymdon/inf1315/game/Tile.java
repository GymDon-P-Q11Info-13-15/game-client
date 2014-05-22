package de.gymdon.inf1315.game;

import de.gymdon.inf1315.game.render.StandardTexture;
import de.gymdon.inf1315.game.render.Texture;

public class Tile {
    public Texture texture;
    public int groundFactor;
    public boolean isWalkable;
    
    public Tile(String text)
    {
	texture = new StandardTexture(text);
    }
}
