package org.example.len.ch11_multicast;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastChatClient extends Frame implements ActionListener {
    private TextField idTF = null;
    private TextField input = null;
    private TextArea display = null;
    private CardLayout cardLayout = null;

    DatagramSocket socket = null;
    DatagramPacket spacket = null;
    InetAddress schannel = null;
    int sport = 20010;
    String saddress = "239.0.0.1";
    boolean onair = true;
    String id = "";

    public MulticastChatClient() {
        super("채팅 클라이언트");
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        Panel loginPanel = new Panel();
        loginPanel.setLayout(new BorderLayout());
        loginPanel.add("North", new Label("아이디를 입력해주신 후 엔터 키를 입력해주세요"));
        idTF = new TextField(20);
        idTF.addActionListener(this);
        Panel c = new Panel();
        c.add(idTF);
        loginPanel.add("Center", c);
        add("login", loginPanel);

        Panel main = new Panel();
        main.setLayout(new BorderLayout());
        input = new TextField();
        input.addActionListener(this);
        display = new TextArea();
        display.setEditable(false);
        main.add("Center", display);
        main.add("South", input);
        add("main", main);

        try {
            socket = new DatagramSocket(sport);
        } catch (Exception e) {
            System.out.println("서버와 접속 시 오류 발생");
            System.out.println(e);
            System.exit(1);
        }

        setSize(500, 500);
        cardLayout.show(this, "login");

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.out.println("종료합니다.");
                sendMsg(id + "님이 종료합니다.");
                try {
                    socket.close();
                } catch (Exception e) {
                    System.exit(0);
                }
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) throws IOException{
        new MulticastChatClient();
    }

    // 문자열을 인자로 전달받아 내부 멀티캐스팅 그룹 IP에 패킷을 전송한다.
    // 그 결과 모든 해당 멀티캐스트 그룹에 포함된 모든 채팅 클라이언트는 패킷을 읽을 수 있게 된다.
    public void sendMsg(String msg) {
        byte[] b = new byte[2000];
        try {
            b = msg.getBytes();
            schannel = InetAddress.getByName(saddress);
            spacket = new DatagramPacket(b, b.length, schannel, sport);
            // 문자열을 인자로 전달받아, send()를 통해 내부 멀티캐스팅 그룹 IP에 패킷을 전송
            socket.send(spacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == idTF) {
            id = idTF.getText();
            if (id == null || id.trim().equals("")) {
                System.out.println("아이디를 다시 입력해주세요.");
                return;
            }
            sendMsg(id + "님이 입장했습니다.");
            WinInputMulticastThread wit = new WinInputMulticastThread();
            wit.start();
            cardLayout.show(this, "main");
            input.requestFocus();
        } else if (e.getSource() == input) {
            String msg = input.getText();
            sendMsg(id + ":" + msg + "/n");
            if (msg.equals("/quit")) {
                try {
                    socket.close();
                } catch (Exception exception) {
                    sendMsg(id + "님이 종료합니다.");
                    System.out.println("종료");
                    System.exit(1);
                }
                input.setText("");
                input.requestFocus();
            }
        }
    }

    // WinInputMulticastThread 객체는 멀티캐스팅 그룹에 전달된 패킷을 읽어 들여 화면에 출력하기 위한 객체이다.
    class WinInputMulticastThread extends Thread {
        MulticastSocket receiver = null;
        DatagramPacket packet = null;
        InetAddress channel = null;
        int port = 20009;
        String address = "239.0.0.1";

        public WinInputMulticastThread() {
            try {
                receiver = new MulticastSocket(port);
                channel = InetAddress.getByName(address);
                receiver.joinGroup(channel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 실제로 멀티캐스팅 그룹에서 패킷을 읽어와 채팅 윈도우의 TextArea에 읽어온 내용을 추가하게 되어 있다.
        // 접속을 종료할 상황이 되면 멀티캐스팅 그룹에 대한 참여를 해지한 후 MulticastSocket를 닫는다.
        public void run() {
            try {
                while (true) {
                    byte[] b = new byte[2000];
                    packet = new DatagramPacket(b, b.length);
                    receiver.receive(packet);
                    String msg = new String(packet.getData());
                    if (msg.equals("/quit")) {
                        break;
                    }
                    display.append(msg);
                }
                receiver.leaveGroup(channel);
                receiver.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }// InputThread 메서드 종료
    }// WinInputMulticastThread 메서드 종료
}// MulticastChat Client class 종료
