package de.gymdon.inf1315.game;

import de.gymdon.inf1315.game.render.Texture;

public abstract class Building extends GameObject {
    int hp, defense, cost;

    public abstract void occupy(Player p);
    public abstract Texture getTexture();
}
