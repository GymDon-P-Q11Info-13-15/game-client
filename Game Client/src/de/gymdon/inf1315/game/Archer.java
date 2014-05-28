package de.gymdon.inf1315.game;

public class Archer extends Unit {

    public Archer(Player owner, int x, int y) {
	this.owner = owner;
	this.x = x;
	this.y = y;
	speed = 8;
	range = 4;
	attack = 20;
	defense = 20;
	hp = 70;
	cost = 60;
    }

    @Override
    public void move(int x, int y) {
	super.move(x, y);
    }

    @Override
    public void attack() {

	// TODO: Generate attack() method

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
