package org.example.len.ch9_UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

class UDPEchoServer {

    public static void main(String[] args) {

        final int port = 11011;
        DatagramSocket dsock = null;
        try {
            System.out.println("접속 대기");
            dsock = new DatagramSocket(port);

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                dsock.receive(receivePacket);
                String msg = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("전송 받은 문자열: " + msg);
                if (msg.equals("quit")) break;


                //전송
                DatagramPacket sendPacket = new DatagramPacket(receivePacket.getData(),
                        receivePacket.getData().length, receivePacket.getAddress(), receivePacket.getPort());
                dsock.send(sendPacket);
            }
            System.out.println("UPDEchoServer 종료");

        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
