package de.gymdon.inf1315.game.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.util.ArrayList;
import java.util.List;

import de.gymdon.inf1315.game.render.gui.Gui;

public class ServerListEntry {
    public static final List<ServerListEntry> DEFAULT = new ArrayList<ServerListEntry>();
    public String name;
    public String ip;
    public boolean removable = true;

    public Gui getGui() {
	return new Gui() {

	    @Override
	    public void render(Graphics2D g2d, int width, int height,
		    int scrollX, int scrollY) {
		LinearGradientPaint gradient = new LinearGradientPaint(0, 0, 0, 100, new float[]{0,1}, new Color[]{new Color(0x666666),new Color(0x444444)});
		g2d.setPaint(gradient);
		g2d.fillRect(0, 0, width, 100);
		g2d.setColor(new Color(0x666666));
		g2d.fillRect(0, 100, width, height - 100);
		g2d.setColor(Color.WHITE);
		g2d.setFont(Font.decode("Helvetica Bold 35"));
		g2d.drawString(name, 50, 50);
		g2d.setFont(Font.decode("Helvetica Bold 18"));
		g2d.setColor(Color.GRAY);
		g2d.drawString(ip, 50, 75);
	    }
	    
	};
    }
    
    @Override
    public boolean equals(Object obj) {
	return obj instanceof ServerListEntry && ((ServerListEntry)obj).ip.equals(ip);
    }
    
    static {
	ServerListEntry main = new ServerListEntry();
	main.name = "PVPcTutorials.de";
	main.ip = "pvpctutorials.de";
	main.removable = false;
	DEFAULT.add(main);
    }
}
