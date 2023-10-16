자바 IO 가 무엇이지?

자바 입출력 프로그래밍을 말한다. 이 때 키보드나, 네트워크 파일 등으로부터 받을 수 있다.
 출력도 마찬가지~

자바IO 를 이해하기 위해서는 상속에 대한 문법을 확실하게 이해해야 한다. 왜?

### 재사용되기 위해서 설계된 자바IO

왜 재사용일까?

상속이라는 것이 왜 재사용과 맞닿아 있는가?

```java
class ChildTest {

    class Parent2 {
        int i = 7;
        public int get() {
            return i;
        }
    }

    class Child2 extends Parent2 {
        int i = 5;

        public int get() {
            return i;
        }
    }

    public static void main(String[] args) {
        ChildTest childTest = new ChildTest();
        childTest.execute();


    }

    private void execute() {
        Parent2 p = new Parent2();
        System.out.println("--1--");
        System.out.println(p.i);
        System.out.println(p.get());

        Child2 c = new Child2();
        System.out.println("--2--");
        System.out.println(c.i);
        System.out.println(c.get());

        Parent2 p2 = new Child2();
        System.out.println("--3--");
        System.out.println(p2.i);
        System.out.println(p2.get());

        System.out.println("--4--");
        print(c);
        print(p2);
    }

    public static void print(Parent2 p) {
        System.out.println(p.i);
        System.out.println(p.get());
    }
}
```

이게 왜 중요 할까?

뒤에서 배울 자바 IO 관련 클래스를 배울 때 이것을 알고 사용하는 것과 모르고 사용하는 것이 다르다.

**재사용되기 위해서 설계된 자바IO 라고 한다.**

상속을 사용하는 경우에 상위 객체의 데이터를 가져올 수 있다는 사실을 알아야 한다.



상속은 재사용을 위해서 사용될 수 있는가?



여튼 왜 위 예제를 알아야하는지 다음 챕터를 보면 이해할 수 있는데, 지켜보자.



### 특수한 IO객체

| 객체                      | 구분 | 내용           |
| ------------------------- | ---- | -------------- |
| public static InputStream | in   | 표준 입력      |
| public static PrintStream | out  | 표준 출력      |
| public static PrintStream | err  | 표준 오류 출력 |

자바IO 에서 사용되는 객체들은 java.io 패키지에 존재. 그런데 왜 표준 입력과 표준 출력에 대한 처리를 System 클래스에서 할까?



System 클래스는 시스템에 따라서 사용할 수 없는 비종속적인 메소드를 가지고 있도록 설계됨. 왜냐면 **모든 컴퓨터가 표준 출력 장치를 가지고 있는 것은 아니기 때문에 System 클래스에 표준 입력과 표준 출력에 대한 필드를 가지게 한 것**



### 자바 IO란?

시작은 어떻게?

자바 IO를 구현하려면 기본적으로 java.io 패키지에 존재하는 클래스를 사용할 줄 알아야 하는데, 해당 클래스에 대한 사용법을 배우는 것이 자바IO에 대한 공부



자바IO 패키지에는 어떤 클래스가 존재할까?

읽고 쓸 수 있는 클래스로는 크게 두 가지로 나눠지는데, 

- 바이트 단위로 읽고 쓸 수 있는 '바이트 스트림 클래스'
- 문자 단위로 읽고 쓸 수 있는 '문자 스트림 클래스'



처음에는 바이트 단위로 읽고 쓸 수 있는 바이트 스트림 클래스만 존재했는데, 2바이트 문화권이 생기면서 문자 스트림 클래스가 추후에 추가됨. 이는 한글을 입출력하는 프로그램의 경우, 문자 스트림 클래스를 사용하는 것이 유리하다.



1. 바이트기반(8bits)의 데이터 처리 : InputStream, OutputStream

자바는 입출력을 위해서 스트림(stream)을 사용한다. 스트림이란 데이터의 흐름을 말하는 추상적인 개념이다.

2. 캐릭터 기반(유니코드)의 문자열 데이터 처리 : Reader, Writer

![img](https://raw.githubusercontent.com/LenKIM/images/master/2023-10-17/223F6E3D56103655110058.png)





| 클래스에서 사용된 단어           | 의미                                                         |
| -------------------------------- | ------------------------------------------------------------ |
| Stream 으로 끝나는 클래스        | 바이트 단위 IO 클래스                                        |
| InputStream 으로 끝나는 클래스   | 바이트 단위로 입력받는 클래스다                              |
| OutputStream  으로 끝나는 클래스 | 바이트 단위로 출력하는 클래스                                |
| Reader로 끝나는 클래스           | 문자 단위로 입력받는 클래스                                  |
| Writer로 끝나는 클래스           | 문자 단위로 출력하는 클래스                                  |
| File                             | 파일로부터 입/출력                                           |
| ByteArray로 시작하는 경우        | 입력 클래스의 경우, 바이트 배열로부터 읽어 들이고 <br />출력 클래스의 경우, 클래스 내부의 자료구조에 출력한 후 출력된 결과를 바이트 배열로 반환하는 기술 |
| CharArray로 시작하는 경우        | 입력 클래스의 경우, 바이트 배열로부터 읽어 들이고 <br />출력 클래스의 경우, 클래스 내부의 자료구조에 출력한 후 출력된 결과를 char 배열로 반환하는 기술 |
| Filter로 시작할 경우             | Filter로 시작하는 IO클래스는 직접 사용하는 것보다는 상속을 받아 사용하며, 사용자가 원하는 내용만 필터링할 목적으로 사용된다. |
| Data                             | 다양한 데이터 형식을 입출력할 목적                           |
| Buffered로 시작하는 경우         | 프로그램에서 Buffer 라는 말은 메모리를 의미. 입출력 시에 병목현상을 줄이고 싶을 경우 사용 |
| RandomAccessFile                 | 입력이나 출력을 모두 할 수 있는 클래스로써, 파일에서 임의 위치의 내용을 읽거나 쓸 수 있는 기능을 제공 |





### 생성자가 중요한 자바 IO 관련 클래스

자바 IO 패키지를 잘 사용하려면

1. 앞서 설명한 클래스명을 구성하는 단어들의 의미를 잘 알고 있어야 한다.
2. 자바 IO 패키지 클래스의 생성자 의미를 잘 알고 있어야 한다. 자바IO와 관련된 클래스의 생성자는 인자로 읽어 들일 대상, 혹은 출력해야할 대상을 지정하는 데 사용하기 때문



예를 들어, 파일 IO 클래스는 파일명이나 파일 관련 클래스를 인자로 받아들여, 해당 파일로부터 읽거나 쓰는 동작을 하게 된다.

즉, "어디서로부터 읽어 들일 것인가?" 또는 "어떤 곳에 출력할 것인가?" 를 잘 하려면 생성자를 잘봐야 한다.



### 자바IO 프로그래밍을 잘하려면



예를 들어, "키보드로부터 한 줄씩 입력 받아 화면에 한 줄씩 출력하시오" 라는 예시가 있다면

일단 "한 줄씩 입력 받는다"라는 문제를 힌트로 java.io 를 찾아보면 세 가지를 알 수 있다.

1. BuffedReader.readLine
2. System.in
3. System.out

재료 준비를 완료하고, 객체를 메모리에 올린 후 사용할 수 있게 한다.



BufferedReader br = new BufferedReader(Reader 객체나 Reader의 자손);

Reader 가 필요하다는 사실을 알 수 있다. 앞서서 생성자의 역할은 읽어 들이거나 쓰기 위한 대상을 지정해주는 것이라고 했으므로, 키보드로부터 읽어 들어야할 것이다.

즉, BufferedReader 의 생성자에는 키보드에 대한 객체가 지정되어야 한다. 그럼 Reader 의 어떤 객체를 써야 하는가? 바로 InputStreamReader.

```java
InputStreamReader isr = new InputStreamReader(System.in);
BufferedReader br = new BufferedReader(isr);
```



문장이 의미하는 것. InputStreamReader 는 System.in 으로부터 읽어 들이고, BufferedReader 는 InputStreamReader로부터 읽어 들인다는 것을 의미.

![image-20231017003745337](https://raw.githubusercontent.com/LenKIM/images/master/2023-10-17/image-20231017003745337.png)



