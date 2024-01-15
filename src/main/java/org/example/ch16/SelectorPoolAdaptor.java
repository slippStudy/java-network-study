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

	public Thread get() {
		synchronized (monitor) {
			return (Thread) pool.get(roundRobinIndex++ % size);
		}
	}

	public void put(Thread handler) {
		synchronized (monitor) {
			if (handler != null) {
				pool.add(handler);
			}
			monitor.notify();
		}
	}

	public int size() {
		synchronized (monitor) {
			return pool.size();
		}
	}

	public boolean isEmpty() {
		synchronized (monitor) {
			return pool.isEmpty();
		}
	}
}

