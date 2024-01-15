package org.example.len.ch8_TCP.web;

import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

class ChatThread extends Thread {
    private Socket sock;
    private String id;
    private BufferedReader br;
    private ConcurrentHashMap hm;
    private boolean iniFlag = false;

    public ChatThread(Socket sock, ConcurrentHashMap hm) {
        this.sock = sock;
        this.hm = hm;

        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            br.close();
            id = br.readLine();
            broadcast(id + "님이 접속했습니다.");
            System.out.println("접속한 사용자 아이디는 " + id);
            synchronized (hm) {
                hm.put(this.id, pw);
            }
            iniFlag = true;
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                if (line.equals("/quit")) {
                    break;
                }
                if (line.indexOf("/to") == 0) {
                    sendmsg(line);
                } else {
                    broadcast(id + " : " + line);
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            synchronized (hm) {
                hm.remove(id);
            }
            broadcast(id + " 님이 종료");
                if (sock != null) {
                    try {
                        sock.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

        }
    }

    private void sendmsg(String msg) {
        int start = msg.indexOf(" ") + 1;
        int end = msg.indexOf(" ", start);

        if (end != -1) {
            String to = msg.substring(start, end);
            String msg2 = msg.substring(end + 1);
            Object obj = hm.get(to);
            if (obj != null) {
                PrintWriter pw = (PrintWriter) obj;
                pw.println(id + " 님이 다음의 귓속말 보냄" + msg2);
                pw.flush();
            }
        }
    }

    private void broadcast(String s) {
        synchronized (hm) {
            Collection values = hm.values();
            Iterator iter = values.iterator();
            while (iter.hasNext()) {
                PrintWriter pw = (PrintWriter) iter.next();
                pw.println(s);
                pw.flush();
            }
        }
    }

}
