import java.util.ArrayList;
import java.util.List;

public class BlockingEventQueue implements Queue {
	private final Object acceptMonitor = new Object();
	private final Object readMonitor = new Object();

	private final List acceptQueue = new ArrayList();
	private final List readQueue = new ArrayList();

	private static BlockingEventQueue instance = new BlockingEventQueue();

	public static BlockingEventQueue getInstance() {
		if (instance == null) {
			synchronized (BlockingEventQueue.class) {
				instance = new BlockingEventQueue();
			}
		}
		return instance;
	}

	private BlockingEventQueue() {}

	public Job pop(int eventType) {
		switch (eventType) {
			case NIOEvent.ACCEPT_EVENT : return getAcceptJob();
			case NIOEvent.READ_EVENT : return getReadJob();
			default: throw new IllegalArgumentException("Illegal EventType..");
		}
	}

	private Job getAcceptJob() { 
		synchronized (acceptMonitor) {
			if (acceptQueue.isEmpty()) {
				try {
					acceptMonitor.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return (Job) acceptQueue.remove(0);
		}
	}

	private Job getReadJob() {
		synchronized (readMonitor) {
			if (readQueue.isEmpty()) {
				try {
					readMonitor.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return (Job) readQueue.remove(0);
		}
	}

	public void push(Job job) {
		if (job != null) {
			int eventType = job.getEventType();
			switch (eventType) {
				case NIOEvent.ACCEPT_EVENT : putAcceptJob(job); break;
				case NIOEvent.READ_EVENT : putReadJob(job); break;
				default: throw new IllegalArgumentException("Illegal EventType..");
			}
		}
	}

	private void putAcceptJob(Job job) {
		synchronized (acceptMonitor) {
			acceptQueue.add(job);
			acceptMonitor.notify();
		}
	}

	private void putReadJob(Job job) {
		synchronized (readMonitor) {
			readQueue.add(job);
			readMonitor.notify();
		}
	}

}
