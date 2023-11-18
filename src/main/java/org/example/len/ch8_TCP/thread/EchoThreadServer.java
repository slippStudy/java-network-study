package org.example.len.ch8_TCP.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class EchoThreadServer {

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(10001);
        System.out.println("접속 기다림");

        while (true) {
            Socket socker = server.accept();
            EchoThread echoThread = new EchoThread(socker);
            echoThread.start();
        }
    }
}
