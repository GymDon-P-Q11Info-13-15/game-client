package de.gymdon.inf1315.game.render.gui;

import java.awt.Graphics2D;

import de.gymdon.inf1315.game.Game;

public class GuiMainMenu extends GuiScreen{

    private GuiButton button = new GuiButton(this, 0, 20, 20, "TestButton").setBackgroundColor(0xCCFFCC).setTextColor(0x000000);
    public GuiMainMenu() {
	controlList.add(button);
    }
    @Override
    public void render(Graphics2D g2d, int width, int height, int scrollX,
            int scrollY) {
        super.render(g2d, width, height, scrollX, scrollY);
        int ticksRunning = Game.instance.getTicksRunning();
	button.setX((int) (Math.sin(ticksRunning/20F)*20+100));
	button.setY((int) (Math.cos(ticksRunning/20F)*20+100));
	button.setWidth((int) (-Math.sin(ticksRunning/20F)*20+200));
	button.setHeight((int) (-Math.cos(ticksRunning/20F)*20+30));
    }
    
}
