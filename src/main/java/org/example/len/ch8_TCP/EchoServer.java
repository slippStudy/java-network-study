package org.example.len.ch8_TCP;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

class EchoServer {

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(10001);
        System.out.println("접속 기다림");
        Socket sock = server.accept();

        InetAddress inetAddress = sock.getInetAddress();
        System.out.println(inetAddress.getHostAddress() + " 로부터 접속했습니다.");

        OutputStream out = sock.getOutputStream();
        InputStream in = sock.getInputStream();

        PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String line;
        while ((line = br.readLine()) != null) {
            System.out.println("클라이언트 전송받은 문자열 : " + line);
            pw.println(line);
            pw.flush();
        }
        pw.close();
        br.close();
        sock.close();
    }
}
