package org.example.len.ch13_buffer;

import java.nio.ByteBuffer;

class BulkReadTest {

    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(10);
        buf.put((byte) 0).put((byte) 1).put((byte) 2).put((byte) 3).put((byte) 4);
        buf.mark();
        buf.put((byte) 5).put((byte) 6).put((byte) 7).put((byte) 8).put((byte) 9);
        buf.reset();

        byte[] b = new byte[15];

        // 버퍼에서 얼마나 쓸 수 있는지를 계산한다.
        int size = buf.remaining();
        if (b.length < size) {
            size = b.length;
        }

        // 배열 또는 버퍼에서 이용할 수 있는 만큼만 이용한다.
        buf.get(b,0,size);
        System.out.println("Position :" + buf.position() + ", Limit : " + buf.limit());

        // byte[]에 담은 데이터를 처리하기 위한 메소드
        doSomething(b,size);
    }

    private static void doSomething(byte[] b, int size) {
        for (int i = 0; i < size; i++) {
            System.out.println("byte = " + b[i]);
        }
    }
}
