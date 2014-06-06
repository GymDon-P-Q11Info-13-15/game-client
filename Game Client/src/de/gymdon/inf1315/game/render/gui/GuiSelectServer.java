package de.gymdon.inf1315.game.render.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import de.gymdon.inf1315.game.client.Client;
import de.gymdon.inf1315.game.client.ServerListEntry;

public class GuiSelectServer extends GuiScreen {

    private GuiScreen last;
    private GuiButton backButton = new GuiButton(this, 0, 300, 550, "gui.back");
    private GuiScrollList serverList;
    private List<ServerListEntry> servers = ServerListEntry.DEFAULT;
    private ListAdapter<ServerListEntry> adapter;
    
    public GuiSelectServer() {
	controlList.add(backButton);
	try {
	    reload();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    public GuiSelectServer(GuiScreen last) {
	this();
	this.last = last;
    }
    
    @Override
    public void render(Graphics2D g2d, int width, int height) {
	drawBackground(g2d, width, height);
        
        Font f = Client.instance.translation.font.deriveFont(Font.BOLD, 80F);
        g2d.setFont(f);
        String title = Client.instance.translation.translate("gui.server.select");
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
	//int buttonWidthSmall = (buttonWidth - buttonSpacing)/2;
	backButton.setX(leftMargin);
	backButton.setY(height - buttonSpacing - buttonHeight);
	backButton.setWidth(buttonWidth);
	backButton.setHeight(buttonHeight);
        
	serverList.setX(leftMargin);
	serverList.setY(topMargin);
	serverList.setWidth(buttonWidth);
	serverList.setHeight(height - buttonSpacing - buttonHeight - buttonSpacing - topMargin);
        
        super.render(g2d, width, height);
    }
    
    private void reload() throws IOException {
	File f = new File("servers.json");
	if(!f.exists()) {
	    writeServers();
	    System.out.println(Client.instance.translation.translate("file.created", "servers.json"));
	}
	servers = new Gson().fromJson(new InputStreamReader(new FileInputStream("servers.json"), Charset.forName("UTF-8")), new TypeToken<List<ServerListEntry>>(){}.getType());
	if(!servers.containsAll(ServerListEntry.DEFAULT)) {
	    servers.addAll(0, ServerListEntry.DEFAULT);
	    writeServers();
	}
	adapter = new ListAdapter<ServerListEntry>(servers) {

	    @Override
	    public int getHeight(ServerListEntry element, GuiScrollList parent) {
		return 100;
	    }

	    @Override
	    public int getWidth(ServerListEntry element, GuiScrollList parent) {
		return parent.getWidth();
	    }

	    @Override
	    public Gui get(ServerListEntry element, GuiScrollList parent) {
		return element.getGui();
	    }
	};
	serverList = new GuiScrollList(this, adapter, 1, 0, 0);
	for(ServerListEntry e : servers)
	    e.ping();
	controlList.add(serverList);
    }
    
    private void writeServers() throws IOException {
	if(servers == null)
	    servers = ServerListEntry.DEFAULT;
	Writer writer = new OutputStreamWriter(new FileOutputStream("servers.json"), Charset.forName("UTF-8"));
	new GsonBuilder().setPrettyPrinting().create().toJson(servers, new TypeToken<List<ServerListEntry>>(){}.getType(), writer);
	writer.close();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getID() == ActionEvent.ACTION_PERFORMED) {
	    GuiButton button = (GuiButton)e.getSource();
	    if(button == backButton) {
		Client.instance.setGuiScreen(last);
	    }
	}
    }

    @Override
    public void keyPressed(KeyEvent e) {
	int key = e.getKeyCode();
	if (key == KeyEvent.VK_ESCAPE)
	    actionPerformed(new ActionEvent(backButton, ActionEvent.ACTION_PERFORMED, null));
    }

    @Override
    public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
    }

}
