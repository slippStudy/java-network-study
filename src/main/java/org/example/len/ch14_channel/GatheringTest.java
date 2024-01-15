package org.example.len.ch14_channel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;

class GatheringTest {

    public static void main(String[] args) throws IOException {
        FileOutputStream fo = new FileOutputStream("/Users/len/study/java-network-study/data-char123.txt");
        GatheringByteChannel channel = fo.getChannel();
        FileChannel channel1 = fo.getChannel();;
        ByteBuffer header = ByteBuffer.allocateDirect(20);
        ByteBuffer body = ByteBuffer.allocateDirect(40);

        ByteBuffer[] buffers = {header, body};

        header.put("Hello".getBytes());
        body.put("World".getBytes());

        header.flip();
        body.flip();

        channel.write(buffers);
        channel.close();;
    }
}
