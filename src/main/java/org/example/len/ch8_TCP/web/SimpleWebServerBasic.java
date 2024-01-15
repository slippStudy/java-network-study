package org.example.len.ch8_TCP.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

class SimpleWebServerBasic {

    public static void main(String[] args) {
        Socket sock = null;
        BufferedReader br = null;
        try {
            ServerSocket ss = new ServerSocket(80);
            sock = ss.accept();
            br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (br != null) br.close();
            } catch (Exception ex) {
            }

            try {
                if (sock != null) sock.close();
            } catch (Exception ex) {
            }
        }
    }
}
