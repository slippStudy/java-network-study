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
				SocketChannel sc = (SocketChannel)job.getSession().get("SocketChannel");
				sc.configureBlocking(false);
				HandlerAdaptor handler = (HandlerAdaptor) PoolManager.getRequestSelectorPool().get();
				handler.addClient(sc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
