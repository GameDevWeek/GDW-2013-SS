package de.fhtrier.gdw.commons.netcode.examples.pingpong;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.fhtrier.gdw.commons.netcode.NetConnection;
import de.fhtrier.gdw.commons.netcode.NetReception;

/**
 * 
 * @author Lusito
 */
public class ServerTest {
    private NetReception reception;
    private List<NetConnection> connections = new ArrayList<>();

    private void sendPing(NetConnection connection, String text) {
        connection.send(new ChatDatagram(text));
    }

    private void handleDatagrams() {
        Iterator<NetConnection> it = connections.iterator();
        while (it.hasNext()) {
            NetConnection connection = it.next();
            if (!connection.isConnected()) {
                System.out.println("client disconnected");
                it.remove();
                continue;
            }
            connection.sendPendingDatagrams();

            ChatDatagram datagram = (ChatDatagram) connection.receive();
            if (datagram != null) {
                System.out.println(datagram.getText());
                sendPing(connection, "Pong");
            }
        }

    }

    private void getNewConnections() {
        while (reception.hasNewConnections()) {
            NetConnection connection = reception.getNextNewConnection();
            connection.setAccepted(true);
            connections.add(connection);
        }
    }

    private void run() {
        try {
            reception = new NetReception("localhost", 9090, 1,
                    new DatagramFactory());
            for (; reception.isRunning();) {
                getNewConnections();

                handleDatagrams();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerTest test = new ServerTest();
        test.run();
    }
}
