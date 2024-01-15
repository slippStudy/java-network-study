package org.example.len.ch16_advenced.server;

import org.example.len.ch16_advenced.pool.PoolManager;
import org.example.len.ch16_advenced.pool.buffer.ByteBufferPool;
import org.example.len.ch16_advenced.pool.buffer.ByteBufferPoolIF;
import org.example.len.ch16_advenced.pool.selector.AcceptSelectorPool;
import org.example.len.ch16_advenced.pool.selector.RequestSelectorPool;
import org.example.len.ch16_advenced.pool.selector.SelectorPoolIF;
import org.example.len.ch16_advenced.pool.thread.ThreadPool;
import org.example.len.ch16_advenced.pool.thread.ThreadPoolIF;
import org.example.len.ch16_advenced.queue.BlockingEventQueue;
import org.example.len.ch16_advenced.queue.Queue;

import java.io.File;
import java.io.IOException;


public class AdvancedChatServer {

	private Queue queue = null;
	private SelectorPoolIF acceptSelectorPool = null;
	private SelectorPoolIF requestSelectorPool = null;
	
	private ByteBufferPoolIF byteBufferPool = null;
	
	ThreadPoolIF acceptThreadPool = null;
	ThreadPoolIF readWriteThreadPool = null;
	
	public AdvancedChatServer() {
		try {
			initResource();
			startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initResource() throws IOException {
		// ByteBufferPool ����..
		File bufferFile = new File("C:/Buffer.tmp");
		if (!bufferFile.exists()) {
			bufferFile.createNewFile();
		}
		bufferFile.deleteOnExit();
		byteBufferPool = new ByteBufferPool(20*1024, 40*2048, bufferFile);
		
		// Queue ����..
		queue = BlockingEventQueue.getInstance();
		
		// PoolManager �� ByteBufferPool ���..
		PoolManager.registByteBufferPool(byteBufferPool);
		
		// ThreadPool ����..
		acceptThreadPool = new ThreadPool(
				queue, "net.daum.javacafe.pool.thread.processor.AcceptProcessor");
		readWriteThreadPool = new ThreadPool(
				queue, "net.daum.javacafe.pool.thread.processor.ReadWriteProcessor");

		// SelectorPool ����..
		acceptSelectorPool = new AcceptSelectorPool(queue);
		requestSelectorPool = new RequestSelectorPool(queue);
		
		// PoolManager �� SelectorPool ���..
		PoolManager.registAcceptSelectorPool(acceptSelectorPool);
		PoolManager.registRequestSelectorPool(requestSelectorPool);	
	}
	
	private void startServer() {
		acceptThreadPool.startAll();
		readWriteThreadPool.startAll();
		
		acceptSelectorPool.startAll();
		requestSelectorPool.startAll();
	}

	public static void main(String[] args) {
		AdvancedChatServer server = new AdvancedChatServer();
	}
	
}
