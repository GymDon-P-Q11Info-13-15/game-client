package de.gymdon.inf1315.game.render.gui;

import java.awt.Graphics2D;

public class GuiMainMenu extends GuiScreen{

    public GuiMainMenu() {
	controlList.add(new GuiButton(this, 0, 20, 20, "TestButton").setBackgroundColor(0xCCFFCC).setTextColor(0x000000));
    }
    @Override
    public void render(Graphics2D g2d, int width, int height, int scrollX,
            int scrollY) {
        super.render(g2d, width, height, scrollX, scrollY);
    }
    
}
