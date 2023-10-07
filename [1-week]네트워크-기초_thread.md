## 목차

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

---

# 2. Thread

무엇을 배우는지?

- 스레드의 기본적인 개념에서 재사용성과 확장성을 고려한 바람직한 스레드 구현 방법
- 효과적인 종료 기법
- 데몬 스레드와 스레드 그룹, 스레드 우선순위에 대해서도 간략하게 알아보고 멀티스레드를 사용할 때의 동기화 문제점
- 자바에서의 해결책





## 2.1. 스레드란?

Process

- 자기 자신만의 주소 공간을 갖는 독립적인 실행 프로그램

스레드

- 프로세스 내의 독립적인 순차흐름 또는 제어
- 경량 프로세스



**어떤 경우에 스레드를 사용해야 될까? 그리고 이점은 무엇이 있는가?**

- 기차표를 사기 위한 매표 창구가 하나만 있는 것과 여러개 있는 것의 차이?

- 그럼 창구가 하나 더 늘기 때문에 컴퓨터의 자원을 좀더 소모하게 된다.

스레드는 동시에 실행될 수 있는 실행흐름을 갖게 함으로써 좀더 효율적인 작업을 하게 해주며 이렇게 병렬로 처리할 수 있어서 서버 프로그램 등 대부분의 경우, 애플리케이션의 성능과 효율을 향상시켜준다는 장점이 있다. 그러나, **이미지 프로세싱처럼 CPU 사용률이 높고 오랜 시간 걸리는 작업에 멀티스레드를 사용하면 오히러 성능이 저하된다.**

❓ 또 어떤 경우일까?

❓ CPU 코어의 갯수의 의미는 무엇인지?



## 2.2 스레드의 생성과 시작

스레드의 생명주기를 살펴보면, Thread 인스턴스를 생성하고 start()메소드로 Thread인스턴스를 실행

![img](https://mblogthumb-phinf.pstatic.net/20120511_177/ljhjjang0125_1336732071717F7mS2_JPEG/%C0%DA%B9%D9_%BD%BA%B7%B9%B5%E5%BB%F3%C5%C2_%BB%FD%B8%ED%C1%D6%B1%E2.jpg?type=w2)



https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html

![image-20231007160204560](https://raw.githubusercontent.com/LenKIM/images/master/2023-10-07/image-20231007160204560.png)

조합은 target, name, group, stacksize 속성들의 조합으로 이뤄짐.

각각의 필드의 의미는 어떻게 될까?

stackSize의 경우, 해당 스레드의 스택사이즈 값 지정한다.

❓VM을 통해서 스레드의 StackSize 는 어떻게 설정하는거지?



1. white-box

   - 상속을 이용하는 것. 자바에서 이미 구현되어 있는 Thread 클래스를 상속학 비즈니스 로직이 들어갈 run() 메소드만 오버라이딩해서 재정의한다.

   - ```java
     public class Main {
         public static void main(String[] args) throws InterruptedException {
             ExtendThread extendThread = new ExtendThread();
             extendThread.run();
         }
         
         static class ExtendThread extends Thread {
             @Override
             public void run() {
                 System.out.println("Hello World");
             }
         }
     }
     
     ```

2. black-box

   - 합성을 이용하는 것. Runnable 인터페이스를 구현하는 클래스를 만들고 새로운 Thread 클래스를 생성할 때, 앞서 구현한 클래스를 파라미터로 넘기는 방식.

   ```java
   public static void main(String[] args) throws InterruptedException {
   //        RunnableThread runnableThread = new RunnableThread();
   //        runnableThread.run();
   
           Thread t = new Thread(new RunnableThread());
           t.start();
       }
   
       static class RunnableThread implements Runnable {
           @Override
           public void run() {
               System.out.println("Runnable > Hello World");
           }
       }
   ```



3. 상속 vs 합성

저자는 상속이 나쁘다고 말하고 있다.

상속을 통한 재사용을 white-box reuse

합성을 통한 재사용을 black-box reuse



상속을 사용할 경우

- private 가 아닌 모든 변수,메소드,생성자가 하위클래스에 노출된다. 그래서 White-box
- 그러나 장점으로는 수퍼클래스의 구현을 손쉽게 재정할 수 있다.

상속을 좋아하지 않는 이유 3가지

1. 캡슐화를 위배
2. 하위클래스가 수퍼클래스의 구현에 종속
3. 수퍼클래스 구현이 변경되어야 할 경우가 생기면 하위클래스도 변경해야 하는 문제점 발생



합성을 사용할 경우

객체가 다른 객체의 참조자를 얻는 방식으로 런타임 시에 동적으로 이뤄짐. 또한 해당 객체의 인터페이스만을 바라보게 됨으로써 캡슐화가 잘 이뤄질 수 있다. 즉, 해당 객체에서 공개하는 것들만 이용할 수 있다.



합성에도 주의해야될 점 있는데,

1. 객체 간의 관계가 수직관계가 아닌 수평 관계가 된다. 그러므로, 큰 시스템에서 많은 부분에 걸쳐 합성이 사용될 때 객체나 메소드명이 명확하지 않으면 코드의 가독성이 떨어지고 이해하기 어려워진다.
2. 따라서 합성을 사용할 때에는 그 용도에 따라 클래스들을 패키지로 적절하게 분리해야 하고 각각의 사용 용도가 명확하게 드러나도록 인터페이스를 잘 설계해야 한다.

## 2.3 스레드의 종료

 스레드를 시작시킨 이후에 그 스레드를 중간에 중지시키고 싶으면 어떻게 할 수 있을까?



현재는 크게 두 가지 방식으로 구현가능하다.

1. 플래그를 사용하는 것

```java
public class Main {

    public static void main(String[] args) {

        Main stopThread = new Main();
        stopThread.process();
    }

    public void process() {
        // StopThread 인스턴스를 생성한 후 이 인자를 파라미터로 받는 스레드 인스턴스를 생성한 후에 출발
        StopThread st = new StopThread();
        Thread thread = new Thread(st);
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        st.stop();
    }

    class StopThread implements Runnable {
        private boolean stoped = false;

        @Override
        public void run() {
            while (!stoped) {
                System.out.println("Thread is alive ... ");
                    // 0.5초 간 멈춘다.
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                System.out.println("Thread is dead...");
            }
        }
        public void stop() {
            stoped = true;
        }
    }
}
//Result
> Task :Main.main()
Thread is alive ... 
Thread is alive ... 
Thread is dead...
```

 플래그 방식에는 문제가 있는데, 만약 특정 로직에서 무한 루프를 돌거나 조건 루프를 도는 시간이 오래 걸리는 작업을 한다면 stopped 플래그를 검사할 수 없다는 것. 그러므로, run() 메소드 안의 처리 로직 시간이 길다면 stopped 플래그를 자주 검사할 수 있도록 중간중간에 체크문을 적절히 삽입



2. interrupt() 메소드를 사용하는 것. interrupt() 메소드는 현재 수행하고 있는 명령을 바로 중지.

만약 interrupt() 메소드를 호출하는 시점에 Object 클래스의 wait(), wait(long), wait(long, int) 메소드나 Thread 클래스의 join(), join(long), join(long, in), sleep(long) 메소드가 호출된 경우 InterruptedException 발생

 ❓참고할 것 - 어떻게 예외를 처리할 것인가

```java
package org.example;

public class AdvancedStopThread implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        AdvancedStopThread ast = new AdvancedStopThread();
        Thread thread = new Thread(ast);
        thread.start();
        
        Thread.sleep(1000);

        thread.interrupt();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Thread is alive..");
                // 0.5초 멈춘다
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {

        } finally {
            System.out.println("Thread is dead...");
        }
    }
}
```

 interrupt() 메소드를 호출한 즉시 스레드를 중지시키고 finally 문에서 안전하게 마무리 작업까지 할수 있다.



## 2.4 데몬스레드와 join()

```java
class NormalThread {

    public static void main(String[] args) {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("MyThread 종료");
                } catch (InterruptedException e) {

                }
            }
        };
        t.start();

        System.out.println("Main() 종료");
    }
}

> Task :NormalThread.main()
Main() 종료
MyThread 종료
```

자바는 모든 스레드가 종료되어야만 JVM이 종료된다. 위 결과값을 보면 알 수 있다. Main 이 종료되었지만, MyThread 가 종료되지 않아 멈추지 않았다.



상황에 따라 분리된 스레드로 백그라운드 작업을 해야 하는 경우도 있다. JVM 안의 가비지 컬렉션과 같은 작업이 대표적인 예시.

 자바 애플리케이션에서 이런 백그라운드 작업이 일반 스레드로 설정되어 있다면 전원이 종료되거나 사용자가 강제로 종료하지 않는 한 애플리케이션은 영원히 정지하지 않을 것.



그러나, 자바에서는 이런 백그라운드 서비스를 위해 **데몬스레드**라는 개념을 도입

"애플리케이션 내부의 모든 스레드가 종료되지 않으면 JVM이 종료되지 않는다"는 조건에서 예외가 되는 것이다.

```java
class DaemonThread {

    public static void main(String[] args) {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    System.out.println("MyThread 종료");
                } catch (InterruptedException e) {

                }
            }
        };
        // 데몬스레드 설정
        t.setDaemon(true);
        t.start();

        System.out.println("Main() 종료");
    }
}

> Task :DaemonThread.main()
Main() 종료
```



  그렇다면 main 스레드가 생성해서 실행시킨 스레드가 종료될 때까지, main 스레드가 기다려야 하는 상황이라면 어떻게 해야 할까? Thread 클래스에서는 이런 경우 사용할 수 있도록 join() 메소드 제공

```java
class ThreadJoin {

    public static void main(String[] args) {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("MyThread 종료");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }
            }
        };
        // 데몬스레드 설정
        t.start();

        try {
            t.join();
          // join 은 InterruptedException을 발생시킬 수 있다.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main() 종료");
    }
}

> Task :ThreadJoin.main()
MyThread 종료
Main() 종료
```

## 2.5 스레드 그룹

스레드를 그룹으로 만든다? 왜 만들지? 그룹으로 묶어서 관리하는 편이 좀더 편리하고 강력할 수 있으니까.

그러나, 자바 라이브러리 중에서 실패한 것이라 한다. 왜? 

- 그룹 안의 스레드를 자유롭게 제어하기 위한 지원 메소드가 부족하기 때문에

자바에서 가장 최상위 그룹은 Main. main 스레드 그룹 인스턴스는 main 스레드를 생성해서 애플리케이션 실행 포인트인 main() 메소드를 호출.

<img src="https://raw.githubusercontent.com/LenKIM/images/master/2023-10-07/image-20231007172711178.png" alt="image-20231007172711178" style="zoom:50%;" />

ThreadGroup(String name)
ThreadGroup(ThreadGroup parent, String name)

```java
class ThreadGroupTest {

    public static void main(String[] args) {
        System.out.println("ThreadGroupTest: " + Thread.currentThread());

        ThreadGroup tg1 = new ThreadGroup(
                Thread.currentThread().getThreadGroup(), "ThreadGroup1"
        );

        ThreadGroup tg2 = new ThreadGroup("ThreadGroup2");
        ThreadGroup tg3 = new ThreadGroup(tg1, "ThreadGroup3");

        Thread t1 = new Thread(tg1, "Thread-1");
        Thread t2 = new Thread(tg2, "Thread-2");
        Thread t3 = new Thread(tg3, "Thread-3");

        System.out.println("    t1: " + t1);
        System.out.println("    t2: " + t2);
        System.out.println("    t3: " + t3);
        System.out.println(
                "main 스레드 그룹:" + Thread.currentThread().getThreadGroup() + ", 활동 중인 스레드 개수:" + Thread.currentThread().getThreadGroup().activeCount() + ", 활동중인 스레드 그룹 개수:" + Thread.currentThread().getThreadGroup().activeGroupCount()
        );

        Thread.currentThread().getThreadGroup().list();

    }
}

> Task :ThreadGroupTest.main()
ThreadGroupTest: Thread[main,5,main]
    t1: Thread[Thread-1,5,ThreadGroup1]
    t2: Thread[Thread-2,5,ThreadGroup2]
    t3: Thread[Thread-3,5,ThreadGroup3]
main 스레드 그룹:java.lang.ThreadGroup[name=main,maxpri=10], 활동중인 스레드 개수:1, 활동중인 스레드 그룹 개수:3
java.lang.ThreadGroup[name=main,maxpri=10]
    Thread[main,5,main]
    java.lang.ThreadGroup[name=ThreadGroup1,maxpri=10]
        java.lang.ThreadGroup[name=ThreadGroup3,maxpri=10]
    java.lang.ThreadGroup[name=ThreadGroup2,maxpri=10]

```

5는 우선순위. 스레드 그룹 이름



## 2.6 스레드 우선순위

스레드에도 우선순위가 있다.

```
/**
 * The minimum priority that a thread can have.
 */
public static final int MIN_PRIORITY = 1;

/**
 * The default priority that is assigned to a thread.
 */
public static final int NORM_PRIORITY = 5;

/**
 * The maximum priority that a thread can have.
 */
public static final int MAX_PRIORITY = 10;
```

 예를 들어, 하나의 CPU를 가진 머신의 어떤 애플리케이션에서 여러 스레드가 실행되고 있다고 가정해보면, 하이퍼스레딩이 지원되지 않는 CPU 라면 멀티 CPI 머신이 아닌 이상 한 순간에 스레드 하나 밖에 실행할 수 없다. 따라서 JVM은 실행 대기중에 있는 스레드 중에서 우선순위가 높은 스레드에게 먼저 작업할 권한을 준다.

 만약 우선순위가 같은 스레드가 있다면 JVM 구현 방식에 따라 시분할 방식으로 스레드에게 작업 기회를 준다.

```java
public class PriorityThread implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Start main");

        Thread t = new Thread(new PriorityThread());
        t.start();
        Thread.sleep(500);

        t.setPriority(Thread.MIN_PRIORITY);
        Thread.sleep(500);

        t.setPriority(8);
        Thread.sleep(500);

        t.setPriority(10);
        Thread.sleep(500);

        t.interrupt();

        System.out.println("End Main");
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Priority : " + Thread.currentThread().getPriority());
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {

        } finally {
            System.out.println("Thread is dead...");
        }
    }
}

> Task :PriorityThread.main()
Start main
Priority : 5
Priority : 1
Priority : 1
Priority : 8
Disconnected from the target VM, address: 'localhost:50107', transport: 'socket'
Priority : 10
End Main
Thread is dead...
```

스레드 우선순위

- 10: 위기 관리
- 7~9: 상호 작용, 이벤트 처리
- 4~6: IO 관련 작업
- 2~3: 백그라운드 작업
- 1: 기타 다른 작업이 없을 때 실행

## 2.7 멀티스레드와 동기화

흔한 멀티스레드 상황.

 은행의 입출금을 다루는 애플리케이션에서 동기화를 고려하지 않을 경우





