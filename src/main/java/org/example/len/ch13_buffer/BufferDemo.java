package org.example.len.ch13_buffer;

import java.nio.Buffer;
import java.nio.ByteBuffer;

class BufferDemo {

    public static void main(String[] args) {
        Buffer buffer = ByteBuffer.wrap(new byte[]{});
        buffer.position(1).mark().position(2).reset();


    }
}
