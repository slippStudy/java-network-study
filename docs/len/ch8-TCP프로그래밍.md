# TCP 프로그래밍

가장 기본이 되는 TCP 프로그래밍

TCP 의 역사를 살펴보면, ARPANET 으로부터 시작됐는데 전쟁 대비 신뢰성있는 데이터를 전송하기 위해서 생김

- TCP 경우 연결지향 방식이기 때문에 한번 연결되면, 연결이 끊어질 때까지는 송신한 데이터가 차례대로 목적지의 소켓에 전달되는 신뢰성 있는 통신이 가능
- 따라서 한번 연결을 맺게 되면, 안정적으로 통신할 수 있다
- 자바에서는 TCP 프로그래밍을 쉽게 하기 위해 java.net 패키지에 관련 클래스를 미리 준비함.

<img src="https://raw.githubusercontent.com/LenKIM/images/master/2023-11-12/image-20231112093836616.png" alt="image-20231112093836616" style="zoom:50%;" />



TCP 방법을 이용해 클라이언트와 서버가 통신하려면 양쪽에 Socket 객체가 있어야 한다. 이때 소켓을 생성하는 방법은 클라이언트 쪽과 서버쪽이 다르다.

가장 큰 차이점은 

- 서버는 클라이언트가 접속하기를 기다리고 있어야 한다는 것. 
- 클라이언트는 기다라고 있는 서버에게 접속을 시도한다는 점.



### 1. 클라이언트의 접속 대기

### 2. 클라이언트 접속

### 3. Socket 으로부터 InputStream 과 OutputStream 구하기

### 4. 접속 끊기

> ReadLine 을 사용할 경우, 개행문자를 삭제한다. 그러므로 추후 사용시 개행문자를 추가해야 한다.

## 2. 간단한 에코 클라이언트/서버 프로그래밍

에코(Echo)는 말 그대로 메아리 의미

클라리언트가 보낸 데이터를 서버 쪽에서 받아들여, 클라이언트에게 그대로 다시 보내주는 것을 의미

**에코 서버**

1. 1001번 포트에서 동작하는 ServerSocket을 생성
2. ServerSocket의 accpet() 메소드를 실행해서 클라이언트의 접속을 대기한다
3. 클라이언트가 접속할 경우 accpet()메소드는 Socket 객체를 반환
4. 반환 받는 Socket으로부터 InputStream과 OutputStream을 구한다.
5. InputStream은 BufferedReader 형식으로 변환하고 OutputStream은 PrintWriter 형식으로 변환
6. BufferedReader의 readLine() 메소드를 이용해서 클라이언트가 보내는 문자열 한 줄을 읽어 들인다.
7. 6에서 읽어들인 문자열을 PrintWriter에 있는 println() 메소드를 이용해서 다시 클라이언트에게 전송
8. 6,7 작업은 클라이언트가 접속을 종료할 때까지 반복. 클라이언트가 접속을 종료하게 되면 BufferedReader에 있는 readLine() 메소드는 null값을 반환
9. IO객체와 소캣의 close() 메소드 호출

**에코 클라이언트**

1. Socket 생성자에 서버의 IP와 서버의 동적 포트 값(10001)을 인자로 넣어 생성. 소켓이 생공적으로 생성되었다면, 서버와 접속이 성공적으로 되었다는 것을 의미
2. 생성된 Socket으로부터 InputStream과 OutputStream을 구한다.
3. InputStream은 BufferedReader 형식으로 변환하고 OutputStream은 PrintWriter 형식으로 변환

4. 키보드로부터 한 줄씩 입력받는 BuffedReader 객체 생성
5. 키보드로부터 한 줄을 입력받아 PrintWriter에 있는 println() 메소드를 이용해서 서버에게 전송
6. 서버가 다시 반환하는 문자열을 BufferedReader에 있는 readline() 메소드를 이용해서 읽어 들인다. 읽어 들인 문자열을 화면에 출력
7. 4,5,6 을 키보드로부터 quit 문자열을 입력 받을 때까지 반복
8. 키보드로부터 quit 문자열이 입력되면 IO 객체와 소켓의 cloase() 메소드를 호출한다.

```java
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
```

네트워크 프로그래밍의 경우, 출력한 후에는 반드시 flush() 메소드를 호출해줘야 한다. flush() 메소드를 호출하지 않을 경우에는 실제로 전송이 안되는 경우가 발생할 수 있기 때문

```java
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
```

문제점?

서버가 단 하나의 클라이언트 접속만을 처리할 수 있다는 것. accept()로 대기하고 있다가 클라이언트의 접속 요청이 오면 클라이언트와 통신할 수 있는 소켓을 반환한 후, 다시 accept()하지 않기 때문.



그렇다면 동시에 다중 사용자는 어떻게 처리할 수 있을까?

## 3. 멀티스레드를 이용한 에코 서버

여러가지 방법중, 스레드를 이용하는 것인데, 클라이언트를 사용하기 위해서 왜 스레드를 사용해야 하는 것일까? 서버는 연결된 클라이언트의 수만큼 소켓을 가지게 되는데, 각각의 소켓은 각각 별개로 동작해야 하기 때문.

<img src="https://raw.githubusercontent.com/LenKIM/images/master/2023-11-13/image-20231113124154309.png" alt="image-20231113124154309" style="zoom: 33%;" />



```java
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
```

여러 스레드에서 확인하기



## 4. 간단한 웹 서버 프로그래밍 

인터넷 브라우저를 이용해 웹 서핑을 즐긴다.

간단한 웹 서버를 소켓 프로그래밍으로 구현.

<img src="https://raw.githubusercontent.com/LenKIM/images/master/2023-11-13/image-20231113162346326.png" alt="image-20231113162346326" style="zoom:50%;" />

```java
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

```

![image-20231113163930484](https://raw.githubusercontent.com/LenKIM/images/master/2023-11-13/image-20231113163930484.png)

<img src="https://raw.githubusercontent.com/LenKIM/images/master/2023-11-13/image-20231113164255146.png" alt="image-20231113164255146" style="zoom: 33%;" />



```java
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
```

문제가 있다? 사용자의 중요한 파일이 노출될 수 있다.

## 5. 간단한 채팅 클라이언트/서버 프로그래밍

클라이언트의 동작 방법

1. 채팅 클라이언트를 실행할 때 사용자의 아이디와 접속할 서버의 IP주소를 전달
2. 다른 클라이언트가 접속하면, "XXX님이 접속"란 메세지 출력
3. 다른 사람의 대화 내용이 클라이언트에게 키보드로 입력하는 중에도 전달되어 화면에 출력
4. 클라이언트에서 키보드로 문장을 입력한 후 엔터 키를 입력하면, 접속된 모든 클라이언트에 입력된 문자열이 전송
5. 클라이언트 종료하면, "XXX님이 접속 종료했음"란 메세지가 출력

위 내용을 보아 클라이언트는 3가지 동작을 할 수 있음

- 클라이언트 ID정보를 서버에 전송
- 클라이언트에서 키보드로 입력된 문자열을 서버에 전송
- 클라이언트의 접속이 종료될 경우, 접속이 종료되었음을 서버에 알림



반대로 서버의 경우

1. 클라이언트 여러 개가 서버에 접속할 수 있어야 한다.
2. 클라이언트가 접속할 경우, 서버는 이미 접속되어 있는 클라이언트에게 전달받은 문자열을 전송해야 한다.
3. 클라이언트가 문자열을 전송할 경우, 서버는 접속되어 있는 모든 클라이언트에게 전달받은 문자열을 전송해야 한다.
4. 클라이언트가 접속을 종료했을 경우, 서버는 접속되어 있는 클라이언트에게 'XXX 님 종료' 문자열을 전송해야 한다.



이런 특성을 보면 클라는 입/출력을 동시에 할 수 있어야 하며, 서버는 클라 여러 개로부터 입출력을 동시에 해야 한다.



