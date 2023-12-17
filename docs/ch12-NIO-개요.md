# Chapter 12: NIO 개요

## 개요

- JDK 1.3 이전까지의 Java는 다양한 개발 환경을 수용할 수 있도록 라이브러리를 폭 넓게 늘려가는 데 중점을 두었다
- JDK 1.3 이전과 이후에 변화한 가장 큰 틀은 `성능` 과 `개발 편의성 향상` 이라고 할 수 있다
- JDK 1.4에는 `OS 수준의 native 기능을 적극적으로 활용하는 NIO가 도입되어` 개발자들로부터 각광을 받았는데, 그 이유는 기존의 Java IO가 blocking IO라서 C나 C++의 IO에 비해 느렸기 때문이다
- 이번 장에서는 `Non-blocking IO`를 지원하면서 기존 Java IO의 단점을 보완한 `New IO(NIO)` 패키지에 대해 알아본다

## 1. Java IO는 느리다?

- Java는 C나 C++처럼 포인터로 직접 메모리를 관리하고, OS 수준의 system call을 직접 사용할 수 있는 저수준 언어보다 JVM이라는 하나의 process 위에서 동작하는 `추상화된 고수준 언어` 이기 때문에 느린 것은 당연하다
- 하지만 대부부분의 경우 C나 C++ 과 그리 큰 속도 차이가 나지는 않는데, 속도 차이가 크게 나는 부분이 IO다
  - Java IO는 저수준의 타 언어와 비교해서 엄청나게 느리다!
- 새로운 NIO 패키지를 이해하기에 앞서 기존 Java IO가 느린 이유를 알아보자

## 2. Blocking Java IO

<p align="center" width="100%">
  <img width="500" alt="IO를 처리하는 전체 구조" src="https://github.com/slippStudy/java-network-study/assets/53922851/6d3c0063-38e8-49d5-b180-3b7628df6237">
</p>

### 유저 영역과 커널 영역

- 유저 영역
  - 일반적인 프로세스들이 존재하는 `제한된 권한`을 갖는 영역
  - 유저 영역의 프로세스들은 하드웨어 장치나 다른 프로세스에 직접적으로 접근할 수 없다
- 커널 영역
  - 운영체제에 존재하는 영역으로서 하드웨어 장치에 직접 접근하고, 다른 프로세스를 제어할 수 있는 권한이 있다

### JVM과 커널 영역

- 위 그림에서 알 수 있듯이, 모든 IO는 커널 영역을 직간접적으로 거쳐야한다
- 그림에 있는 프로세스가 JVM이라고 가정할 때 Java에서 파일 읽기를 시도하면 아래와 같이 동작한다
    1. 커널에 명령을 전달하고
    2. 커널은 system call (`read()`)  을 사용하여 디스크 컨트롤러가 물리 디스크로부터 읽어온 파일 데이터를 커널 영역안의 버퍼에 저장한다
    3. 모든 파일 데이터가 커널 안의 버퍼로 복사되면, JVM (프로세스) 안의 버퍼로 복사를 시작한다

### 비효율적인 Java IO

- 커널 영역 버퍼에서 프로세스 영역 안의 버퍼로 데이터를 복사하는 점
  - 커널 영역 → 프로세스 영역 버퍼로의 데이터 전달은 CPU가 관여해야 한다
  - 만약 커널 영역 버퍼에 저장된 데이터를 직접 사용한다면
        1. 복사하는 시간을 단축할 수 있고,
        2. 복사 대상의 GC도 필요 없고,
        3. CPU 자원도 효율적으로 사용할 수 있다
- 디스크 컨트롤러에서 커널 영역의 버퍼로 데이터를 복사하는 동안 프로세스 영역은 `블로킹` 되는 점
  - OS는 효율을 높이기 위해 최대한 많은 양의 데이터를 커널 영역 버퍼에 저장하고 프로세스 영역의 버퍼로 전달한다
  - 즉, 디스크의 파일 데이터를 커널 영역 안의 버퍼로 모두 복사할 때까지 파일 읽기를 요청한 Java thread는 블로킹 된다

위의 문제점으로 인해 system call을 이용해 직접 IO 작업을 수행하는 C나 C++ 과 같은 저수준 언어에 비해 Java IO는 느리다

## 3. IO 향상을 위한 운영체제 수준의 기술

대다수의 OS들은 IO 의 향상을 위해 아래와 같은 기능들을 제공한다

<aside>
✅ NIO는 아래 내용의 대부분을 이용함으로써 기존 IO에 비해 많은 성능 향상을 이루었다

</aside>

### 3-1. Buffer

- Buffer는 효율적으로 데이터를 전달하기 위한 객체다
- 데이터를 한 개씩 여러번 전달하는 것 보다, 중간에 Buffer를 두고 그 Buffer에 데이터를 모아 한 번에 전달하는 것이 훨씬 효율적이다

### 3-2. Scatter / Gather

- Java 프로그램 안에 Buffer를 N개 만들어 사용할 때, 만약 동시에 각각의 Buffer에 데이터를 쓰거나 읽으면 system call을 N번 호출해야 한다
  - system call은 가벼운 작업이 아니므로 비효율적이다!
  - 위의 단점을 보완하기 위해 OS 수준에서 지원하는 기술이 Scatter / Gather다
- Scatter / Gather 를 사용하면 system call을 한 번만 호출한다
  - 대신 system call을 호출할 때마다 사용할 Buffer의 주소 목록을 넘겨줌으로써, OS에서는 최적화된 로직을 사용해 주어진 Buffer들로부터 순차적으로 데이터를 읽거나 쓴다
- NIO에 적용
  - NIO에서는 성능 향상을 위해 Scatter / Gather 를 사용하고자 `ScatteringByteChannel` 과 `GatheringByteChannel` interface를 사용한다

    → 뒤에서 배울 예정

### 3-3. Virtual Memory

- 가상 메모리란?
  - 프로그램이 사용할 수 있는 주소 공간을 늘리기 위해 OS에서 지원하는  기술
  - OS는 가상 메모리를 page라는 고정된 크기로 나누고, page는 메모리가 아닌 disk에 먼저 저장된다
  - 실제 프로그램이 실행되는 데 필요한 page의 가상 주소만 물리적 메모리 (RAM) 주소로 바꾸어 실제 메인 메모리에 올려놓는다
- 장점
    1. 실제 물리적 메모리보다 큰 가상 메모리 공간을 사용할 수 있다
    2. 여러 개의 가상 주소가 하나의 물리적 메모리 주소를 참조함으로써 메모리를 효율적으로 사용할 수 있게 한다
- 적용
  - 프로그래밍에서 가상 메모리를 사용하면 유저 영역 Buffer와 커널 영역 Buffer를 매핑시킴으로써 커널 영역에서 유저 영역으로 데이터를 복사하지 않아도 된다
  - 커널 영역 Buffer와 유저 영역 Buffer 모두 동일한 물리적 메모리를 참조하는 가상 메모리를 사용한다!
    - 즉, 커널 영역에 저장하는 것이 곧 유저 영역 Buffer에 저장하는 것이 된다
  - 둘 다 물리적으로 같은 메모리를 참조하고 있기 때문에, Java IO가 느린 문제를 해결해준다

### 3-4. Memory-mapped IO

- IO를 다루는 프로그램을 만들면 매번 값비싼 system call이 일어나고, (불필요한) 커널 영역과 유저 영역간의 복사가 이루어져서 garbage가 많이 생긴다
  - 즉, GC가 빈번히 호출되는데, GC가 일어나는 것은 느린 작업이다
  - 이로 인해 파일의 크기가 커질수록 성능이 나빠진다
- 이런 문제를 해결하기 위해 OS에서는 `Memory-mapped IO` 를 지원한다
  - Memory-mapped IO 는 file system의 page와 유저 영역의 Buffer를 `가상 메모리로 매핑시킨다`
- `Memory-mapped IO` 의 장점
    1. 프로세스가 파일 데이터를 메모리로서 바라보기 때문에 system call(e.g. read(), write())을 할 필요가 없다
        - 파일 데이터를 변경하면 별도의 IO 과정을 거치지 않고 변경된 부분을 물리 디스크에 자동으로 반영하게 되고, 커널 영역 Buffer → 유저 영역 Buffer로 복사도 필요 없다
    2. 큰 파일을 복사하기 위해 많은 양의 메모리를 소비하지 않아도 된다
        - 파일 시스템의 page들을 메모리로서 바라보기 때문에, 그때 그때 필요한 부분만 실제 메모리에 올려놓으면 된다
- NIO에 적용
  - NIO에는 `ByteBuffer` 를 상속하는 `MappedByteBuffer` 라는 클래스가 있는데, 이것이 메모리 냅 파일과 관련하여 사용되는 buffer 다

    → 뒤에서 배운다~

### 3-5. File Lock

- Thread 동기화와 비슷한 개념으로, 하나의 process가 특정 파일에 lock을 획득했을 때 다른 process가 동시에 접근하는 것을 막거나 접근 방식에 제한을 둔다
  - 파일 전체를 잠구는 것이 아닌 일부분만 잠궈 사용함으로써 Lock이 설정되지 않은 파일의 다른 위치에서 여러 process들이 동시에 작업을 할 수 있게 된다
- File Lock의 종류
    1. shared lock
        - 읽기 작업에 사용
        - 읽기 요청에 대해서는 lock을 건네주지만, 읽기 작업이 끝나지 않은 상태에서 데이터를 변경하기 위한 요청이 들어오면 lock을 허용하지 않는다
            - 동기화 문제가 발생하기 때문이다!
    2. exclusive lock
        - 쓰기 작업에 사용
        - 어떤 process에 의해 exclusive lock이 사용중일 때는, 다른 process들이 어떠한 형태의 lock도 얻을 수 없다
- NIO에 적용
  - NIO의 `FIleChannel` 과 `FileLock` 에서 다룰 예정이다

## 4. Java의 새로운 변화

기존 Java IO와 비교해서 NIO 에서 변화하는 것을 알아보자

### 4-1. Java의 Pointer `Buffer` 도입

- 가장 핵심적인 변화는 NIO에서 Buffer class를 도입한 것이다
- NIO에서는 커널에 의해 관리되는 system memory를 직접 사용할 수 있는 Buffer class가 도입되었다 (단, `DirectByteBuffer` 한정!)
  - 해당 class의 구현은 C로 만들어져있고, 추상화 된 Java class인 Buffer를 사용하는 것이다
- Buffer class를 사용하면 기존에 배열로 처리해야 했던 부분을 좀 더 효율적으로 관리할 수 있는 여러 method를 제공한다 (ref. [Oracle Docs - java.nio.Buffer](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/Buffer.html))

- 결론적으로 C/C++의 포인터가 자바에서 생겼다고 볼 수 있다

### 4-2. Native IO 서비스를 제공해주는 `Channel` 도입

- 기존의 단방향 스트림은 읽거나 쓰는 한가지만 가능하게 했다
- NIO에서는 해당 스트림의 향상된 버전이라고 할 수 있는 `Channel` 을 도입했다
- Channel의 기능
  - stream 처럼 읽거나, 쓰거나, 읽고 쓰는 `양방향 통신`까지 세가지 형식이 존재한다
  - OS에서 제공하는 다양한 Native IO 서비스를 이용할 수 있게 해준다
- Channel과 Buffer
  - Channel은 Buffer class와 함께 작업하도록 만들어져 있다
  - 즉, Channel을 이용해서 system memory인 buffer에 직접적으로 데이터를 읽고 쓸 수 있다
  - 또한 Channel은 `Scatter / Gather`를 구현해서 효율적으로 IO를 처리할 수 있다

→ Native IO 서비스를 사용할 수 잇게 해주는 Channel이 도입되고 이로 인해 Buffer class와 함게 작업하는 양방향 통신이 가능해졌다!

### 4-3. `Selector` 도입

- Selector는 네트워크 프로그래밍의 효율을 높이기 위한 것으로, Reactor 패턴의 구현채다
- 기존 Java 네트워크 프로그래밍의 문제점
  - client 하나당 thread 하나를 생성해서 처리해야 해서 사용자가 늘어나면 thread가 늘어나 성능 저하를 가져왔다
  - 구조적으로 많은 thread를 생성해야 해서 메모리를 비효율적으로 사용했다
- NIO에서 개선된 점
  - NIO에서는 Selector를 이용함으로써 한 개의 thread만으로 다수의 사용자를 동시에 처리할 수 있는 서버를 만들게 되었다

이제 다음 장에서 자세히 알아보자~~~
