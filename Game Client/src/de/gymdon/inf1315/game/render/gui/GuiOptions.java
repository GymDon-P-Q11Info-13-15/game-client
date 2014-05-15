package de.gymdon.inf1315.game.render.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.io.FileWriter;
import java.io.IOException;

import de.gymdon.inf1315.game.client.Client;

public class GuiOptions extends GuiScreen {
    
    private GuiScreen last;
    private Section section;
    private GuiButton backButton = new GuiButton(this, 0, 300, 550, "gui.back");
    
    //Sections
    private GuiButton videoButton = new GuiButton(this, 0, 100, 200, "gui.options.video");
    private GuiButton languageButton = new GuiButton(this, 0, 100, 200, "gui.options.language");
    // -- Video
    private GuiButton videoVsyncButton = new GuiButton(this, 0, 100, 200, 
	    "gui.options.video.vsync." + (Client.instance.preferences.video.vsync ? "on" : "off"));
    // -- Language
    private GuiButton ENButton = new GuiButton(this, 0, 100, 200, "gui.options.language.en");
    private GuiButton DEButton = new GuiButton(this, 0, 100, 200, "gui.options.language.de");
    
    public GuiOptions() {
	setSection(Section.MAIN);
    }
    
    @Override
    public void render(Graphics2D g2d, int width, int height, int scrollX, int scrollY) {
	drawBackground(g2d, width, height);
        
        Font f = Font.decode("Helvetica 80");
        g2d.setFont(f);
        String title = Client.instance.translation.translate("gui.options" + (section != Section.MAIN ? "." + section.name().toLowerCase() : ""));
        Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(title, g2d);
        int titleX = (int) (width/2 - bounds.getCenterX());
        int titleY = (int) (80 + bounds.getMaxY());
        g2d.setColor(Color.WHITE);
        g2d.drawString(title, titleX, titleY);
        
	int buttonWidth = width - width/4;
	int buttonHeight = height/10;
	int buttonSpacing = buttonHeight/4;
	int topMargin = 150;
	int leftMargin = width/2 - buttonWidth/2;
	int buttonWidthSmall = (buttonWidth - buttonSpacing)/2;
	backButton.setX(leftMargin);
	backButton.setY(height - buttonSpacing - buttonHeight);
	backButton.setWidth(buttonWidth);
	backButton.setHeight(buttonHeight);
	
	if (section == Section.MAIN) {
	    videoButton.setX(leftMargin);
	    videoButton.setY(topMargin);
	    videoButton.setWidth(buttonWidthSmall);
	    videoButton.setHeight(buttonHeight);
	    
	    languageButton.setX(leftMargin + (buttonWidthSmall + buttonSpacing));
	    languageButton.setY(topMargin);
	    languageButton.setWidth(buttonWidthSmall);
	    languageButton.setHeight(buttonHeight);
	} else if (section == Section.VIDEO) {
	    videoVsyncButton.setX(leftMargin);
	    videoVsyncButton.setY(topMargin);
	    videoVsyncButton.setWidth(buttonWidthSmall);
	    videoVsyncButton.setHeight(buttonHeight);
	} else if (section == Section.LANGUAGE) {
	    ENButton.setX(leftMargin);
	    ENButton.setY(topMargin);
	    ENButton.setWidth(buttonWidthSmall);
	    ENButton.setHeight(buttonHeight);
	    
	    DEButton.setX(leftMargin + (buttonWidthSmall + buttonSpacing));
	    DEButton.setY(topMargin);
	    DEButton.setWidth(buttonWidthSmall);
	    DEButton.setHeight(buttonHeight);
	}
        super.render(g2d, width, height, scrollX, scrollY);
    }
    
    public GuiOptions(GuiScreen last) {
	this();
	this.last = last;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getID() == ActionEvent.ACTION_PERFORMED) {
	    GuiButton button = (GuiButton)e.getSource();
	    if(button == backButton) {
		if(section != Section.MAIN)
		    setSection(Section.MAIN);
		else {
		    Client.instance.setGuiScreen(last);
		    try {
			Client.instance.preferences.write(new FileWriter("preferences.json"));
		    } catch (IOException e1) {
			System.err.println("Unable to save preferences");
		    }
		}
	    }else if(button == videoButton) {
		setSection(Section.VIDEO);
	    }else if(button == videoVsyncButton) {
		Client.instance.preferences.video.vsync = !Client.instance.preferences.video.vsync;
		videoVsyncButton.setText("gui.options.video.vsync." + (Client.instance.preferences.video.vsync ? "on" : "off"));
		
	    }else if(button == languageButton) {
		setSection(Section.LANGUAGE);
	    }else if(button == ENButton) {
		
	    }else if(button == DEButton) {
		
	    }
	}
    }
    
    private void setSection(Section s) {
	section = s;
	controlList.clear();
	switch(s) {
	case MAIN:
	    controlList.add(videoButton);
	    controlList.add(languageButton);
	    break;
	case VIDEO:
	    controlList.add(videoVsyncButton);
	    break;
	case LANGUAGE:
	    controlList.add(ENButton);
	    controlList.add(DEButton);
	    break;
	}
	controlList.add(backButton);
    }

    private enum Section {
	MAIN, VIDEO, LANGUAGE;
    }
}
