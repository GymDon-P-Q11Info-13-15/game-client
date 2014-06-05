package de.gymdon.inf1315.game.render;

import java.util.Map;
import java.util.HashMap;

import de.gymdon.inf1315.game.Tile;

public class TileRenderMap {
    
    private static Map<Tile, Texture> map = new HashMap<Tile, Texture>();

    public static Texture getTexture(Tile t) {
	return map.get(t);
    }
    
    static {
	map.put(Tile.grass, new StandardTexture("grass"));
	map.put(Tile.grass2, new StandardTexture("grass"));
	map.put(Tile.sand, new StandardTexture("sand"));
	map.put(Tile.sand2, new StandardTexture("sand"));
	map.put(Tile.water, new StandardTexture("water"));
	map.put(Tile.water2, new StandardTexture("water"));
    }
}
