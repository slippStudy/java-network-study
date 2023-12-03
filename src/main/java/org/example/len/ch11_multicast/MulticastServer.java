package org.example.len.ch11_multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

class MulticastServer extends Thread {

    DatagramSocket socket = null;
    DatagramPacket packet = null;
    InetAddress channel = null;
    int port = 20001;
    String address = "239.0.0.1"; //239 로 시작하는 것이 멀티캐스트 그룹 IP
    boolean onAir = true;

    public MulticastServer() throws SocketException {
        super("멀티캐스트 방송국");
        this.socket = new DatagramSocket(port);
    }

    @Override
    public void run() {
        String msg = "멀티캐스트 방속이 잘 들리시나요?";
        byte[] b = new byte[100];
        while (onAir) {
            try {
                b = msg.getBytes();
                channel = InetAddress.getByName(address);
                packet = new DatagramPacket(b, b.length, channel, port);
                socket.send(packet);
                try {
                    sleep(500);
                    System.out.println("방송중");
                } catch (InterruptedException e) { }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        socket.close();
    }

    public static void main(String[] args) throws SocketException {
        new MulticastServer().start();
    }
}
