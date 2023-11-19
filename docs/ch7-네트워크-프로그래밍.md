---
tags:
  - 학습정리
  - SLiPP_25차_스터디
  - Java
  - Network
  - 책_자바_IO_NIO_네트워크_프로그래밍
---
# 01. 소켓이란

> [!info] 네트워크 프로그래밍에서의 소켓이란
> 사용자에게 네트워크에 접근할 수 있는 인터페이스를 제공해준다는 의미를 가진다.

- 소켓을 사용하려면 ==소켓 생성(소켓 열기)==, ==소켓을 통한 송신과 수신==, ==소켓 소멸(소켓 닫기)==의 세 가지 과정을 이해해야 한다.
- 소켓은 TCP 와 UDP 를 이용한 두 가지 방법이 있으며 세 가지 소켓 형식이 있다.

> [!tip]
> 네트워크에 강력한 운영체제는 전통적으로 유닉스 운영체제다.
> 유닉스 운영체제는 대부분 네트워크 방식을 지원하고 있으며, 수많은 프로그래밍 도구를 제공하고 있다.
> 
> 유닉스 시스템은 같은 컴퓨터 안의 프로세스(Process) 간에 통신하기 위해서 파이프(pipe)를 사용한다.
> 파이프란 프로세스 두 개인 A, B 가 있을 경우 A 에서 출력한 결과를 B 의 입력으로 전달하는 방법을 말한다.
> 
> 파이프로 인해서 동일한 컴퓨터 안에서 프로세스 사이에 통신할 수 있는 것이다.
> 하지만, 서로 다른 컴퓨터에 있는 프로세스들은 파이프를 이용해서 통신할 수 없다.
> 이런 문제를 해결하기 위해서 버클리 유닉스에 최초로 소켓이라는 방법이 도입되었다.
> 
> 소켓은 유닉스 시스템에서의 파일 입출력과 같은 방식으로 사용되도록 만들어져 있다.
> 유닉스 시스템은 파일을 입출력하기 위해서 다음과 같은 과정을 거친다.
> 
> - 파일 열기 -> 파일 읽기/쓰기 -> 파일 닫기
> 
> 소켓도 위의 방식을 그대로 받아들여 다음과 같이 같은 방식으로 통신하도록 설계되었다.
> 
> - 소켓 열기 -> 소켓을 통한 읽기/쓰기 -> 소켓 닫기

## 1. 소켓의 세 가지 형식

- 소켓의 세 가지 형식에는 `SOCK_STREAM`, `SOCK_DGRAM`, `SOCK_RAW` 가 있다.
	- 이 중 `SOCK_RAW` 는 자바에서 보안상 지원하지 않는다.

### `SOCK_STREAM`

바이트를 주고받을 수 있는 스트림(Stream) 통신을 구현할 수 있게 해주는 소켓으로 양방향 통신이 가능하다.
- 이 형식을 이용하는 통신 방법은 TCP 이다.

### `SOCK_DGRAM`

데이터그램 통신용 소켓으로 `SOCK_STREAM` 과 마찬가지로 양방향 통신이 가능하다.
- 이 형식을 이용하는 통신 방법은 UDP 이다.

### `SOCK_RAW`

> [!info]- 좀 더 높은 수준의 제어를 하고 싶은 사용자를 위한 형식
> - 패킷을 전달할 때 패킷이 지나갈 경로까지 지정할 수 있다.
> - 이는 패킷을 받는 쪽에게 잘못된 경로의 패킷을 전송할 수도 있다는 것을 의미한다.
> - 
> - 마치, 이미 접근을 허락 받은 클라이언트인 것처럼 잘못된 내용을 담아서 전송할 수 있다는 것이다.
> - 이러한 이유로 보안상 문제가 발생할 수 있기 때문에 자바 언어에서는 `SOCK_RAW` 를 지원하지 않는다.
> - 
> - 이 형식을 이용하는 대표적인 프로그램에는 핑(Ping) 프로그램이 있다.
> - 
> - 핑 프로그램은 ICMP(Internet Control Message Protocol)를 사용하는데, ICMP 패킷은 `SOCK_RAW` 형식의 소켓만을 지원한다.
> - 
> - 이런 이유로 자바에서는 제대로 된 핑 프로그램을 구현할 수 없다.
> - 결국, 대부분의 자바 네트워크 책에서 소개되는 핑 프로그램은 핑 프로그램의 흉내를 내는 것 뿐이다.

## 2. 인터넷 주소와 포트

- 인터넷 주소 : 인터넷에서 유일한 컴퓨터를 구분하는 주소, 즉 IP 이다.
- 포트 : 컴퓨터 안의 프로세스를 구분하는 주소

- 하나의 컴퓨터에는 프로세스 여러 개들이 소켓으로 통신한다.
- 이때 각각의 소켓을 구분하기 위해서 포트를 사용하게 된다.

# 02. InetAddress 클래스를 활용한 도메인과 IP 변환

> [!note] IP 와 관련된 클래스, InetAddress
> - java.net 패키지에 있는 클래스
> - 도메인 주소를 IP 주소로 변환하거나 반대로 IP 주소를 도메인 주소로 변경할 수 있다.
> - 문자열이나 바이트 배열 형태로 IP 주소에 대한 정보를 얻을 수 있다.
> - 현재 컴퓨터의 이름을 구할 수 있다.

- InetAddress 객체는 생성자를 이용해서 객체를 생성하지 않고, 정적 메소드를 이용하여 객체를 생성한다.

- InetAddress 클래스는 IP 를 이용해서 IP 에 해당하는 도메인도 구할 수 있지만, 이 부분은 신뢰성이 떨어진다.
- 도메인에 대한 IP 를 구하고자 할 경우에만 사용하는 것이 좋다.

![[자바 IO & NIO 네트워크 프로그래밍 - 표 7-1.png]]

![[자바 IO & NIO 네트워크 프로그래밍 - 표 7-2.png]]

## 1. InetAddress 클래스를 이용한 nslookup 명령 구현

```java
import java.net.InetAddress;  
  
public class NSLookup {  
    public static void main(String[] args) {  
        InetAddress inetaddr[] = null;  
        try {  
            inetaddr = InetAddress.getAllByName("www.google.com");  
        } catch (Exception e) {  
            e.printStackTrace();  
            return;  
        }  
  
        for (int i = 0; i < inetaddr.length; i++) {  
            System.out.println(inetaddr[i].getHostName());  // 도메인명
            System.out.println(inetaddr[i].getHostAddress()); // IP주소 
            System.out.println(inetaddr[i].toString()); // 도메인명/IP주소
            System.out.println();  
        }  
    }  
}
```

![[자바 IO & NIO 네트워크 프로그래밍 - 예제 7-1.png|400]]

- 하나의 도메인은 IP 정보 여러 개를 가질 수 있기 때문에 배열 형태로 반환된다.
- 만약 찾을 수 없는 도메인명이거나 잘못된 형식의 IP 주소라면 UnknownHostException 이 발생한다.

## 2. InetAddress 클래스를 이용한 로컬 컴퓨터명과 IP 구하기

```java
import java.net.InetAddress;  
import java.net.UnknownHostException;  
  
public class NSLookupLocal {  
    public static void main(String[] args) {  
        InetAddress inetAddress = null;  
        try {  
            inetAddress = InetAddress.getLocalHost();  
        } catch (UnknownHostException e) {  
            e.printStackTrace();  
            return;  
        }  
  
        System.out.println(inetAddress.getHostName()); // 호스트 주소  
        System.out.println(inetAddress.getHostAddress()); // IP 주소  
  
        System.out.println("byte[] 형식의 ip 주소 값의 출력");  
        byte[] ip = inetAddress.getAddress();  
        for (int i = 0; i < ip.length; i++) {  
            System.out.println(Byte.toUnsignedInt(ip[i]));  
            if (i != ip.length - 1) {  
                System.out.print(".");  
            }  
        }  
    }  
}
```

![[자바 IO & NIO 네트워크 프로그래밍 - 예제 7-2.png|300]]
