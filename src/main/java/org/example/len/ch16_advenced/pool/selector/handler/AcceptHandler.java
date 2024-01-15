package org.example.len.ch16_advenced.pool.selector.handler;

import org.example.len.ch16_advenced.event.Job;
import org.example.len.ch16_advenced.event.NIOEvent;
import org.example.len.ch16_advenced.queue.Queue;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AcceptHandler extends Thread {
	
	private Queue queue = null;
	private Selector selector = null;
	private int port = 9090;
	private String name = "AcceptHandler-";
	
	public AcceptHandler(Queue queue, Selector selector, int port, int index) {
		this.queue = queue;
		this.selector = selector;
		this.port = port;
		setName(name + index);
		init();
	}
	
	private void init() {
		try {
			ServerSocketChannel ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);
			
			InetSocketAddress address = new InetSocketAddress("localhost", port);
			ssc.socket().bind(address);
			
			System.out.println("@AcceptHandler(" + getName() + ") Bound to " + address);
			
			ssc.register(this.selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				int keysReady = selector.select();
				acceptPendingConnections();
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	private void acceptPendingConnections() throws Exception {
		Iterator iter = selector.selectedKeys().iterator();	
		while (iter.hasNext()) {
			SelectionKey key = (SelectionKey) iter.next();
			iter.remove();
			
			ServerSocketChannel readyChannel = (ServerSocketChannel) key.channel();
			SocketChannel sc = readyChannel.accept();
			
			System.out.println("@AcceptHandler(" + getName() + ") connection accepted from " + sc.socket().getInetAddress());
			
			pushMyJob(sc);
		}
	}
	
	private void pushMyJob(SocketChannel sc) {
		Map session = new HashMap();
		session.put("SocketChannel", sc);
		Job job = new Job(NIOEvent.ACCEPT_EVENT, session);
		queue.push(job);
	}

}
