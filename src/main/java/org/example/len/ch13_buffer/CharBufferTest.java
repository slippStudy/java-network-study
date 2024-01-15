package org.example.len.ch13_buffer;

import java.nio.CharBuffer;

class CharBufferTest {

    public static void main(String[] args) {
        CharBuffer c = CharBuffer.allocate(10);
        System.out.println("Position: " + c.position());

        c.put("Hello 월드");
        System.out.println("Position: " + c.position());

        CharBuffer buf = CharBuffer.wrap("hi ~ 지훈!");
        while (buf.hasRemaining()) {
            System.out.println(buf.get());
        }
    }
}
