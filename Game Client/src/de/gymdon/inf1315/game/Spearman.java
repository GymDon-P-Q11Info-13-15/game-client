package de.gymdon.inf1315.game;

public class Spearman extends Unit {

    public Spearman(Player owner, int x, int y) {
	this.owner = owner;
	this.x = x;
	this.y = y;
	speed = 6;
	range = 1;
	attack = 40;
	defense = 20;
	hp = 80;
	cost = 80;
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
