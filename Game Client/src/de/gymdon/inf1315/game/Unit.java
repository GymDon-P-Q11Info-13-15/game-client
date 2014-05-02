package de.gymdon.inf1315.game;

public abstract class Unit extends GameObject {
int hp, speed, attack, defense;

public abstract void move();
public abstract void attack();
public abstract void setHP(int health);

}
