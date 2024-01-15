package org.example.len.ch16_advenced.pool.selector;

import java.util.ArrayList;
import java.util.List;

public abstract class SelectorPoolAdaptor implements SelectorPoolIF {
	
	protected int size = 2;
	
	private int roundRobinIndex = 0;
	
	private final Object monitor = new Object();
	protected final List pool = new ArrayList();
	
	protected abstract Thread createHandler(int index);
	public abstract void startAll();
	public abstract void stopAll();

	/* (non-Javadoc)
	 * @see net.daum.javacafe.pool.SelectorPoolIF#pop()
	 */
	public Thread get() {
		synchronized (monitor) {
			return (Thread) pool.get( roundRobinIndex++ % size );
		}
	}

	/* (non-Javadoc)
	 * @see net.daum.javacafe.pool.SelectorPoolIF#push(net.daum.javacafe.pool.handler.SelectorHandlerIF)
	 */
	public void put(Thread handler) {
		synchronized (monitor) {
			if (handler != null) {
				pool.add(handler);				
			}
			monitor.notify();
		}
	}

	/* (non-Javadoc)
	 * @see net.daum.javacafe.pool.SelectorPoolIF#size()
	 */
	public int size() {
		synchronized (monitor) {
			return pool.size();
		}
	}

	/* (non-Javadoc)
	 * @see net.daum.javacafe.pool.SelectorPoolIF#isEmpty()
	 */
	public boolean isEmpty() {
		synchronized (monitor) {
			return pool.isEmpty();
		}
	}

}
