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

public class GuiPauseMenu extends GuiScreen{

    private GuiButton continueGame = new GuiButton(this, 0, 20, 20, "gui.game.continue");
    private GuiButton options = new GuiButton(this, 1, 20, 20, "gui.options");
    private GuiButton mainMenu = new GuiButton(this, -1, 20, 20, "gui.back.mainmenu");
    public GuiPauseMenu() {
	controlList.add(continueGame);
	controlList.add(options);
	controlList.add(mainMenu);
    }
    @Override
    public void render(Graphics2D g2d, int width, int height) {
	
	g2d.drawImage(Client.instance.mapren.getMapBackground(), 0, 0, null);
        Font f = Client.instance.translation.font.deriveFont(Font.BOLD, 120F);
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
	continueGame.setX(leftMargin);
	continueGame.setY(topMargin);
	continueGame.setWidth(buttonWidth);
	continueGame.setHeight(buttonHeight);
	
	options.setX(leftMargin);
	options.setY(topMargin + (buttonHeight + buttonSpacing));
	options.setWidth(buttonWidth);
	options.setHeight(buttonHeight);
	
	mainMenu.setX(leftMargin);
	mainMenu.setY(topMargin + (buttonHeight + buttonSpacing)*2);
	mainMenu.setWidth(buttonWidth);
	mainMenu.setHeight(buttonHeight);
        super.render(g2d, width, height);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getID() == ActionEvent.ACTION_PERFORMED) {
	    GuiButton button = (GuiButton)e.getSource();
	    if(button == continueGame)
		Client.instance.activateMap(false);
	    else if(button == options)
		Client.instance.setGuiScreen(new GuiOptions(this));
	    else if(button == mainMenu)
		Client.instance.setGuiScreen(new GuiMainMenu());
	}
    }
}
