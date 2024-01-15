package org.example.len.ch8_TCP.web;

import java.net.ServerSocket;
import java.net.Socket;

class SimpleWebServer {

    public static void main(String[] args) {
        try {


            ServerSocket ss = new ServerSocket(80);

            while (true) {
                System.out.println("접속을 대기합니다.");
                Socket sock = ss.accept();
                System.out.println("새로운 스레드를 시작합니다.");
                HttpThread ht = new HttpThread(sock);
                ht.start();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
