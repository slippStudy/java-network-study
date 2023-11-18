package org.example.len.ch8_TCP.web;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

class ChatServer {

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(10001);
            System.out.println("접속을 기다립니다");

            ConcurrentHashMap<Object, Object> hm = new ConcurrentHashMap<>();
            while (true) {
                Socket sock = server.accept();
                ChatThread chatThread = new ChatThread(sock, hm);
                chatThread.start();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
