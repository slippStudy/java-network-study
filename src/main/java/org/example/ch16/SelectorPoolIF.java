public interface SelectorPoolIF {
	public Thread get();
	public void put(Thread handler);
	public int size();
	public boolean isEmpty();
	public void startAll();
	public void stopAll();
}
