package de.gymdon.inf1315.game.render.gui;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

import de.gymdon.inf1315.game.Game;

public class GuiMainMenu extends GuiScreen{

    private GuiButton button = new GuiButton(this, 0, 20, 20, "TestButton").setBackgroundColor(0xCCFFCC).setTextColor(0x000000);
    private GuiButton button2 = new GuiButton(this, 1, 540, 50, "ActionButton").setBackgroundColor(0xCCCCCF);
    public GuiMainMenu() {
	controlList.add(button);
	controlList.add(button2);
    }
    @Override
    public void render(Graphics2D g2d, int width, int height, int scrollX,
            int scrollY) {
        super.render(g2d, width, height, scrollX, scrollY);
        int ticksRunning = Game.instance.getTicksRunning();
        //Button 1
	button.setX((int) (Math.sin(ticksRunning/20F)*20+100));
	button.setY((int) (Math.cos(ticksRunning/20F)*20+100));
	button.setWidth((int) (-Math.sin(ticksRunning/20F)*20+200));
	button.setHeight((int) (-Math.cos(ticksRunning/20F)*20+30));
	//Button 2
	button2.setWidth(button2.getText().length()*10 +40);
	button2.setHeight(75);
	button2.setX(width/2 - button2.getWidth()/2);
	button2.setY(height/2 - button2.getHeight()/2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getID() == ActionEvent.ACTION_PERFORMED)
	    System.out.println(((GuiButton)e.getSource()).getText());
    }
    
}
