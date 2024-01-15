# 객체 스트림

객체 스트림을 왜 하는지? 일단 파일로 저장하거나 네트워크로 전송하기 위해서. 객체에 있는 내용을 하나씩 읽어들여 저장하거나 전송.



**자바 RMI의 기반 기술**

// 원격 피시에 자바코드를 실행시키기 위한 목적으로 탄생


물체 전송기 동작 원리

1. 왼쪽의 기계에 사람이 들어가면, 정보를 일렬로 늘어선 값으로 바꾼다. 왜냐하면 작은 선을 통과해야 하기 떄문에, 일렬로 늘어선 것을 **직렬화**라고 한다.
2. 직렬화된 정보를 얇은 선을 통해서 전송
3. 얋은 선을 통해 정보를 받아 들인 후, 원래 사람 형태로 변환. 이때 중요한 것은 좌측에서 전송한 것이 사람이라는 것을 우측에서 알고 있어야 한다. 이것을 **형 변환** 이라 한다.



## ObjectStreamTest

```java
package org.example.ch6;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

class ObjectStreamTest1 {

    public static void main(String[] args) throws IOException {
        FileOutputStream fout = null;

        LinkedList list = new LinkedList<>();
        LinkedList list1 = new LinkedList<>();
        LinkedList list2 = new LinkedList<>();
        LinkedList list3 = new LinkedList<>();

        list.add("data 1");
        list.add("data 2");
        list.add("data 3");
        list1.add("data 4");

        list.add(list2);
        list.add(list3);

        fout = new FileOutputStream("object.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fout);
			// FileOutputStream 가능한데, ObjectOutputStream 왜 사용할까? @xx 없어서
        oos.writeObject(list);
        oos.reset();
        System.out.println("저장되었습니다.");
        
        fout.close();
        oos.close();
    }
}

```

ObjectOutputStream oos 는 reset()을 호출해야 한다. 그렇지 않으면 메모리 릭(Leak)



반대

```java
package org.example.ch6;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

class ObjectStreamTest2 {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        FileInputStream fin = new FileInputStream("object.txt");
        ObjectInputStream ois = new ObjectInputStream(fin);

        LinkedList linkedList = (LinkedList) ois.readObject();
        String data1 = (String) linkedList.get(0);
        String data2 = (String) linkedList.get(1);
        String data3 = (String) linkedList.get(2);
        LinkedList linkedList1 = (LinkedList) linkedList.get(3);
        LinkedList linkedList2 = (LinkedList) linkedList.get(4);

        System.out.println(data1);
        System.out.println(data2);
        System.out.println(data3);

        fin.close();
        ois.close();
    }
}

```



## 객체 스트림

객체를 마샬링 해서 전송한 뒤 언마샬링한다.



**마샬링**

 마샬링이란 데이터를 바이트의 흐름으로 만들어 TCP와 같은 통신 채널을 통해 전송하거나 스트림으로 써줄 수 있는 형태로 바꾸는 과정

- 기본적으로 java.io.Serializable 인터페이스를 구현하고 있는 객체만 가능
- 마샬링은 객체 스트림인 ObjectOutputStream에 의해 제공
- ObjectOutputStream 은 해당 객체가 java.io.Serializable 인터페이스를 구현하고 있는 것을 어떻게 알고 있나? 바로, 특정한 클래스를 상속하거나 인터페이스를 구현했는지 알려면 instanceof 연산자 사용

**전송**

 데이터를 발신지에서 목적지로 전달하는 과정. 전송을 위해서 객체 스트림은 바이트 기반의 표준 스트림을 이용

**언마샬링**

 전송받은 데이터를 원래의 형태로 변환하는 과정. 객체 직렬화와 반대로 이 경우에는 역직렬화(deserialization) 라고 하며, ObjectInputStream 에 의해 제공된다. writeObject() 메소드와 readObject() 메소드를 이용해 객체를 역직렬화한다.



## 두 번째 예제: 나의 책 목록 예시

@java.io.Serializable 을 붙인 것들은 어떤 것들이 있을까?

대부분 붙여있다.



---

## ObjectOutputStream 클래스의 생성자와 메소드

객체를 직렬화하기 위해서 사용한다. `public class ObjetOutputStream extends OutputStream implements ObjectOutput, ObjectStreamConstants`

| 메소드                                                       | 설명                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| public final void writeObject(Object obj) throows IOException | ObjectOutputStream에 있는 가장 중요한 메소드다. 인자로 지정된 객체를 직렬화해서 전송 |
| public void reset() throws IOException                       | 스트림을 방금 전에 생성된 것처럼 초기 상태로 만든다. 네트워크 프로그래밍에서 사용할 때에는 writeObject() 메소드를 호출한 후 반드시 호출해 줘야한다. |
| public void flush() throws IOException                       | 전송해야 할 데이터를 버퍼링함으로써 ObjectInputStream 에서 읽어들이지 못하는 경우가 발생. 이런 문제를 해결하기 위해서 writeObject()메소드를 호출한 후 flush() 메소드를 바로 호출 |

## ObjectInputStream 클래스의 생성자와 메서드

역직렬화하기 위해서 사용

> public class ObjectInputStream extends InputStream implements ObjectInput, ObjectStreamConstants



## 예외

직렬화와 역직렬화할 경우 발생할 수 있는 예외

| 예외                     | 설명                                                         |
| ------------------------ | ------------------------------------------------------------ |
| InvalidClassException    | 객체를 역직렬화할 경우에 전송한 객체에 대한 클래스는 존재하지만 일치하지 않을 경우 발생. 그리고 객체에 대한 버전 관리가 잘못 되었을 경우 발생 |
| NotSerializableException | 객체를 읽거나 쓸 경우 발생. 그리고 객체를 직렬화할 수 없을 경우 발생 |
| StreamCorruptedException | 스트림에 포함된 객체 스트림의 데이터가 유효한 것이 아니거나 헤더 정보가 잘못되었을 경우 발생 |
| InvalidObejctException   | 객체를 역직렬화한 후 검증 과정이 성고적으로 끝나지 못했을 경우 발생 |
| WriteAbortedException    | 객체를 출력하는 과정에서 예외가 발생해서 제대로 출력되지 않는 객체를 다른 한쪽에 읽어 들일 때 발생. 이 경우 객체가 전송될 때 발생한 예외 내용 포함 |

