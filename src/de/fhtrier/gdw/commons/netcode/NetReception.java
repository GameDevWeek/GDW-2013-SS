package de.fhtrier.gdw.commons.netcode;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.fhtrier.gdw.commons.netcode.datagram.INetDatagramFactory;

/**
 * A reception waits for clients to connect
 * 
 * @author Lusito
 */
public class NetReception extends Thread {

	/** Set to true during shutdown */
	private boolean shutdown = false;
	/** The maximum number of clients able to connect */
	private int maxConnections;
	/** The accepting socket channel */
	private ServerSocketChannel channel;
	/**
	 * A list of all new connections established that have not yet been taken by
	 * the application
	 */
	private ConcurrentLinkedQueue<NetConnection> newConnections = new ConcurrentLinkedQueue<>();
	/** The full list of all running connections */
	private ConcurrentLinkedQueue<NetConnection> connections = new ConcurrentLinkedQueue<>();
	/** The factory used to create datagrams based on their type */
	private INetDatagramFactory datagramFactory;

	/**
	 * Create a reception on the specified ip/port accepting a maximum number of
	 * connections. This starts a thread that listens for new connections
	 * 
	 * @param ip
	 *            the hostname or ip to bind the reception to
	 * @param port
	 *            the listening port
	 * @param maxConnections
	 *            the maximum number of connections to accept
	 * @param datagramFactory
	 *            the factory used to create datagrams based on their type.
	 * @throws IOException
	 */
	public NetReception(String ip, int port, int maxConnections,
			INetDatagramFactory datagramFactory) throws IOException {
		this.datagramFactory = datagramFactory;
		this.maxConnections = maxConnections;

		channel = ServerSocketChannel.open();
		channel.bind(new InetSocketAddress(InetAddress.getByName(ip), port));

		start();
	}

	/**
	 * The main routing to accept new connections
	 */
	@Override
	public void run() {
		while (!shutdown) {
			try {
				SocketChannel ch = channel.accept();
				if (connections.size() < maxConnections) {
					NetConnection client = new NetConnection(this, ch,
							datagramFactory);
					connections.add(client);
					newConnections.add(client);
				} else {
					ch.close();
				}
			} catch (IOException e) {
				if (!shutdown) {
					// TODO: what to do here ?
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Shut down the reception
	 */
	public void shutdown() {
		try {
			shutdown = true;
			channel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Do not call this manually, this is a callback for NetConnection.
	 * 
	 * @param client
	 *            the client connection that disconnected
	 */
	public void onClientDisconnect(NetConnection client) {
		connections.remove(client);
	}

	/**
	 * @return true if new connections have been established
	 */
	public boolean hasNewConnections() {
		return !newConnections.isEmpty();
	}

	/**
	 * @return the next new connection available
	 */
	public NetConnection getNextNewConnection() {
		return newConnections.poll();
	}

	/**
	 * @return true if the reception is still running
	 */
	public boolean isRunning() {
		return !shutdown;
	}
}
