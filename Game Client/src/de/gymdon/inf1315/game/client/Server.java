package de.gymdon.inf1315.game.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

import de.gymdon.inf1315.game.packet.Packet;
import de.gymdon.inf1315.game.packet.PacketHeartbeat;
import de.gymdon.inf1315.game.packet.Remote;

public class Server extends Remote {

    public Server(Socket s) throws IOException {
	super(s);
    }

    @Override
    public boolean isServer() {
	return true;
    }

    @Override
    public boolean isClient() {
	return false;
    }

    public void processPackets() {
	if(left())
	    return;
	try {
	    DataInputStream din = getInputStream();
	    if (din.available() >= 2) {
		short id = din.readShort();
		Packet p = Packet.newPacket(id, this);
		if (p != null)
		    p.handlePacket();
		else
		    kick("Invalid Packet");
	    }
	    if (getSocket().isClosed())
		leave("Socket closed");
	    long now = System.currentTimeMillis();
	    if(lastPacket == 0)
		lastPacket = System.currentTimeMillis();
	    if (now - this.getLastPacketTime() >= 2100) {
		PacketHeartbeat heartbeat = new PacketHeartbeat(this);
		heartbeat.response = false;
		byte[] bytes = new byte[43];
		new Random().nextBytes(bytes);
		heartbeat.payload = bytes;
		heartbeat.send();
	    }
	} catch (Exception e) {
	    leave(e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage());
	}
    }
}
