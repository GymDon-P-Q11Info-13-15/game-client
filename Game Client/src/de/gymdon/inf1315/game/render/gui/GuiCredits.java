package de.gymdon.inf1315.game.render.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.Gson;

import de.gymdon.inf1315.game.client.Client;
import de.gymdon.inf1315.game.render.gui.GuiCredits.Credits.Person;

public class GuiCredits extends GuiScreen {

    private GuiScreen last;
    private GuiButton backButton = new GuiButton(this, 0, 300, 550, "gui.back");
    private Credits credits;
    
    public GuiCredits(GuiScreen last) {
	this.last = last;
	credits = new Gson().fromJson(new InputStreamReader(GuiCredits.class.getResourceAsStream("/credits.json")), Credits.class);
	controlList.add(backButton);
    }
    
    
    @Override
    public void render(Graphics2D g2d, int width, int height) {
	drawBackground(g2d, width, height);
	Font f = Client.instance.translation.font.deriveFont(Font.BOLD, 80F);
	g2d.setFont(f);
	String title = Client.instance.translation.translate("gui.credits");
	Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(title, g2d);
	int titleX = (int) (width / 2 - bounds.getCenterX());
	int titleY = (int) (80 + bounds.getMaxY());
	g2d.setColor(Color.WHITE);
	g2d.drawString(title, titleX, titleY);
	
	AffineTransform tx = g2d.getTransform();
	g2d.translate(0, titleY);
	
	int buttonWidth = width - width / 4;
	int buttonHeight = height / 10;
	int buttonSpacing = buttonHeight / 4;
	//int topMargin = 150 - titleY;
	int leftMargin = width / 2 - buttonWidth / 2;
	//int buttonWidthSmall = (buttonWidth - buttonSpacing) / 2;
	backButton.setX(leftMargin);
	backButton.setY(height - buttonSpacing - buttonHeight);
	backButton.setWidth(buttonWidth);
	backButton.setHeight(buttonHeight);
	
	int contribsTopMargin = 100;
	int contribsLeftMargin = width/2 - 150;
	int contribsLineHeight = 20;
	Font f1 = Client.instance.translation.font.deriveFont(Font.PLAIN, 14F);
	g2d.setFont(f1);
	int line = 0;
	g2d.drawString(Client.instance.translation.translate("gui.credits.contributors") + ":", contribsLeftMargin, line++ * contribsLineHeight + contribsTopMargin);
	for(List<Person> group : credits.contributers) {
	    for(Person person : group) {
		g2d.drawString(person.toString(), contribsLeftMargin, line * contribsLineHeight + contribsTopMargin);
		line++;
	    }
	    line++;
	}
	g2d.setTransform(tx);
        super.render(g2d, width, height);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == backButton)
	    Client.instance.setGuiScreen(last);
    }

    
    public static class Credits {
	public List<List<Person>> contributers;
	
	public static class Person {
	    public String name;
	    public String username;
	    public List<String> roles;
	    
	    @Override
	    public String toString() {
		StringBuilder sb = new StringBuilder(name);
		if(username != null)
		    sb.append(" aka. ").append(username);
		if(roles != null && roles.size() > 0) {
		    sb.append(" (");
		    boolean first = true;
		    for(String role : roles) {
			if(!first)
			    sb.append(", ");
			else
			    first = false;
			sb.append(Client.instance.translation.translate("gui.credits." + role));
		    }
		    sb.append(')');
		}
		return sb.toString();
	    }
	}
    }
}
