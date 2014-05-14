package de.gymdon.inf1315.game;

public class Knight extends Unit {

    public Knight(Player owner, int x, int y) {
	this.owner = owner;
	this.x = x;
	this.y = y;
	speed = 4;
	range = -1;
	attack = 40;
	defense = 50;
	hp = 120;
    }

    @Override
    public void move(int x, int y) {
	super.move(x, y);
    }

    @Override
    public void attack() {

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
