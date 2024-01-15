package org.example.len.ch13_buffer;

import java.io.FileInputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileLock;

class BufferDemo {

    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(10);

        buf.put((byte) 1);
        buf.put((byte) 2);
        buf.put((byte) 3);
        buf.put((byte) 4);
        buf.put((byte) 5);
        buf.put((byte) 6);
        buf.put((byte) 7);
        buf.put((byte) 8);

        System.out.println();
        System.out.println("Position : " + buf.position() + ", Limit : " + buf.limit());

//        buf.flip(); // 0, 10

//        System.out.println(buf.get());
//        System.out.println(buf.get());
//        System.out.println(buf.get());

        System.out.println("Position : " + buf.position() + ", Limit : " + buf.limit());

        buf.compact();
//         ---,.---------------
//         ,-------------------.
        System.out.println("Position : " + buf.position() + ", Limit : " + buf.limit());
        buf.put((byte) 11);
        buf.put((byte) 12);
        buf.put((byte) 13);
        while (buf.hasRemaining()) {
            System.out.print(buf.get() + " ");
        }
    }
}
