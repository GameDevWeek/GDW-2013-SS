package de.fhtrier.gdw.commons.netcode.examples.game;

import de.fhtrier.gdw.commons.netcode.datagram.INetDatagram;
import de.fhtrier.gdw.commons.netcode.datagram.INetDatagramFactory;

public class DatagramFactory implements INetDatagramFactory {
    @Override
    public INetDatagram createDatagram(byte type, short id, short param1,
            short param2) {
        switch (type) {
        case PlayerDatagram.PLAYER_MESSAGE:
            return new PlayerDatagram(type, id, param1, param2);
        default:
            throw new IllegalArgumentException("type: " + type);
        }
    }
}
