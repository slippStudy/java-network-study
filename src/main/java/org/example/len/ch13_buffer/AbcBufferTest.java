package org.example.len.ch13_buffer;

import java.nio.ByteBuffer;

class AbcBufferTest {

    public static void main(String[] args) {
        ByteBuffer aBuf = ByteBuffer.allocate(10);
        ByteBuffer bBuf = ByteBuffer.allocate(5);

        aBuf.put(bBuf);


    }
}
