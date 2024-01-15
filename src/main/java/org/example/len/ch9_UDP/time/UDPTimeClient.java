package org.example.len.ch9_UDP.time;

import java.io.IOException;
import java.net.*;

class UDPTimeClient {

    public static void main(String[] args) throws UnknownHostException {
        String ip = "localhost";
        int port = 123;

        InetAddress inetAddress = InetAddress.getByName(ip);

        DatagramSocket dsock = null;
        try {
            dsock = new DatagramSocket();
            // 전송
            DatagramPacket sendPacket = new DatagramPacket("".getBytes(), "".getBytes().length, inetAddress, port);
            dsock.send(sendPacket);

            byte[] buffer = new byte[200];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            dsock.receive(receivePacket);;

            // 받은 결과 출력
            String msg = new String(receivePacket.getData());
            System.out.println("서버로부터 전달받은 시간:" + msg.trim());
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
