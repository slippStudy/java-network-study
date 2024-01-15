package org.example.len.ch8_TCP;

import java.io.*;
import java.net.Socket;

class EchoClient {

    public static void main(String[] args) throws IOException {

        Socket sock = new Socket("127.0.0.1", 10001);
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        OutputStream out = sock.getOutputStream();
        InputStream in = sock.getInputStream();
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String line = "";
        while ((line = keyboard.readLine()) != null) {
            if (line.equals("q")) break;
            pw.println(line);
            pw.flush();
            String echo = br.readLine();
            System.out.println("서버로부터 전달받은 문자열 :" + echo);
        }

        pw.close();
        br.close();
        sock.close();
    }
}
