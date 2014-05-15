package de.gymdon.inf1315.game.client;

import java.awt.Graphics2D;
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
		// TODO Auto-generated method stub
		
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
