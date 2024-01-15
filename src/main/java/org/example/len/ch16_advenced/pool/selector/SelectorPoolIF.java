package org.example.len.ch16_advenced.pool.selector;

public interface SelectorPoolIF {
	
	Thread get();
	void put(Thread handler);
	int size();
	boolean isEmpty();
	void startAll();
	void stopAll();
	
}
