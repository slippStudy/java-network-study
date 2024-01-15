import java.nio.channels.SocketChannel;

public abstract class HandlerAdaptor extends Thread { 
	public abstract void run();
	public abstract void addClient(SocketChannel sc);
}
