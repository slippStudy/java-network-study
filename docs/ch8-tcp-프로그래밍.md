---
tags:
  - 학습정리
  - SLiPP_25차_스터디
  - Java
  - Network
  - 책_자바_IO_NIO_네트워크_프로그래밍
---
# 01. TCP 프로그래밍 기본

> [!note] TCP 란
> 스트림 통신 프로토콜

- TCP 통신을 하려면 양쪽의 소켓이 연결된 상태여야만 가능하다.
- 그렇기 때문에 연결지향 프로토콜이라고도 말한다.
- TCP 의 경우 연결지향 방식이기 때문에 한번 연결되면, 연결이 끊어질 때까지는 송신한 데이터가 차례대로 목적지의 소켓에 전달되는 신뢰성 있는 통신이 가능하다.

- 신뢰성 있는 데이터 전송이란 송신한 쪽의 데이터가 수신 측에 차례대로, 중간에 유실되는 일 없이 도착하는 것을 말한다.

- 자바 언어에서 TCP 프로그래밍을 하기 위해서는 java.net 패키지 클래스들을 이용하면 된다.
- TCP 프로그래밍에서 가장 중요한 클래스는 
  `java.net.ServerSocket` 과 `java.net.Socket` 이다.
- `java.net.ServerSocket`
	- 서버 쪽에서 클라이언트의 접속을 대기하기 위하여 사용하는 클래스
- `java.net.Socket`
	- 서버와 클라이언트가 통신하기 위해서 필요한 클래스

![[자바 IO & NIO 네트워크 프로그래밍 - 그림 8-3.png]]

- TCP 방법을 이용해서 클라이언트와 서버가 통신하려면 양쪽에 Socket 객체가 있어야 한다.
- 이때 소켓을 생성하는 방법은 클라이언트 쪽과 서버 쪽이 다르다.

- 서버와 클라이언트의 가장 큰 차이점은, 서버는 클라이언트가 접속하기를 기다리고 있어야 한다는 것이고, 클라이언트는 기다리고 있는 서버에게 접속을 시도한다는 점이다.

## 1. 클라이언트의 접속 대기

- 서버 쪽에서는 클라이언트의 접속을 기다리기 위해서 ServerSocket 객체를 생성한 후 ServerSocket 에 있는 `accept()` 메소드를 실행해서 대기하게 된다.

- 이렇게 무엇인가 대기하기 위해서 멈춰 있는 메소드를 <u>블로킹 메소드</u>라 한다.

```java
ServerSocket server = new ServerSocket(10001);  
System.out.println("접속을 기다립니다.");  
Socket socket = server.accept();
```

## 2. 클라이언트 접속

- 서버에서 위처럼 대기하고 있으면, 클라이언트는 서버로 접속할 수 있다.
- 클라이언트에서 Socket 만 생성하면 내부적으로 알아서 접속이 일어나게 된다.

- 클라이언트에서 Socket 객체가 성공적으로 생성되었다면, 서버의 `accept()` 메소드는 클라이언트에 대한 Socket 객체를 반환하게 된다.

```java
Socket socket = new Socket("127.0.0.1", 10001);
```

- 클라이언트에서 위 문장이 실행되면 `accept()` 메소드는 대기하고 있다가 접속한 클라이언트의 Socket 객체를 반환한다.

```java
Socket socket = server.accept();
```

## 3. Socket 으로부터 InputStream 과 OutputStream 구하기

- 클라이언트에서 Socket 을 성공적으로 생성했다면(클라이언트에서 서버로 접속에 성공했다면), 
  서버와 클라이언트는 각각 Socket 객체를 가지게 된다.
	- 클라이언트에 있는 소켓은 서버로부터 읽어 들이고 서버에 쓰기 위한 객체
	- 서버에 있는 소켓은 클라이언트로부터 읽어 들이고 클라이언트에 쓰기 위한 객체

- 소켓이 있다는 것은 소켓이 연결되어 있는 곳에 
  읽고 쓸 수 있는 InputStream 과 OuputStream 을 구할 수 있다는 것이다.

```java
OutputStream out = socket.getOutputStream();
InpustStream in = socket.getInputStream();
```

- OutputStream 을 통해서 상대방에게 스트림을 전송할 수 있고, inputStream 을 통해 상대방이 전송한 스트림을 읽어 들일 수 있다.

![[자바 IO & NIO 네트워크 프로그래밍 - 그림 8-4.png]]

- 아래와 같이 쓸 수 도 있다.
```java
PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
BufferedReader br = new BufferedReader(new InputStreamReader(in));
```

## 4. 접속 끊기

- 읽고 쓰는 것은 양쪽의 소켓 중에서 어느 한쪽의 소켓이 끊어질 때까지 가능하다.
- 소켓은 아래 방법으로 끊는다.

```java
socket.close();
```

# 02. 간단한 에코 클라이언트/서버 프로그래밍

## 1. 에코 서버 프로그래밍

```java
import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.io.OutputStreamWriter;  
import java.io.PrintWriter;  
import java.net.InetAddress;  
import java.net.ServerSocket;  
import java.net.Socket;  
  
public class EchoServer {  
    public static void main(String[] args) {  
        try {  
            ServerSocket server = new ServerSocket(10001); // 서버소켓 생성
            System.out.println("접속을 기다립니다.");  
            Socket sock = server.accept(); 
            // accpet 메소드를 실행하면 클라이언트가 접속할 때까지 블로킹 상태가 된다.
            // 클라이언트가 접속하면 클라이언트와 통신할 수 있게 도와주는 Socket 객체를 반환한다.
  
            InetAddress inetAddress = sock.getInetAddress(); // 상대 IP 주소를 구한다.
            System.out.println(inetAddress.getHostAddress() + " 로부터 접속했습니다.");  
  
            BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream())); // 쓰기 편하도록 InputStream 을 다른 객체로 바꿈
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream())); // 읽기 쉽도록 OutputStream 을 다른 객체로 바꿈
  
            String line;  
            while ((line = br.readLine()) != null) {  
                System.out.println("클라이언트로 부터 전송받은 문자열 : " + line);  
                pw.println(line);  
                pw.flush();  
            }  
  
            br.close();  
            pw.close();  
            sock.close();  
  
        } catch (Exception e) {  
            System.out.println("연결종료");  
        }  
    }  
}
```

> [!important] 한 줄을 읽어 들이는 메서드를 사용할 때의 주의점
> - BufferedReader 의 `readLine()` 메소드는 읽어 들일 때 개행문자를 삭제한다.
> - 따라서 다시 클라이언트 쪽으로 문자열을 전송할 때에는 문자열 뒤에 개행문자를 추가해서 전송해야 한다.
> - 그래서 PrintWriter 의 `println()` 메소드를 사용한 것이다.

> [!important] 출력할 때 `flush()` 메소드 호출을 잊지 말자
> - 네트워크 프로그래밍의 경우, 출력한 후에는 반드시 `flush()` 메소드를 호출해줘야 한다.
> - `flush()` 메소드를 호출하지 않을 경우에는 실제로 전송이 안되는 경우가 발생할 수 있다.
> - `\n` 개행문자, 버퍼가 꽉찼을때, close 메서드 호출했을 때 flush 호출된다.

## 2. 에코 클라이언트 프로그래밍

```java
import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.io.OutputStreamWriter;  
import java.io.PrintWriter;  
import java.net.Socket;  
  
public class EchoClient {  
    public static void main(String[] args) {  
        try {  
            Socket sock = new Socket("127.0.0.1", 10001);  
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));  
  
            BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));  
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));  
  
            String line;  
            while ((line = keyboard.readLine()) != null) {  
                if (line.equals("quit")) {  
                    break;  
                }  
                pw.println(line);  
                pw.flush();  
  
                String echo = br.readLine();  
                System.out.println("서버로부터 전달받은 문자열 : " + echo);  
            }  
  
            pw.close();  
            br.close();  
            sock.close();  
  
        } catch (Exception e) {  
            System.out.println("연결종료");  
        }  
    }  
}
```

- 키보드로부터 "quit" 문자열을 입력 받으면, 앞서 설명한 while 문으로 빠져나온다.
- 더 이상 서버와 통신하지 않을 것이기 때문에 IO 객체와 소켓에 있는 `close()` 메소드를 호출하게 된다.
- 이때 서버쪽에서는 BufferedReader 의 `readLine()` 메소드가 null 값을 반환하게 된다.

## 3. 에코 서버와 에코 클라이언트 실행

![[자바 IO & NIO 네트워크 프로그래밍 - 그림 8-5.png|650]]

![[자바 IO & NIO 네트워크 프로그래밍 - 그림 8-6.png|650]]

## 4. 에코 클라이언트와 에코 서버의 문제점

- 서버가 단 하나의 클라이언트 접속만 처리할 수 있다는 것이 문제다.
- `accept()` 로 대기하고 있다가 클라이언트의 접속 요청이 오면 클라이언트와 통신할 수 있는 소켓을 반환한 후, 다시 `accept()`  하지 않기 때문이다.

# 03. 멀티스레드를 이용한 에코 서버

- 서버가 클라이언트 여러 개를 동시에 처리하지 못하는 문제를 해결하는 방법에는 여러가지 방법이 있다.
- 여기서는 그 중 한 가지 방법인 스레드를 이용한 방법을 소개할 것이다.

> [!tip] 클라이언트를 사용하기 위해서는 왜 스레드를 사용해야 할까?
> 서버 입장에서, 서버는 연결된 클라이언트의 수만큼 소켓을 가지게 되는데, 각각의 소켓은 별개로 동작하기 때문이다.

![[자바 IO & NIO 네트워크 프로그래밍 - 그림 8-7.png]]

## 1. 멀티스레드 에코 서버 프로그래밍

```java
import java.net.ServerSocket;  
import java.net.Socket;  
  
public class EchoThreadServer {  
    public static void main(String[] args) {  
        try (ServerSocket server = new ServerSocket(10001)) {  
            System.out.println("접속을 기다립니다.");  
  
            while (true) {  
                Socket sock = server.accept();
                EchoThread echoThread = new EchoThread(sock);
                echoThread.start();
            }  
  
        } catch (Exception e) {}  
    }  
}
```

- 무한히 반복되는 while 문 안에서 `accept()` 메소드를 실행해서 서버를 대기시킨다.
- 클라이언트가 접속하게 되면 `accept()` 메소드는 접속한 클라이언트의 소켓 객체를 반환하게 된다.
- 반환받은 소켓 객체는 스레드 객체인 EchoThread 의 생성자에 인자로 지정한다.

- EchoThread 객체가 생성되면 `start()` 메소드를 실행해서 새로운 스레드가 실행되게 한다.
- 이때 EchoThread 의 `run()` 메소드는 자동으로 실행되며, 메인 스레드는 동시에 while문 위쪽으로 올라가서 다시 `accept()` 메소드로 다른 클라이언트의 접속에 대기하게 된다.

```java
import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.io.OutputStreamWriter;  
import java.io.PrintWriter;  
import java.net.InetAddress;  
import java.net.Socket;  
  
public class EchoThread extends Thread {  
    private Socket sock;  
  
    public EchoThread(Socket sock) {  
        this.sock = sock;  
    }  
  
    @Override  
    public void run() {  
        try {  
            InetAddress inetAddr = sock.getInetAddress();  
            System.out.println(inetAddr.getHostAddress() + " 로 부터 접속했습니다.");  
  
            BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));  
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));  
  
            String line;  
            while ((line = br.readLine()) != null) {  
                System.out.println("클라이언트로부터 전송받은 문자열 : " + line);  
                pw.println(line);  
                pw.flush();  
            }  
            pw.close();  
            br.close();  
            sock.close();  
  
        } catch (Exception e) {  
            System.out.println(e);  
        }  
    }  
}
```

## 2. 멀티스레드 에코 서버 실행

![[자바 IO & NIO 네트워크 프로그래밍 - 그림 8-8.png|400]]

![[자바 IO & NIO 네트워크 프로그래밍 - 그림 8-9.png|600]]

![[자바 IO & NIO 네트워크 프로그래밍 - 그림 8-10.png|600]]

# 04. 간단한 웹 서버 프로그래밍

간단한 웹 서버를 소켓 프로그래밍을 이용해서 직접 구현해보자.

## 1. 브라우저의 요청정보 출력

```java
import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.net.ServerSocket;  
import java.net.Socket;  
  
public class SimpleWebServerBasic {  
    public static void main(String[] args) {  
        try (ServerSocket serverSocket = new ServerSocket(80);  
             Socket sock = serverSocket.accept();  
             BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()))) {  
  
            String line;  
            while ((line = br.readLine()) != null) {  
                System.out.println(line);  
            }  
  
        } catch (Exception e) {  
            System.out.println();  
        }  
    }  
}
```

## 2. SimpleWebServerBasic 실행

- `http://localhost/hello/hello.html` 를 브라우저에서 실행하면 서버에서 아래와 같이 정보가 뜬다.
- 아래에서 가장 중요한 정보는 첫째 줄로, 브라우저가 GET 방식으로 '/' 페이지를 HTTP 1.1 프로토콜 방식으로 요청했다는 것을 의미한다.
- 웹 서버는 첫째 줄을 분석하여 브라우저가 요청한 파일을 읽어 들여 브라우저 쪽으로 출력한다.

![[자바 IO & NIO 네트워크 프로그래밍 - 그림 8-13.png|800]]

## 3. 간단한 웹 서버 프로그래밍: SimpleWebServer

- SimpleWebServer 는 브라우저 여러 개가 동시에 요청을 해도 문제가 없도록 작성할 것이다.

![[자바 IO & NIO 네트워크 프로그래밍 - 그림 8-15.png]]

```java
import java.net.ServerSocket;  
import java.net.Socket;  
  
public class SimpleWebServer {  
    public static void main(String[] args) {  
        try (ServerSocket ss = new ServerSocket(80)) {  
            while (true) {  
                System.out.println("접속을 대기합니다.");  
                Socket sock = ss.accept();  
                System.out.println("새로운 스레드를 시작합니다.");  
                HttpThread ht = new HttpThread(sock);  
                ht.start();  
            }  
        } catch (Exception e) {  
            System.out.println(e);  
        }  
    }  
}
```

- 브라우저의 요청정보를 읽어 들이고 응답을 보내는 것이 단 한번씩만 이루어진다.

```java
import java.io.BufferedReader;  
import java.io.FileReader;  
import java.io.InputStreamReader;  
import java.io.OutputStreamWriter;  
import java.io.PrintWriter;  
import java.net.Socket;  
  
public class HttpThread extends Thread {  
    private Socket sock;  
  
    public HttpThread(Socket sock) {  
        this.sock = sock;  
    }  
  
    @Override  
    public void run() {  
        String filename = "";  
        try (BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()))) {  
  
            String line = br.readLine();  
            int start = line.indexOf(" ") + 2;  
            int end = line.lastIndexOf("HTTP") - 1;  
  
            filename = line.substring(start, end);  
            if (filename.equals("")) {  
                filename = "index.html";  
            }  
        } catch (Exception e) {  
            System.out.println(e);  
        }  
  
        try (BufferedReader fbr = new BufferedReader(new FileReader(filename));  
             PrintWriter pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()))) {  
  
            System.out.println("사용자가 " + filename + "을 요청했습니다.");  
            String fline;  
            while ((fline = fbr.readLine()) != null) {  
                pw.println(fline);  
                pw.flush();  
            }  
        } catch (Exception e) {  
            System.out.println(e);  
        }  
    }  
}
```

## 4. index.html 파일 작성

- 생략

## 5. 브라우저를 이용해서 요청정보를 보내고 응답 받기

- 생략

## 6. SimpleWebServer 의 문제점

- 생략

# 05. 간단한 채팅 클라이언트/서버 프로그래밍

## 0. Intro

### 채팅 클라이언트의 동작 방법 정의

1. 채팅 클라이언트를 실행할 때 사용자의 아이디와 접속할 서버의 IP 주소를 전달한다.
2. 다른 클라이언트가 접속하면, "XXX님이 접속했습니다." 란 메시지를 출력한다.
3. 다른 사람의 대화 내용이 클라이언트에서 키보드로 입력하는 중에도 전달되어 화면에출력된다.
4. 클라이언트에서 키보드로 문장을 입력한 후 엔터 키를 입력하면, 접속된 모든 클라이언트에 입력된 문자열이 전송된다.
5. 클라이언트를 종료하면, "XXX님이 접속 종료했습니다." 란 메시지를 출력한다.

#### 클라이언트 -> 서버

- 클라이언트의 ID 정보를 서버에 전송한다.
- 클라이언트에서 키보드로 입력된 문자열을 서버에 전송한다.
- 클라이언트의 접속이 종료될 경우, 접속이 종료되었음을 서버에 알린다.

### 채팅 서버의 동작 방법 정의

1. 클라이언트 여러 개가 서버에 접속할 수 있어야 한다.
2. 클라이언트가 접속할 경우, 서버는 이미 접속되어 있는 클라이언트에게 "XXX님이 접속했습니다" 라는 문자열을 전송해야 한다.
3. 클라이언트가 문자열을 전송할 경우, 서버는 접속되어 있는 모든 클라이언트에게 전달받은 문자열을 전송해야 한다.
4. 클라이언트가 접속을 종료했을 경우, 서버는 접속되어 있는 클라이언트에게 "XXX님이 접속 종료했습니다" 라는 문자열을 전송해야 한다.

### 동시에 수행해야 하는 일들

클라이언트, 서버 모두 <u>동시에</u> 처리해야 하는 일이 있다.

- 클라이언트는 입력과 출력을 동시에 할 수 있어야 한다.
- 서버는 클라이언트 여러 개로부터 입출력을 동시에 해야 한다.

자바에서 "동시에" 라는 말이 나오면 "스레드" 가 필요하다는 뜻이다.

- 클라이언트는 입출력을 동시에 하기 위해 스레드를 사용할 것이다.
- 서버는 클라이언트 여러 개로부터 입출력을 하기 위해서 스레드를 사용할 것이다.

## 1. 채팅 서버 프로그래밍: ChatServer

- 채팅 서버는 스레드 간에 연관을 맺고 있다.
- 하나의 스레드가 클라이언트로부터 문자열을 전송 받으면, 다른 스레드에 있는 OutputStream 을 통해서 전송 받은 문자열을 재전송할 수 있기 때문이다.

- 즉, 클라이언트가 보낸 문자열을 접속한 모든 클라이언트에게 전송하기 위해서는 스레드 간에 접속한 클라이언트의 OutputStream 을 공유하는 방법이 필요하다는 것이다.

```java
import java.io.PrintWriter;  
import java.net.ServerSocket;  
import java.net.Socket;  
import java.util.Map;  
import java.util.concurrent.ConcurrentHashMap;  
  
public class ChatServer {  
    public static void main(String[] args) {  
        try (ServerSocket server = new ServerSocket(10001)) {  
            System.out.println("접속을 기다립니다.");  
            Map<String, PrintWriter> clientWriters = new ConcurrentHashMap<>();  
            while (true) {  
                Socket sock = server.accept();  
                ChatThread chatThread = new ChatThread(sock, clientWriters);  
                chatThread.start();  
            }  
        } catch (Exception e) {  
            System.out.println(e);  
        }  
    }  
}
```

```java
import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.io.OutputStreamWriter;  
import java.io.PrintWriter;  
import java.net.Socket;  
import java.util.Arrays;  
import java.util.List;  
import java.util.Map;  
  
public class ChatThread extends Thread {  
    private final Socket clientSocket;  
    private String id;  
    private BufferedReader clientBufferedReader;  
    private final Map<String, PrintWriter> clientPrintWriters;  
    private boolean initFlag = false;  
  
    public ChatThread(Socket clientSocket, Map<String, PrintWriter> clientPrintWriters) {  
        this.clientSocket = clientSocket;  
        this.clientPrintWriters = clientPrintWriters;  
        try {  
            clientBufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  
            this.id = clientBufferedReader.readLine();  
  
            broadcast(id + "님이 접속했습니다.");  
            System.out.println("접속한 사용자의 아이디는 " + id + "입니다.");  
  
            PrintWriter clientPrintWriter = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));  
            clientPrintWriters.put(this.id, clientPrintWriter);  
            initFlag = true;  
        } catch (Exception e) {  
            System.out.println(e);  
        }  
    }  
  
    @Override  
    public void run() {  
        try {  
            String line;  
            while ((line = clientBufferedReader.readLine()) != null) {  
                if (line.startsWith("/quit")) { // /quit 으로 시작하면 종료한다.  
                    break;  
                }  
                if (line.startsWith("/to ")) { // /to 로 시작하면 특정 클라이언트에게 메시지를 보낸다.  
                    sendMsg(line);  
                } else { // 전체 클라이언트에게 메시지를 보낸다.  
                    broadcast(id + " : " + line);  
                }  
            }  
        } catch (Exception e) {  
            System.out.println(e);  
        } finally {  
            clientPrintWriters.remove(id); // Client 목록에서 제거한다.  
            broadcast(id + " 님이 접속 종료했습니다.");  
            try {  
                if (clientSocket != null) {  
                    clientSocket.close();  
                }  
            } catch (Exception e) {  
                System.out.println(e);  
            }  
        }  
    }  
  
    private void broadcast(String msg) {  
        for (PrintWriter pw : clientPrintWriters.values()) {  
            pw.println(msg);  
            pw.flush();  
        }  
    }  
  
    private void sendMsg(String msg) {  
        List<String> splits = Arrays.asList(msg.split(" ", 3)); // format : /to 전송할_클라이언트_ID message  
        if (splits.size() == 3) {  
            String to = splits.get(1);  
            String realMsg = splits.get(2);  
  
            PrintWriter pw = clientPrintWriters.get(to);  
            if (pw != null) {  
                pw.println(id + " 님이 다음의 귓속말을 보내셨습니다. : " + realMsg);  
                pw.flush();  
            }  
        }  
    }  
}
```

## 2. 채팅 클라이언트 프로그래밍: ChatClient

- 채팅 클라이언트는 명령 창에서 동작하는 프로그램이다.
- 키보드로부터 입력 받은 문자열을, 소켓을 통해서 구한 PrintWriter 를 이용해서 출력한다.
- 그 결과 서버에 문자열이 전송된다.

- 그런데 문제는 키보드로 사용자가 글을 입력하고 있는 중간에도 서버에서 다른 클라이언트에서 전송한 문자열을 소켓을 통해서 전달 받을 수 있다는 것이다.

![[자바 IO & NIO 네트워크 프로그래밍 - 그림 8-19.png|700]]

- 결국, 메인 스레드가 키보드로부터 입력을 받을 때에는 다른 일을 할 수 없기 때문에 서버로부터 전달받은 문자열을 화면에 출력할 수 없다는 문제가 생긴다.
- 이러한 문제를 해결하려면 입력 따로, 출력 따로 동작하도록 채팅 클라이언트를 작성해야 한다.
	- 키보드로부터 문자열을 입력 받아 서버로 전달하는 것은 메인 스레드로 처리
	- 서버로부터 전송 받은 문자열은 스레드를 따로 작동시켜서 처리

![[자바 IO & NIO 네트워크 프로그래밍 - 그림 8-20.png|700]]



```java
import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.io.OutputStreamWriter;  
import java.io.PrintWriter;  
import java.net.Socket;  
  
public class ChatClient {  
    public static void main(String[] args) {  
        String clientId = args[0];  
        String serverIp = "127.0.0.1";  
  
        boolean endflag = false;  
  
        try (Socket sock = new Socket(serverIp, 10001);  
             PrintWriter clientOutputStream = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));  
             BufferedReader clientInputStream = new BufferedReader(new InputStreamReader(sock.getInputStream()));  
             BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))) {  
  
            clientOutputStream.println(clientId);  
            clientOutputStream.flush();  
  
            InputThread it = new InputThread(sock, clientInputStream);  
            it.start();  
  
            String line;  
            while ((line = keyboard.readLine()) != null) {  
                clientOutputStream.println(line);  
                clientOutputStream.flush();  
                if (line.equals("/quit")) {  
                    endflag = true;  
                    break;  
                }  
            }  
            System.out.println("클라이언트의 접속을 종료합니다.");  
  
        } catch (Exception e) {  
            if (!endflag) {  
                System.out.println(e);  
            }  
        }  
    }  
}
```

```java
import java.io.BufferedReader;  
import java.net.Socket;  
  
public class InputThread extends Thread {  
    private Socket sock;  
    private BufferedReader clientInputStream;  
  
    public InputThread(Socket sock, BufferedReader clientInputStream) {  
        this.sock = sock;  
        this.clientInputStream = clientInputStream;  
    }  
  
    @Override  
    public void run() {  
        try {  
            String line;  
            while ((line = clientInputStream.readLine()) != null) {  
                System.out.println(line);  
            }  
        } catch (Exception e) {  
            System.out.println(e);  
        } finally {  
            try {  
                if (clientInputStream != null) {  
                    clientInputStream.close();  
                }  
            } catch (Exception e) {}  
            try {  
                if (sock != null) {  
                    sock.close();  
                }  
            } catch (Exception e) {}  
        }  
    }  
}
```

## 3. 채팅 클라이언트와 서버의 실행

![[자바 IO & NIO 네트워크 프로그래밍 - 그림 8-21.png|500]]

![[자바 IO & NIO 네트워크 프로그래밍 - 그림 8-22.png|500]]

![[자바 IO & NIO 네트워크 프로그래밍 - 그림 8-23.png|400]]

## 4. 채팅 클라이언트/서버에서 아쉬운 부분

- 이 채팅 클라이언트와 서버는 방(room)이 하나만 있다.
- 이러한 점을 해결하려면 방 여러 개를 운영해야 한다.

# 06. 윈도우용 채팅 클라이언트 작성

생략

# 07. 객체 직렬화를 이용한 네트워크 프로그래밍

생략
