package de.gymdon.inf1315.game;

public abstract class Unit extends GameObject {
    int hp, speed, attack, defense, range, cost, act_speed;

    public void move(int x, int y) {
	if (this.x + x < 0)
	    throw new IllegalArgumentException(
		    "X - Coordinate must not be negative!");
	this.x = this.x + x;
	if (this.y + y < 0)
	    throw new IllegalArgumentException(
		    "Y - Coordinate must not be negative!");
	this.y = this.y + y;
    }

    public abstract void attack();

    public abstract void setHP(int health);

    public abstract int getSpeed();
    
    public abstract void clicked();
    
   /* public boolean[][] attackableFoes(){
	Player attacker = this.owner;
	
	
	
	
    }
*/
}
