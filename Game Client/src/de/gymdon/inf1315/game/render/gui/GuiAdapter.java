package de.gymdon.inf1315.game.render.gui;

public interface GuiAdapter {
    public int getHeight(int index, GuiScrollList parent);
    public int getWidth(int index, GuiScrollList parent);
    public Gui get(int index, GuiScrollList parent);
    public int getLength(GuiScrollList parent);
}
