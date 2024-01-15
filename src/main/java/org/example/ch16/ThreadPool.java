import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ThreadPool implements ThreadPoolIF {
	private int max = 10;
	private int min = 2;
	private int current = 0;

	private final Object monitor = new Object();
	private final List pool = new ArrayList();

	private Queue queue = null;
	private int type;

	public ThreadPool(Queue queue, int type) {
		this(queue, type, 2, 10);
	}

	public ThreadPool(Queue queue, int type, int min, int max) {
		this.queue =  queue;
		this.type = type;
		this.min = min;
		this.max = max;
		init();
	}

	private void init() {
		for (int i = 0; i < min; i++) {
			pool.add(createThread());
		}
	}

	private synchronized Thread createThread() {
		Thread thread = null;
		try { 
			if (type == NIOEvent.ACCEPT_EVENT)
				thread = new AcceptProcessor(queue);
			if (type == NIOEvent.READ_EVENT)
				thread = new ReadWriteProcessor(queue);				
		} catch (Exception e) {
			e.printStackTrace();
		}
		current++;
		return thread;
	}

	public void startAll() {
		synchronized (monitor) {
			Iterator iter = pool.iterator();
			while (iter.hasNext()) {
				Thread thread = (Thread) iter.next();
				thread.start();
			}
		}
	}

	public void stopAll() {
		synchronized (monitor) {
			Iterator iter = pool.iterator();
			while (iter.hasNext()) {
				Thread thread = (Thread) iter.next();
				thread.interrupt();
				thread = null;
			}
			pool.clear();
		}
	}

	public void addThread() {
		synchronized (monitor) {
			if (current< max) {
				Thread t = createThread();
				t.start();
				pool.add(t);
			}
		}
	}

	public void removeThread() {
		synchronized (monitor) {
			if (current > min) {
				Thread t = (Thread) pool.remove(0);
				t.interrupt();
				t = null;
			}
		}
	}
}
