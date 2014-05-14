package de.gymdon.inf1315.game.render.gui;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

import de.gymdon.inf1315.game.client.Client;
import de.gymdon.inf1315.game.render.StandardTexture;

public class GuiMainMenu extends GuiScreen{

    private GuiButton button1 = new GuiButton(this, 0, 20, 20, "TestButton").setBackgroundColor(0xCCFFCC).setTextColor(0x000000);
    private GuiButton button2 = new GuiButton(this, 1, 20, 20, "ActionButton1").setBackgroundColor(0xCCCCCF);
    private GuiButton button3 = new GuiButton(this, 1, 20, 20, "ActionButton2").setBackgroundColor(0xCCCCCF);
    private GuiButton button4 = new GuiButton(this, 1, 20, 20, "ActionButton3").setBackgroundColor(0xCCCCCF);
    private GuiButton button5 = new GuiButton(this, 1, 20, 20, "ActionButton4").setBackgroundColor(0xCCCCCF);
    public GuiMainMenu() {
	controlList.add(button1);
	controlList.add(button2);
	controlList.add(button3);
	controlList.add(button4);
	controlList.add(button5);
    }
    @Override
    public void render(Graphics2D g2d, int width, int height, int scrollX,
            int scrollY) {
        super.render(g2d, width, height, scrollX, scrollY);
        int ticksRunning = Client.instance.getTicksRunning();
        int Abstand = (width + height)/10;
        //Button 1
	button1.setX((int) (Math.sin(ticksRunning/20F)*20+100));
	button1.setY((int) (Math.cos(ticksRunning/20F)*20+100));
	button1.setWidth((int) (-Math.sin(ticksRunning/20F)*20+200));
	button1.setHeight((int) (-Math.cos(ticksRunning/20F)*20+30));
	//Button 2
	button2.setWidth(button2.getText().length()*10 + 40);
	button2.setHeight(75);
	button2.setX(width/2 - button2.getWidth()/2 - Abstand);
	button2.setY(height/2 - button2.getHeight()/2 - Abstand/2);
	//Button 3
	button3.setWidth(button3.getText().length()*10 + 40);
	button3.setHeight(75);
	button3.setX(width/2 - button3.getWidth()/2 + Abstand);
	button3.setY(height/2 - button3.getHeight()/2 - Abstand/2);
	//Button 4
	button4.setWidth(button4.getText().length()*10 + 40);
	button4.setHeight(75);
	button4.setX(width/2 - button4.getWidth()/2 - Abstand);
	button4.setY(height/2 - button4.getHeight()/2 + Abstand/2);
	//Button 5
	button5.setWidth(button5.getText().length()*10 + 40);
	button5.setHeight(75);
	button5.setX(width/2 - button5.getWidth()/2 + Abstand);
	button5.setY(height/2 - button5.getHeight()/2 + Abstand/2);
	
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
