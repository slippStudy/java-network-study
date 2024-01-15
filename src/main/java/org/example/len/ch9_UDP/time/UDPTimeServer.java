package org.example.len.ch9_UDP.time;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

class UDPTimeServer {

    public static void main(String[] args) {
        int port = 123;

        try {
            DatagramSocket dsock = new DatagramSocket(port);
            System.out.println("접속 대기 상태");

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                dsock.receive(receivePacket);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
                String sDate = simpleDateFormat.format(new Date());

                System.out.println( receivePacket.getAddress().getHostAddress() + "시간 전송: " + sDate);

                DatagramPacket sendPacket = new DatagramPacket(sDate.getBytes(), sDate.getBytes().length, receivePacket.getAddress(), receivePacket.getPort());
                dsock.send(sendPacket);
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
