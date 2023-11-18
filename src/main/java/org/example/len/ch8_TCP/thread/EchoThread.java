package org.example.len.ch8_TCP.thread;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

class EchoThread extends Thread {
    private Socket sock;

    public EchoThread(Socket sock) {
        this.sock = sock;
    }

    @Override
    public void run() {
        try {
            InetAddress inetAddress = sock.getInetAddress();
            System.out.println(inetAddress.getHostAddress() + " 로 부터 접속");
            OutputStream out = sock.getOutputStream();
            InputStream in = sock.getInputStream();
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("클라이언트로부터 전송받은 문자열 : " + line);
                pw.println(line);
                pw.flush();
            }
            pw.close();
            br.close();
            sock.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }
}
