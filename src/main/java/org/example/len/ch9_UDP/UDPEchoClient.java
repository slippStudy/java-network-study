package org.example.len.ch9_UDP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

class UDPEchoClient {

    public static void main(String[] args) {
        String ip = "localhost";
        int port = 11011;
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }


        try {
            DatagramSocket dsock = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            dsock = new DatagramSocket();
            String line = null;
            while ((line = br.readLine()) != null) {

                // 전송
                DatagramPacket sendPacket =
                        new DatagramPacket(line.getBytes(), line.getBytes().length, inetAddress, port);
                dsock.send(sendPacket);

                if (line.equals("quit"))break;

                // 받기
                byte[] buffer = new byte[line.getBytes().length];
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                dsock.receive(receivePacket);

                //받은 결과 출력
                String msg = new String(receivePacket.getData());
                System.out.println("전송받은 문자열: " + msg);
            }

            System.out.println("Client 종료");
            dsock.close();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

        }
    }
}
