package de.gymdon.inf1315.game;

public class Spearman extends Unit
{
    Player own;
    public Spearman(Player owner,int x, int y)
    {
	own=owner;
	this.x = x;
	this.y = y;
	speed = 0;
	range = 0;
	attack = 0;
	defense = 0;
	hp = 0;
    }
    
    @Override
    public void move() {
	
	
    }
    @Override
    public void attack() {
	
    }
    @Override
    public void setHP(int health) {
	
	
    }
    @Override
    public int getSpeed() {
	// TODO Auto-generated method stub
	return speed;
    }

}
