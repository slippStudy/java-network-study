package org.example.len.ch13_buffer;

import java.nio.ByteBuffer;

class OtherDataTest {

    public static void main(String[] args) {
        //크기가 10인 ByteBuffer 생성
        ByteBuffer buf = ByteBuffer.allocate(10);
        print(buf);
        buf.putInt(100);
        print(buf);
    }

    private static void print(ByteBuffer buf) {
        System.out.println("position=" + buf.position() + ", limit=" + buf.limit() + ", capacity=" + buf.capacity());
    }
}
