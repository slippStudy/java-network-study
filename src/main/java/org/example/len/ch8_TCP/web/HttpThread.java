package org.example.len.ch8_TCP.web;

import java.io.*;
import java.net.Socket;

class HttpThread extends Thread {

    private Socket sock = null;
    BufferedReader br = null;
    PrintWriter pw = null;

    public HttpThread(Socket sock) {
        this.sock = sock;
        try {
            br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        BufferedReader fbr = null;

        try {
            String line = br.readLine();
            int start = line.indexOf(" ") + 2;
            int end = line.lastIndexOf("HTTP") - 1;
            String filename = line.substring(start, end);
            if (filename.equals("")) filename = "index.html";
            System.out.println("사용자가 " + filename + "을 요청했습니다");
            fbr = new BufferedReader(new FileReader(filename));
            String fline = null;
            while ((fline = fbr.readLine()) != null) {
                pw.println(fline);
                pw.flush();
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (fbr != null) fbr.close();
                if (br != null) br.close();
                if (pw != null) pw.close();
                if (sock != null) sock.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }
}
