package org.example.len.ch11_multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

class MulticastClient {
     MulticastSocket receiver = null;
     DatagramPacket packet = null;
     InetAddress channel = null;
     int port = 20001;
     String address = "239.0.0.1";
     byte[] b = new byte[100];

    public MulticastClient() {
        try {
            receiver = new MulticastSocket(port);
            channel = InetAddress.getByName(address);
            packet = new DatagramPacket(b, b.length);
            receiver.joinGroup(channel);
            for (int i = 0; i < 3; i++) {
                receiver.receive(packet);
                String notice = new String(packet.getData());
                System.out.println(notice);
            }
            receiver.leaveGroup(channel);
            receiver.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new MulticastClient();
    }
}
