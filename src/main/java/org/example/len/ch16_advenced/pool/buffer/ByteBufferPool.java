package org.example.len.ch16_advenced.pool.buffer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class ByteBufferPool implements ByteBufferPoolIF {

	private static final int MEMORY_BLOCKSIZE = 1024;
	private static final int FILE_BLOCKSIZE = 2048;
	
	private final List memoryQueue = new ArrayList();
	private final List fileQueue = new ArrayList();
	
	private boolean isWait = false;
	
	public ByteBufferPool(int memorySize, int fileSize, File file) throws IOException {
		if (memorySize > 0) 
			initMemoryBuffer(memorySize);

		if (fileSize > 0)
			initFileBuffer(fileSize, file);
	}
	
	private void initMemoryBuffer(int size) {
		int bufferCount = size / MEMORY_BLOCKSIZE;
		size = bufferCount * MEMORY_BLOCKSIZE;
		ByteBuffer directBuf = ByteBuffer.allocateDirect(size);
		divideBuffer(directBuf, MEMORY_BLOCKSIZE, memoryQueue);
	}
	
	private void initFileBuffer(int size, File f) throws IOException {
		int bufferCount = size / FILE_BLOCKSIZE;
		size = bufferCount * FILE_BLOCKSIZE;
		RandomAccessFile file = new RandomAccessFile(f, "rw");
		try {
			file.setLength(size);
			ByteBuffer fileBuffer = file.getChannel().map(FileChannel.MapMode.READ_WRITE, 0L, size);
			divideBuffer(fileBuffer, FILE_BLOCKSIZE, fileQueue);
		} finally {
			file.close();
		}
	}

	private void divideBuffer(ByteBuffer buf, int blockSize, List list) {
		int bufferCount = buf.capacity() / blockSize;
		int position = 0;
		for (int i = 0; i < bufferCount; i++) {
			int max = position + blockSize;
			buf.limit(max);
			list.add(buf.slice());
			position = max;
			buf.position(position);
		}
	}
	
	public ByteBuffer getMemoryBuffer() {
		return getBuffer(memoryQueue, fileQueue);
	}

	public ByteBuffer getFileBuffer() {
		return getBuffer(fileQueue, memoryQueue);
	}

	private ByteBuffer getBuffer(List firstQueue, List secondQueue) {
		ByteBuffer buffer = getBuffer(firstQueue, false);
		if (buffer == null) {
			buffer = getBuffer(secondQueue, false);
			if (buffer == null) {
				if (isWait)
					buffer = getBuffer(firstQueue, true);
				else
					buffer = ByteBuffer.allocate(MEMORY_BLOCKSIZE);
			}
		}
		return buffer;
	}

	private ByteBuffer getBuffer(List queue, boolean wait) {
		synchronized (queue) {
			if (queue.isEmpty()) {
				if (wait) {
					try {
						queue.wait();
					} catch (InterruptedException e) {
						return null;
					}
				} else {
					return null;
				}
			}
			return (ByteBuffer) queue.remove(0);
		}
	}

	public void putBuffer(ByteBuffer buffer) {
		if (buffer.isDirect()) {
			switch (buffer.capacity()) {
				case MEMORY_BLOCKSIZE :
					putBuffer(buffer, memoryQueue);
					break;
				case FILE_BLOCKSIZE :
					putBuffer(buffer, fileQueue);
					break;
			}
		}
	}
	
	private void putBuffer(ByteBuffer buffer, List queue) {
		buffer.clear();
		synchronized (queue) {
			queue.add(buffer);
			queue.notify();
		}
	}
	
	public synchronized void setWait(boolean wait) { this.isWait = wait; }	
	public synchronized boolean isWait() { return isWait; }
}
