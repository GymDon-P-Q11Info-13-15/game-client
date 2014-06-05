package de.gymdon.inf1315.game.render.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.gymdon.inf1315.game.Utils;
import de.gymdon.inf1315.game.client.Client;
import de.gymdon.inf1315.game.render.StandardTexture;

public class GuiOptions extends GuiScreen {

    private GuiScreen last;
    private Section section;
    private GuiButton backButton = new GuiButton(this, 0, 300, 550, "gui.back");

    // Sections
    private GuiButton videoButton = new GuiButton(this, 0, 100, 200, "gui.options.video");
    // -- Video
    private GuiButton videoVsyncButton = new GuiButton(this, 0, 100, 200, "gui.options.video.vsync." + (Client.instance.preferences.video.vsync ? "on" : "off"));
    private GuiButton videoFullscreenButton = new GuiButton(this, 0, 100, 200, "gui.options.video.fullscreen." + (Client.instance.preferences.video.fullscreen ? "on" : "off"));
    // -- Language
    private GuiButton languageButton = new GuiButton(this, 0, 100, 200, "gui.options.language");
    private List<GuiButton> languageButtons = new ArrayList<GuiButton>();
    // -- Game Options
    private GuiButton gameButton = new GuiButton(this, 0, 100, 200, "gui.options.game");
    private GuiButton gameArrowButton = new GuiButton(this, 0, 100, 200, "gui.options.game.arrow");
    // -- Arrows
    private List<GuiButton> arrowButtons = new ArrayList<GuiButton>();

    public GuiOptions() {
	setSection(Section.MAIN);
    }

    public GuiOptions(GuiScreen last) {
	this();
	this.last = last;
    }
    
    public void rebuild() {
	backButton = new GuiButton(this, 0, 300, 550, "gui.back");
	videoButton = new GuiButton(this, 0, 100, 200, "gui.options.video");
	videoVsyncButton = new GuiButton(this, 0, 100, 200, "gui.options.video.vsync." + (Client.instance.preferences.video.vsync ? "on" : "off"));
	videoFullscreenButton = new GuiButton(this, 0, 100, 200, "gui.options.video.fullscreen." + (Client.instance.preferences.video.fullscreen ? "on" : "off"));
	languageButton = new GuiButton(this, 0, 100, 200, "gui.options.language");
	languageButtons.clear();
	gameButton = new GuiButton(this, 0, 100, 200, "gui.options.game");
	gameArrowButton = new GuiButton(this, 0, 100, 200, "gui.options.game.arrow");
	arrowButtons.clear();
	setSection(section);
    }

    @Override
    public void render(Graphics2D g2d, int width, int height) {
	drawBackground(g2d, width, height);

	Font f = Client.instance.translation.font.deriveFont(Font.BOLD, 80F);
	g2d.setFont(f);
	String title = Client.instance.translation.translate("gui.options" + (section != Section.MAIN ? "." + section.name().toLowerCase() : ""));
	Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(title, g2d);
	int titleX = (int) (width / 2 - bounds.getCenterX());
	int titleY = (int) (80 + bounds.getMaxY());
	g2d.setColor(Color.WHITE);
	g2d.drawString(title, titleX, titleY);

	int buttonWidth = width - width / 4;
	int buttonHeight = height / 10;
	int buttonSpacing = buttonHeight / 4;
	int topMargin = 150;
	int leftMargin = width / 2 - buttonWidth / 2;
	int buttonWidthSmall = (buttonWidth - buttonSpacing) / 2;
	backButton.setX(leftMargin);
	backButton.setY(height - buttonSpacing - buttonHeight);
	backButton.setWidth(buttonWidth);
	backButton.setHeight(buttonHeight);

	if (section == Section.MAIN) {
	    videoButton.setX(leftMargin);
	    videoButton.setY(topMargin);
	    videoButton.setWidth(buttonWidthSmall);
	    videoButton.setHeight(buttonHeight);
	    languageButton.setX(leftMargin + buttonWidthSmall + buttonSpacing);
	    languageButton.setY(topMargin);
	    languageButton.setWidth(buttonWidthSmall);
	    languageButton.setHeight(buttonHeight);
	    gameButton.setX(leftMargin);
	    gameButton.setY(topMargin + buttonHeight + buttonSpacing);
	    gameButton.setWidth(buttonWidthSmall);
	    gameButton.setHeight(buttonHeight);
	} else if (section == Section.VIDEO) {
	    videoVsyncButton.setX(leftMargin);
	    videoVsyncButton.setY(topMargin);
	    videoVsyncButton.setWidth(buttonWidthSmall);
	    videoVsyncButton.setHeight(buttonHeight);
	    videoFullscreenButton.setX(leftMargin + buttonWidthSmall + buttonSpacing);
	    videoFullscreenButton.setY(topMargin);
	    videoFullscreenButton.setWidth(buttonWidthSmall);
	    videoFullscreenButton.setHeight(buttonHeight);
	} else if (section == Section.LANGUAGE) {
	    int i = 0;
	    for (GuiButton b : languageButtons) {
		b.setX(leftMargin);
		b.setY(topMargin + (i++) * (buttonHeight + buttonSpacing));
		b.setWidth(buttonWidth);
		b.setHeight(buttonHeight);
	    }
	} else if (section == Section.GAME) {
	    gameArrowButton.setX(leftMargin);
	    gameArrowButton.setY(topMargin);
	    gameArrowButton.setWidth(buttonWidthSmall);
	    gameArrowButton.setHeight(buttonHeight);
	} else if (section == Section.ARROWS) {
	    int i = 0;
	    for (GuiButton b : arrowButtons) {
		b.setX(leftMargin + (i % 4) * (buttonHeight + buttonSpacing));
		b.setY(topMargin + (i / 4) * (buttonHeight + buttonSpacing));
		b.setWidth(buttonHeight);
		b.setHeight(buttonHeight);
		i++;
	    }
	}
	super.render(g2d, width, height);
    }

    @Override
    public void tick() {
	super.tick();
	videoFullscreenButton.setText("gui.options.video.fullscreen." + (Client.instance.preferences.video.fullscreen ? "on" : "off"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getID() == ActionEvent.ACTION_PERFORMED) {
	    GuiButton button = (GuiButton) e.getSource();
	    if (button == backButton) {
		if (section != Section.MAIN)
		    setSection(Section.MAIN);
		else {
		    Client.instance.setGuiScreen(last);
		    try {
			Client.instance.preferences.write(new FileWriter("preferences.json"));
		    } catch (IOException e1) {
			System.err.println("Unable to save preferences");
		    }
		}
	    } else if (button == videoButton) {
		setSection(Section.VIDEO);
	    } else if (button == videoVsyncButton) {
		Client.instance.preferences.video.vsync = !Client.instance.preferences.video.vsync;
		videoVsyncButton.setText("gui.options.video.vsync." + (Client.instance.preferences.video.vsync ? "on" : "off"));
	    } else if (button == videoFullscreenButton) {
		Client.instance.preferences.video.fullscreen = !Client.instance.preferences.video.fullscreen;
		videoFullscreenButton.setText("gui.options.video.fullscreen." + (Client.instance.preferences.video.fullscreen ? "on" : "off"));
		Client.instance.setFullscreen(Client.instance.preferences.video.fullscreen);
	    } else if (button == languageButton) {
		setSection(Section.LANGUAGE);
	    } else if (languageButtons.contains(button)) {
		String lang = (String)button.getTextData()[0];
		Client.instance.preferences.language = lang;
		Client.instance.translation.reload("en_US");
		Client.instance.translation.load(lang);
		this.rebuild();
		if(last != null)
		    last.rebuild();
	    } else if (button == gameButton) {
		setSection(Section.GAME);
	    } else if (button == gameArrowButton) {
		setSection(Section.ARROWS);
	    } else if (arrowButtons.contains(button)) {
		Client.instance.preferences.game.arrow = button.getId();
		setSection(Section.ARROWS);
	    }
	}
    }

    private void setSection(Section s) {
	section = s;
	controlList.clear();
	switch (s) {
	case MAIN:
	    controlList.add(videoButton);
	    controlList.add(languageButton);
	    controlList.add(gameButton);
	    break;
	case VIDEO:
	    controlList.add(videoVsyncButton);
	    controlList.add(videoFullscreenButton);
	    break;
	case LANGUAGE:
	    languageButtons.clear();
	    Map<String,String> languages = new Gson().fromJson(new InputStreamReader(GuiOptions.class.getResourceAsStream("/lang/languages.json")), new TypeToken<Map<String, String>>() {
	    }.getType());
	    int i = 0x100;
	    for (Map.Entry<String,String> e : languages.entrySet()) {
		GuiButton button = new GuiButton(this, i, 0, 0, e.getValue()).setTranslate(false).setTextData(e.getKey());
		languageButtons.add(button);
		if (e.getKey().equals(Client.instance.preferences.language))
		    button.setEnabled(false);
	    }
	    controlList.addAll(languageButtons);
	    break;
	case GAME:
	    controlList.add(gameArrowButton);
	    break;
	case ARROWS:
	    arrowButtons.clear();
	    int arrows;
	    try {
		arrows = Utils.getResourceListing(GuiOptions.class.getClassLoader(), "/textures/arrow_").size();
	    } catch (Exception e1) {
		e1.printStackTrace();
		arrows = 0;
	    }
	    for (int j = 0; j < arrows; j++) {
		GuiButton button = new GuiButton(this, j, 0, 0, null);
		button.setTexture(StandardTexture.get("arrow_" + j));
		arrowButtons.add(button);
		if (j == Client.instance.preferences.game.arrow)
		    button.setEnabled(false);
	    }
	    controlList.addAll(arrowButtons);
	    break;
	}
	controlList.add(backButton);
    }

    private enum Section {
	MAIN, VIDEO, LANGUAGE, GAME, ARROWS;
    }
}
