package org.example.len.ch14_channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.WritableByteChannel;

class SimpleChannelTest {

    public static void main(String[] args) throws IOException {
        ReadableByteChannel src = Channels.newChannel(System.in);
        WritableByteChannel des = Channels.newChannel(System.out);
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        while (src.read(buffer) != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                des.write(buffer);
            }
            buffer.clear();
        }

    }
}
