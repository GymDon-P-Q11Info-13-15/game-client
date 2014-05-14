package de.gymdon.inf1315.game.render.gui;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

import de.gymdon.inf1315.game.client.Client;
import de.gymdon.inf1315.game.render.StandardTexture;

public class GuiMainMenu extends GuiScreen{

    private GuiButton button1 = new GuiButton(this, 0, 20, 20, "TestButton").setBackgroundColor(0xCCFFCC).setTextColor(0x000000);
    private GuiButton button2 = new GuiButton(this, 1, 540, 50, "ActionButton").setBackgroundColor(0xCCCCCF);
    public GuiMainMenu() {
	controlList.add(button1);
	controlList.add(button2);
    }
    @Override
    public void render(Graphics2D g2d, int width, int height, int scrollX,
            int scrollY) {
        super.render(g2d, width, height, scrollX, scrollY);
        int ticksRunning = Client.instance.getTicksRunning();
        //Button 1
	button1.setX((int) (Math.sin(ticksRunning/20F)*20+100));
	button1.setY((int) (Math.cos(ticksRunning/20F)*20+100));
	button1.setWidth((int) (-Math.sin(ticksRunning/20F)*20+200));
	button1.setHeight((int) (-Math.cos(ticksRunning/20F)*20+30));
	//Button 2
	button2.setWidth(button2.getText().length()*10 +40);
	button2.setHeight(75);
	button2.setX(width/2 - button2.getWidth()/2);
	button2.setY(height/2 - button2.getHeight()/2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getID() == ActionEvent.ACTION_PERFORMED) {
	    GuiButton button = (GuiButton)e.getSource();
	    if(button.getTexture() != null)
	    {
		button.setTexture(null);
	    }
	    else if(button.getTexture() == null)
	    {
		button.setTexture(StandardTexture.get("grass"));
	    }
	}
    }
}
