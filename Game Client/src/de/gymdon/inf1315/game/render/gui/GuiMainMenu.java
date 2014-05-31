package de.gymdon.inf1315.game.render.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

import de.gymdon.inf1315.game.client.Client;

public class GuiMainMenu extends GuiScreen{

    private GuiButton newGame = new GuiButton(this, 0, 20, 20, "gui.game.new");
    private GuiButton options = new GuiButton(this, 1, 20, 20, "gui.options");
    private GuiButton test = new GuiButton(this, 2, 20, 20, "gui.test");
    private GuiButton exit = new GuiButton(this, -1, 20, 20, "gui.exit");
    public GuiMainMenu() {
	controlList.add(newGame);
	controlList.add(options);
	controlList.add(test);
	controlList.add(exit);
    }
    @Override
    public void render(Graphics2D g2d, int width, int height) {
	drawBackground(g2d, width, height);
        //int ticksRunning = Client.instance.getTicksRunning(); //not needed right now. Maybe later?
        
        Font f = Font.decode("Helvetica Bold 120");
        g2d.setFont(f);
        Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(Client.instance.translation.translate("game.title"), g2d);
        int titleX = (int) (width/2 - bounds.getCenterX());
        int titleY = (int) (height/3 - 50 + bounds.getCenterY());
        GlyphVector gv = f.createGlyphVector(g2d.getFontRenderContext(), Client.instance.translation.translate("game.title"));
        Shape outline = gv.getOutline();
        g2d.translate(titleX, titleY);
        {
	    g2d.setColor(new Color(0x6C4824));
	    g2d.setStroke(new BasicStroke(6F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
	    g2d.draw(outline);
	    g2d.setColor(new Color(0xc69c6d));
	    g2d.setStroke(new BasicStroke(1));
	    g2d.fill(outline);
        }
        g2d.translate(-titleX, -titleY);
        
        
        int topMargin = height/3;
	int buttonWidth = width - width/4;
	int buttonHeight = height/10;
	int buttonSpacing = buttonHeight/4;
	int leftMargin = width/2 - buttonWidth/2;
	int buttonWidthSmall = (buttonWidth - buttonSpacing)/2;
	int buttonWidthVerySmall = (buttonWidthSmall - buttonSpacing)/2;
	newGame.setX(leftMargin);
	newGame.setY(topMargin);
	newGame.setWidth(buttonWidth);
	newGame.setHeight(buttonHeight);
	
	options.setX(leftMargin);
	options.setY(topMargin + (buttonHeight + buttonSpacing));
	options.setWidth(buttonWidth);
	options.setHeight(buttonHeight);

	test.setX(leftMargin);
	test.setY(height - buttonSpacing - buttonHeight);
	test.setWidth(buttonWidthVerySmall);
	test.setHeight(buttonHeight);
	
	exit.setX(width - leftMargin - buttonWidthVerySmall);
	exit.setY(height - buttonSpacing - buttonHeight);
	exit.setWidth(buttonWidthVerySmall);
	exit.setHeight(buttonHeight);
        super.render(g2d, width, height);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getID() == ActionEvent.ACTION_PERFORMED) {
	    GuiButton button = (GuiButton)e.getSource();
	    if(button == newGame)
		Client.instance.setGuiScreen(new GuiSelectServer(this));
	    else if(button == test)
		Client.instance.activateMap();
	    else if(button == options)
		Client.instance.setGuiScreen(new GuiOptions(this));
	    else if(button == exit)
		Client.instance.stop();
	}
    }
}
