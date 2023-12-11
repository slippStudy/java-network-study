package org.example.len.ch13_buffer;

import java.nio.ByteBuffer;

class AbsoluteBufferTest {

    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(10);
        System.out.println("Init position :" + buf.position());
        System.out.println(", Init Limit :" + buf.limit());
        System.out.println(", Init Capacity :" + buf.capacity());

        buf.put(3, (byte) 3);
        buf.put(4, (byte) 4);
        buf.put(5, (byte) 5);
        // 위치를 지정해서 쓴 경우에도 position 이 변하지 않는다.
        System.out.println("Position : " + buf.position());

        //버퍼에 있는 데이터 출력. 역시 position이 변하지 않는다.
        System.out.println("Value : " + buf.get(3) + ", Position :" + buf.position());
        System.out.println("Value : " + buf.get(4) + ", Position :" + buf.position());
        System.out.println("Value : " + buf.get(5) + ", Position :" + buf.position());
        System.out.println("Value : " + buf.get(9) + ", Position :" + buf.position());

    }
}
