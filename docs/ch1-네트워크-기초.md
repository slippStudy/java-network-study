```
1장. 네트워크 기초
  현장 포인트
  01. 네트워크와 네트워킹
  02. 네트워크 통신 방법
  03. OSI 7계층
  04. 인터넷 프로토콜
  05. 인터넷 애플리케이션 프로토콜
  06. TCP/IP 스택
  07. 소켓과 포트
  08. 보안
  생각해볼 문제

2장. 스레드
  현장 포인트
  01. 스레드란
  02. 스레드의 생성과 시작
  03. 스레드의 종료
  04. 데몬스레드와 join()
  05. 스레드 그룹
  06. 스레드 우선순위
  07. 멀티스레드와 동기화
  08. 생성자-소비자 패턴
  생각해볼 문제
```





**네트워크는 무엇인가?**

네트워크 케이블이나 전화선, 무선 링크 등으로 연결되어 **동일한 프로토콜을 사용하는 디바이스들의 집합**

여기서 디바이스란? 네트워크에 연결해서 어떤 서비스를 이용하거나 제공할 수 있는 것들을 총칭하는 것.



**네트워킹?**

네트워크에 연결된 디바이스들 간의 전송



네트워크는 어떻게 통신?

[헤더 | 바디(데이터: 11001100)]

--

### 기초

OSI 7 계층

물데네전세표응

**ICMP(Internet Control Message Protocal)**

- IP에는 오류처리, 보고 메커니즘이 없다. 그래서 그런 기능이 추가된 프로토콜이 ICMP다.

**IP(Internet Protocol)**

- 네트워크 계층의 프로토콜로 TCP와 함께 인터넷 구조에서 널리 사용.
- 신뢰성 없는 프로토콜
- 상위계층에서 책임

**TCP(Transmission Control Protocal)**

- 신뢰성 있는 프로토콜, 즉 전송할 데이터가 안전하게 전달되는 것을 보장



TCP/IP 에 대한 이해가 되었는지?

UDP와 차이점은 무엇인지 이해했는가?



--

### 애플리케이션 계층 프로토콜

- Telnet
- FTP(File Transfer Protocal)
- POP3(Post Office Protocal Version3)
- IMAP(Internet Message Access Protocol)
    - POP3 와 차이점? IMAP 은 사용자가 메일의 제목과 송신자만을 보고, 실제로 메일을 내려받을 것인지를 결정할 수 있다는 점
- SMTP(Simple Mail Transfer Protocal)
- HTTP(HyperText Transfer Protocal)
- Finger
- NNTP(Network News Transport Protocal)



HTTP 1.1 과 HTTP2 차이점은 무엇인지?

--

### TCP/IP 스택

- OSI 7 계층에 의해 분해되는 것

### 소켓과 포트

- 예약된 포트에 의해 데이터를 주고 받는다.

### 보안