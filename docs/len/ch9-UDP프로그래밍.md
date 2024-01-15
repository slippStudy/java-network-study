# UDP 를 이용한 프로그래밍 방법

데이터그램 통신 프로토콜이라 한다.

- 비연결성
- UDP는 패킷을 보낼 때마다 수신 측의 주소와 로컬 파일 설명자를 함께 전송
- 그러므로, 전송해야 할 데이터 외에 추가적인 데이터 전송.
- 단점은 전송한 순서대로 도달하지 않은 문제
- 크기가 64KB 로 제한되어 있다는 점



<img src="https://raw.githubusercontent.com/LenKIM/images/master/2023-11-18/image-20231118150345147.png" alt="image-20231118150345147" style="zoom:33%;" />

UDP 방식의 클라이언트와 서버의 동작 순서

- 클라는 DatagramSocket 생성.
- 서버와는 다르게 동작하는 포트는 지정하지 않음. 클라이언트는 전송할 데이터그램 패킷에 서버의 DatagramSocket의 동작 포트, 서버의 IP, 전송할 데이터, 전송할 데이터 길이 등을 지정한 후 send() 메소드를 이용해서 전송

```java
package org.example.len.ch9_UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

class UDPEchoServer {

    public static void main(String[] args) {

        final int port = 11011;
        DatagramSocket dsock = null;
        try {
            System.out.println("접속 대기");
            dsock = new DatagramSocket(port);

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                dsock.receive(receivePacket);
                String msg = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("전송 받은 문자열: " + msg);
                if (msg.equals("quit")) break;


                //전송
                DatagramPacket sendPacket = new DatagramPacket(receivePacket.getData(),
                        receivePacket.getData().length, receivePacket.getAddress(), receivePacket.getPort());
                dsock.send(sendPacket);
            }
            System.out.println("UPDEchoServer 종료");

        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
```

```java
package org.example.len.ch9_UDP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

class UDPEchoClient {

    public static void main(String[] args) {
        String ip = "localhost";
        int port = 11011;
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }


        try {
            DatagramSocket dsock = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            dsock = new DatagramSocket();
            String line = null;
            while ((line = br.readLine()) != null) {

                // 전송
                DatagramPacket sendPacket =
                        new DatagramPacket(line.getBytes(), line.getBytes().length, inetAddress, port);
                dsock.send(sendPacket);

                if (line.equals("quit"))break;

                // 받기
                byte[] buffer = new byte[line.getBytes().length];
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                dsock.receive(receivePacket);

                //받은 결과 출력
                String msg = new String(receivePacket.getData());
                System.out.println("전송받은 문자열: " + msg);
            }

            System.out.println("Client 종료");
            dsock.close();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

        }
    }
}
```

## UDP를 이용한 타임 서버와 클라이언트 작성

타임서버란> 클라이언트가 시간에 대해서 요청을 보내면, 서버의 시간을 구해서 시간에 대한 요청을 보낸 클라이언트에게 현재 시간에 대한 정보를 전송

타임 서버와 클라이언트는 UDP를 이용하는 것이 맞음. 왜냐하면? 전송하는 데이터가 간단하고 빠르게 반응하게 하기 위해서.



> 타임서버는 UDP 통신으로, 123번 포트로 동작한다.

```java
package org.example.len.ch9_UDP.time;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

class UDPTimeServer {

    public static void main(String[] args) {
        int port = 123;

        try {
            DatagramSocket dsock = new DatagramSocket(port);
            System.out.println("접속 대기 상태");

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                dsock.receive(receivePacket);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
                String sDate = simpleDateFormat.format(new Date());

                System.out.println( receivePacket.getAddress().getHostAddress() + "시간 전송: " + sDate);

                DatagramPacket sendPacket = new DatagramPacket(sDate.getBytes(), sDate.getBytes().length, receivePacket.getAddress(), receivePacket.getPort());
                dsock.send(sendPacket);
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
```

```java
package org.example.len.ch9_UDP.time;

import java.io.IOException;
import java.net.*;

class UDPTimeClient {

    public static void main(String[] args) throws UnknownHostException {
        String ip = "localhost";
        int port = 123;

        InetAddress inetAddress = InetAddress.getByName(ip);

        DatagramSocket dsock = null;
        try {
            dsock = new DatagramSocket();
            // 전송
            DatagramPacket sendPacket = new DatagramPacket("".getBytes(), "".getBytes().length, inetAddress, port);
            dsock.send(sendPacket);

            byte[] buffer = new byte[200];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            dsock.receive(receivePacket);;

            // 받은 결과 출력
            String msg = new String(receivePacket.getData());
            System.out.println("서버로부터 전달받은 시간:" + msg.trim());
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
```