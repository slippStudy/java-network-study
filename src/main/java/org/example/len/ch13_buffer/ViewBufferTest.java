package org.example.len.ch13_buffer;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

class ViewBufferTest {

    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(10);
        IntBuffer ib = buf.asIntBuffer();
        System.out.println("position=" + ib.position() + ", limit=" + ib.limit() + ", capacity=" + ib.capacity());

        // 뷰 버퍼에 데이터 씀
        ib.put(1012324).put(2048);
        // 숫자가 커지면 어떻게 쓰는거지?

        // 뷰버퍼에 데이터 쓴다.
        System.out.println("indext_0=" + ib.get(0) + ", indext_1=" + ib.get(1));

        // 원본 버퍼도 변경되었는지 확인하기 위해 출력
        while (buf.hasRemaining()) {
            System.out.print(buf.get() + " ");
        }
    }
}
