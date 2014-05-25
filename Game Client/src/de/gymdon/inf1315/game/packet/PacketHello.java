package de.gymdon.inf1315.game.packet;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketHello extends Packet {

    public static final short ID = 0;
    public boolean serverHello;
    public String serverName;
    public int protocolVersion;
    public boolean ping;

    public PacketHello(Remote r) {
	super(r);
    }

    @Override
    public void handlePacket() throws IOException {
	super.handlePacket();
	DataInput in = remote.getInputStream();
	serverHello = in.readBoolean();
	if (serverHello)
	    serverName = in.readUTF();
	protocolVersion = in.readInt();
	ping = in.readBoolean();
	remote.setPing(ping);
	if(protocolVersion != Packet.PROTOCOL_VERSION) {
	    remote.kick("protocol.version.incompatible", protocolVersion, Packet.PROTOCOL_VERSION);
	}
    }

    @Override
    public void send() throws IOException {
	DataOutputStream out = remote.getOutputStream();
	out.writeShort(ID);
	out.writeBoolean(serverHello);
	if (serverHello)
	    out.writeUTF(serverName);
	out.writeInt(Packet.PROTOCOL_VERSION);
	out.writeBoolean(ping);
	super.send();
    }
}
