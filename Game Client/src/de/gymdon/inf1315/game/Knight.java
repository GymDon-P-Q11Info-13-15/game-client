package de.gymdon.inf1315.game;

public class Knight extends Unit {
    Player own;

    public Knight(Player owner, int x, int y) {
	own = owner;
	this.x = x;
	this.y = y;
	speed = 0;
	range = 0;
	attack = 0;
	defense = 0;
	hp = 0;
    }

    @Override
    public void move(int x, int y) {

	if ((this.x = this.x + x) > 0)
	    this.x = this.x + x;
	else
	    throw new IllegalArgumentException(
		    "X - Coordinate must be greater than 0!");
	if ((this.y = this.y + y) > 0)
	    this.y = this.y + y;
	else
	    throw new IllegalArgumentException(
		    "Y - Coordinate must be greater than 0!");

    }

    @Override
    public void attack() {
	// TODO Auto-generated method stub

    }

    @Override
    public void setHP(int hp) {

	this.hp = hp;

    }

    @Override
    public int getSpeed() {

	return speed;
    }

}
