package org.example.len.ch16_advenced.pool.selector;

import org.example.len.ch16_advenced.pool.selector.handler.AcceptHandler;
import org.example.len.ch16_advenced.queue.Queue;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.Iterator;

public class AcceptSelectorPool extends SelectorPoolAdaptor {
	
	private int port = 9090;
	private Queue queue = null;
	
	public AcceptSelectorPool(Queue queue) {
		this(queue, 1, 9090);
	}
	
	public AcceptSelectorPool(Queue queue, int size, int port) {
		super.size = size;		
		this.queue = queue;
		this.port = port;
		init();
	}
	
	private void init() {
		for (int i = 0; i < size; i++) {
			pool.add(createHandler(i));
		}
	}
	
	/* (non-Javadoc)
	 * @see net.daum.javacafe.pool.SelectorPoolAdaptor#createHandler(int)
	 */
	protected Thread createHandler(int index) {
		Selector selector = null;
		try {
			selector = Selector.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread handler = new AcceptHandler(queue, selector, port, index);
		
		return handler;
	}

	/* (non-Javadoc)
	 * @see net.daum.javacafe.pool.selector.SelectorPoolIF#startAll()
	 */
	public void startAll() {
		Iterator iter = pool.iterator();
		while (iter.hasNext()) {
			Thread handler = (Thread) iter.next();
			handler.start();
		}
	}

	/* (non-Javadoc)
	 * @see net.daum.javacafe.pool.selector.SelectorPoolIF#stopAll()
	 */
	public void stopAll() {
		Iterator iter = pool.iterator();
		while (iter.hasNext()) {
			Thread handler = (Thread) iter.next();
			handler.interrupt();
			handler = null;
		}
	}

}
