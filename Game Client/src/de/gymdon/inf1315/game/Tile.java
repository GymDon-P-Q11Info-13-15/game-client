package de.gymdon.inf1315.game;

public class Tile {
    public double groundFactor;
    private boolean walkable;
    public double f;
    public double g = groundFactor;
    public double h;
    
    private Tile(int id, String name) {
	// TODO use name
    }
    
    private Tile setGroundFactor(int groundFactor) {
	this.groundFactor = groundFactor;
	return this;
    }
    
    public double getGroundFactor() {
	return groundFactor;
    }
    
    /* Not needed right now
    private Tile setWalkable(boolean walkable) {
	this.walkable = walkable;
	return this;
    }*/
    
    public boolean isWalkable() {
	return walkable;
    }

    public static final Tile grass = new Tile(0, "grass").setGroundFactor(1);
    public static final Tile grass2 = new Tile(0, "grass").setGroundFactor(1);
    public static final Tile sand = new Tile(1, "sand").setGroundFactor(3);
    public static final Tile sand2 = new Tile(1, "sand").setGroundFactor(3);
    public static final Tile water = new Tile(2, "water").setGroundFactor(2);
    public static final Tile water2 = new Tile(2, "water").setGroundFactor(2);
}
