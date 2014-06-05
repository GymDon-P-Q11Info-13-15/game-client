package de.gymdon.inf1315.game.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import de.gymdon.inf1315.game.packet.Packet;
import de.gymdon.inf1315.game.packet.PacketHello;
import de.gymdon.inf1315.game.packet.PacketListener;
import de.gymdon.inf1315.game.packet.Remote;
import de.gymdon.inf1315.game.render.gui.Gui;

public class ServerListEntry {
    public static final List<ServerListEntry> DEFAULT = new ArrayList<ServerListEntry>();
    public String name;
    public String ip;
    public boolean removable = true;
    private int ping = -1;
    private boolean pinging = false;

    public Gui getGui() {
	return new Gui() {

	    @Override
	    public void render(Graphics2D g2d, int width, int height) {
		LinearGradientPaint gradient = new LinearGradientPaint(0, 0, 0, 100, new float[]{0,1}, new Color[]{new Color(0x666666),new Color(0x444444)});
		g2d.setPaint(gradient);
		g2d.fillRect(0, 0, width, 100);
		g2d.setColor(new Color(0x666666));
		g2d.fillRect(0, 100, width, height - 100);
		g2d.setColor(Color.WHITE);
		g2d.setFont(Client.instance.translation.font.deriveFont(Font.BOLD, 35F));
		g2d.drawString(name, 50, 50);
		g2d.setFont(Client.instance.translation.font.deriveFont(Font.BOLD, 18F));
		g2d.setColor(Color.GRAY);
		g2d.drawString(ip, 50, 75);
		if(ping >= 0) {
		    if(ping == 0)
			g2d.setColor(Color.RED.darker());
		    g2d.drawString(ping == 0 ? Client.instance.translation.translate("protocol.timeout") : ping + "ms", 50, 95);
		}
	    }
	    
	};
    }
    
    public InetSocketAddress getAddress() {
	if(ip.contains(":"))
	    return new InetSocketAddress(ip.substring(0, ip.indexOf(':')), Integer.parseInt(name.substring(ip.indexOf(':') + 1)));
	return new InetSocketAddress(ip, 22422);
    }
    
    public void ping() {
	new Thread() {
	    public void run() {
		try {
		    pinging = true;
		    ping = -1;
		    Socket s = new Socket();
		    s.connect(getAddress(), 2000);
		    Server server = new Server(s);
		    Client.instance.remotes.add(server);
		    final long start = System.currentTimeMillis();
		    server.addPacketListener(new PacketListener() {
		        
		        @Override
		        public void handlePacket(Remote r, Packet p, boolean in) {
		    		if(in) {
		    		    if(p instanceof PacketHello) {
		    			r.leave("");
		    			ping = (int) (System.currentTimeMillis() - start);
		    		    }
		    		}
		        }
		    });
		    PacketHello hello = new PacketHello(server);
		    hello.serverHello = false;
		    hello.ping = true;
		    hello.send();
		    try {
			Thread.sleep(2000);
		    } catch (InterruptedException e) {
		    }
		    if(pinging && ping < 0) {
			ping = 0;
			pinging = false;
			System.err.println("[ServerList] " + name + "(" + ip + "): " + Client.instance.translation.translate("protocol.timeout"));
			server.leave("");
		    }
		    if(!server.left())
			server.leave("");
		} catch (IOException e) {
		    System.err.println("[ServerList] " + name + "(" + ip + "): " + e.getMessage());
		    ping = 0;
		    pinging = false;
		}
		
	    }
	}.start();
    }
    
    public int getPing() {
	return ping;
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
