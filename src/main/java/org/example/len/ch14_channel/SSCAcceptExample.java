package org.example.len.ch14_channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Objects;

class SSCAcceptExample {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(8080));
        ssc.configureBlocking(false);

        while (true) {
            System.out.println("커넥션 연결 대기중...");

            SocketChannel sc = ssc.accept();
            if (Objects.isNull(sc)) Thread.sleep(1000);
            else {
                System.out.println(sc.socket().getRemoteSocketAddress() + " 가 연결 시도");
            }
        }
    }
}
