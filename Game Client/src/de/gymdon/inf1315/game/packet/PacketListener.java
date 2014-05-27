package de.gymdon.inf1315.game.packet;

public interface PacketListener {
    public void handlePacket(Remote r, Packet p, boolean in);
}
