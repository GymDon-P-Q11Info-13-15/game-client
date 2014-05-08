package de.gymdon.inf1315.game;

public abstract class Building extends GameObject {
int hp, defense;
    public abstract void occupy(Player p);
}
