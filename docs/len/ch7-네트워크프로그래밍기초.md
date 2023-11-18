## 01. 소켓이란?

실제 네트워크 프로그래밍에 대한 알아볼 예정.

- TCP/UDP 방식의 소켓 프로그래밍
- URL 관련 클래스를 이용한 프로그래밍과 멀티캐스트 프로그래밍 방식

```
socket
1. 꽂는[끼우는] 구멍, 베어링, 소켓;
..
```

전구 등을 꽂는 소켓.

네트워크 프로그래밍에서의 소켓이란? "사용자에게 네트워크에 접근할 수 있는 인터페이스를 제공해준다."

소켓 과정

- 소켓 생성(소켓 열기)
- 소켓을 통한 송신과 수신
- 소켓 소멸(소켓 닫기)



1. 소켓의 세 가지 형식
   - SOCK_STREAM - 바이트를 주고받을 수 있는 스트림(Stream)통신을 구현할 수 있게 해주는 소켓으로 양방향 통신 가능
     - TCP(Transfer Control Protocol)
   - SOCK_DGRAM - 데이터그램 통신용 소켓으로 SOCK_STREAM과 마찬가지로 양방향 통신이 가능
     - UDP(User Datagram Protocol)
   - SOCK_RAW - 자바에서 지원하지 않음 (feats, Ping 프로그램)
2. 인터넷 주소와 포트

네트워크를 통해서 정보를 전송하고 수신하려면 소켓이 필요하다. 그러면 당연히 인터넷 주소와 포트를 알아야 한다.

인터넷 주소와 포트는 무엇인가?

- 인터넷에서 유일한 컴퓨터를 구분하는 것은 인터넷 주소 - 즉 IP
  - IP를 이용하면 원하는 컴ㅍ터를 찾을 수 있다.
- 하나의 컴퓨터에는 프로세스 여러 개들의 소켓을 통신
  - 각각의 소켓을 구분하기 위해서 포트를 사용하게 되는데, 포트는 정수 값으로 되어 있는데 0 ~1023 는 known port
  - 유명 프로그램이 사용되도록 이미 정해진 포트며 일반 사용자는 1023 이후의 포트를 이용하게 됨

## 02. InetAddress 클래스를 활용한 도메인과 IP 변환

- 네트워크 내에서 컴퓨터와 컴퓨터 간에 통신을 하려면 IP 주소
- java.net 패키지에 있는 InetAddress
  - 도메인 주소를 IP 주소로 변환하거나 반대로 IP 주소를 도메인 주소로 변경할 수 있음
  - 문자열이나 바이트 배열 형태로 IP주소에 대한 정보를 얻을 수 있음

| 메소드                                         | 설명                                                         |
| ---------------------------------------------- | ------------------------------------------------------------ |
| static InetAddress[] getAllByName(String host) | 호스트의 모든 IP 주소에 대한 정보를 InetAddress 배열 형태로 반환 |
| static InetAddress getAllByName(byte[] host)   | 바이트로 표현된 addr에 해당하는 IP 정보를 InetAddress 객체 형태로 반환 |
| static InetAddress getLocalHost()              | 로컬 호스트의 IP주소에 대한 정보를 InetAddress 객체 형태로 반환 |



>  nslookup www.naver.com

| 메소드                       | 설명                                                         |
| ---------------------------- | ------------------------------------------------------------ |
| byte[] getAddress()          | IP주소를 바이트 형태로 반환                                  |
| String getHostAddress()      | 호스트의 IP주소를 점으로 구분하는 10진수 형태                |
| String getHostName()         | 호스트의 도메인명을 문자열로 반환                            |
| boolean isMulticastAddress() | 주소가 멀티캐스트 주소인지 확인. 멀티캐스트 주소일 경우 true |
| String toString()            | IP 주소를 문자열 형태로 반환                                 |



nslookup 동작 순서

1. InetAddress 객체를 이용한 NSLookup 애플리케이션 작성
2. NSLookup 애플리케이션을 컴파일
3. 도메인 주소를 인자로 지정해서 실행
4. IP 주소를 인자로 지정해서 실행

```java
class NSLookup {

    public static void main(String[] args) throws UnknownHostException {

        InetAddress inetAddress[] = null;
        inetAddress = InetAddress.getAllByName("ceo.baemin.com");
        for (InetAddress address : inetAddress) {
            System.out.println(address.getHostName());
            System.out.println(address.getHostAddress());
            System.out.println(address);
            System.out.println("--------------------");
        }

    }
}
```

```java
package org.example.len;

import java.net.InetAddress;
import java.net.UnknownHostException;

class NSLookupLocal {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress inetAddr = null;

        inetAddr = InetAddress.getLocalHost();

        System.out.println(inetAddr.getHostName());
        System.out.println(inetAddr.getHostAddress());

        System.out.println("Byte[] 형식의 ip 주소 값을 출력");
        byte[] ip = inetAddr.getAddress();
        for (byte b : ip) {
            
            System.out.print(b);
        }
    }
}
```



