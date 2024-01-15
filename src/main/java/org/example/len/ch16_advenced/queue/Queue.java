package org.example.len.ch16_advenced.queue;


import org.example.len.ch16_advenced.event.Job;

public interface Queue {
	
	public Job pop(int eventType);
	public void push(Job job);

}
