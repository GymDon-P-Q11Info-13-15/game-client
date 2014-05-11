package de.gymdon.inf1315.game;

public class Swordsman extends Unit {

    public Swordsman(Player owner, int x, int y) {
	this.owner = owner;
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
