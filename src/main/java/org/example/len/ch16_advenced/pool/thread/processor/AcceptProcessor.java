package org.example.len.ch16_advenced.pool.thread.processor;

import org.example.len.ch16_advenced.event.Job;
import org.example.len.ch16_advenced.event.NIOEvent;
import org.example.len.ch16_advenced.pool.PoolManager;
import org.example.len.ch16_advenced.pool.selector.handler.HandlerAdaptor;
import org.example.len.ch16_advenced.queue.Queue;

import java.nio.channels.SocketChannel;

public class AcceptProcessor extends Thread {
	
	private Queue queue = null;
	
	public AcceptProcessor(Queue queue) {
		this.queue = queue;
	}
	
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				Job job = queue.pop(NIOEvent.ACCEPT_EVENT);
				SocketChannel sc = (SocketChannel) job.getSession().get("SocketChannel");
				sc.configureBlocking(false);
				HandlerAdaptor handler = (HandlerAdaptor) PoolManager.getRequestSelectorPool().get();
				handler.addClient(sc);
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

}
