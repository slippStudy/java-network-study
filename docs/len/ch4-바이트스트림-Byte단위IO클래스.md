# 바이트 스트림: 바이트 단위 IO 클래스

왜 바이트 스트림을 어렵게 생각할까? 모든 데이터는 바이트로 구성되어있음에도 불구하고 말이다.

이유는 바이트 스트림 클래스의 사용 원리를 이해하지 못했기 때문이다.



java.io 패키지에서의 클래스는 객체를 재사용하는 좋은 예라고 말할 수 있는데, 그 이유가 클래스를 조합해서 다양한방법으로 사용할 수 있기 때문에~



## File 클래스



> File 클래스 생성자는 파일 클래스에 대한 객체를 자바 힙 메모리에 생성하는 것이지, 실제로 파일 시스템에 파일을 생성하지 않는다. 그리고 File 클래스의 인자로 지정되는 파일명은 실제로 존재하지 않는 파일명일 수도 있다.
>
>  이 경우 File 클래스에 있는 exists() 메소드를 이용해서 실제로 존재하는지의 유무를 확인한다.



주요 메서드

| 메소드             | 설명                                                         |
| ------------------ | ------------------------------------------------------------ |
| boolean canRead()  | 읽기 가능한 파일일 경우에는 true, 그렇지 않으면 false 를 반환 |
| boolean canWrite() | 쓰기 가능한 파일일 경우에는 true, 그렇지 않으면 false        |
|                    |                                                              |
|                    |                                                              |
|                    |                                                              |
|                    |                                                              |
|                    |                                                              |



File클래스는 왜 이야기하고 있는거지?



## 바이트 단위 IO 클래스

컴퓨터에 존재하는 모든 데이터는 바이트 단위로 구성되어 있습니다. 

바이트 스트림 클래스는 모두 추상 클래스(abstreact class)로서 InputStream과 OutputStream의 자식(하위) 클래스입니다.



![Chapter 15 입출력(I/O) | Java Study Group](https://raw.githubusercontent.com/LenKIM/images/master/2023-10-18/file_io.jpg)



앞 장에서 배웠던 상속의 관계에 대해서 주의깊게 고민해야 한다.



## InputStream 과 OutputStream

InputStream 과 OutputStream 는 앞에서 언급한 것과 같이 모든 바이트 스트림의 최상위 클래스이다. 

안방을 설명하기 위해서는 '방'이라는 개념을 알아야 하듯이 FileInputStream 을 알기 위해서는 InputStream 이 무엇인지 알아야 한다.



InputStream 의 주요 메소드는 다음과 같다.

| 메소드                                   | 설명                                                         |
| ---------------------------------------- | ------------------------------------------------------------ |
| int available() throws IOException       | 현재 읽을 수 있는 바이트 수를 반환한다.                      |
| void close() throws IOException          | 입력 스트림을 닫는다.                                        |
| int read() throws IOException            | 입력 스트림에서 한 바이트를 읽어 int 값으로 반환. 더 이상 읽어 들여야 할 내용이 없을 경우, -1을 반환 |
| int read(byte buf[])  throws IOException | 입력 스트림에서 buf[] 크기만큼을 읽어 buf에 저장하고, 읽은 바이트 수를 반환. 더 이상 읽어 들어야할 내용이 없을 경우, -1 |
| int skip(long numBytes)                  | numBytes로 지정된 바이트를 무시하고, 무시된 바이트 수를 반환 |



OutputStream 의 주요 메소드는 다음과 같다

| 메소드                                                       | 설명                                              |
| ------------------------------------------------------------ | ------------------------------------------------- |
| void close() throws IOException                              | 출력 스트림을 닫는다.                             |
| void flush() throws IOException                              | 버퍼에 남은 출력 스트림을 출력한다.               |
| void write(int i) throws IOException                         | 정수 i의 하위 8비트를 출력한다.                   |
| void write(byte buf[]) throws IOException                    | buf 배열의 내용을 출력한다.                       |
| void write(byte buf[], int index, int size) throws IOException | buf 배열의 index 위치부터 size 만큼의 바이트 출력 |



### System.in 을 이용해서 키보드로부터 입력받기

InputStream 형식



- FileInputStream 과 FileOutputStream 은 대상 파일로부터 바이트 단위로 출력할 수 있는 클래스

**파일을 읽어 출력하는 프로그램의 개선을 위해서는 Buffer 를 사용한다. 어떻게?**

- DataInputStream 과 DataOutputStream 은 자바의 기본형 데이터인 int, float, double, boolean, short, byte 등의 정보를 입력하고 출력하는 데 알맞는 클래스다.

  ```java
  class DataOutputStreamTest {
  
      public static void main(String[] args) {
          FileOutputStream fos = null;
          DataOutputStream dos = null;
  
          try {
              fos = new FileOutputStream("data.txt");
              dos = new DataOutputStream(fos);
              dos.writeBoolean(true);
              dos.writeByte((byte)5);
              dos.writeInt(100);
              dos.writeDouble(200.5);
              dos.writeUTF("hello world");
              System.out.println("저장했습니다.");
          } catch (IOException e) {
              System.out.println(e);
          } finally {
              try {
                  fos.close();
              } catch (IOException ignored) {
  
              }
              try {
                  dos.close();
              } catch (IOException ignored) {
  
              }
          }
      }
  }
  ```

  핵심은

  ```java
  	FileOutputStream fos = null;
    DataOutputStream dos = null;
  	try {
          fos = new FileOutputStream("data.txt");
          dos = new DataOutputStream(fos);
  		}
  ```

  FileOutputStream 으로 어디에 저장하겠다고 명시한 다음, DataOutputSTream 의 생성자로 넣어줬다는 것은 dos 를 통해 데이터를 출력하는데, 이때 fos 를 이용하겠다는 것이다.

  ```java
  class DataInputStreamTest {
  
      public static void main(String[] args) throws IOException {
          FileInputStream fis = null;
          DataInputStream dis = null;
  
          try {
              fis = new FileInputStream("data.bin");
              dis = new DataInputStream(fis);
  
              boolean b = dis.readBoolean();
              byte b1 = dis.readByte();
              int i = dis.readInt();
              double d = dis.readDouble();
              String s = dis.readUTF();
              System.out.println("boolean : " + b);
              System.out.println("byte : " + b1);
              System.out.println("int : " + i);
              System.out.println("double : " + d);
              System.out.println("String : " + s);
  
          } catch (IOException e) {
              System.out.println(e);
          } finally {
              fis.close();
              dis.close();
          }
      }
  }
  ```

  마찬가지.

- ByteArrayInputStream과 ByteArrayOutputStream 은 말 그대로 바이트 배열을 차례대로 읽어 들이기 위한 클래스

  - ByteArrayOutputStream은 내부적으로 저장 공간을 가지고 있어, 메서드를 이용해서 출력하게 되면, 출력되는 모든 내용들이 내부적인 저장 공간에 쌓이게 된다.

  

  ```java
  package org.example.ch4;
  
  import java.io.ByteArrayInputStream;
  import java.io.ByteArrayOutputStream;
  import java.io.FileInputStream;
  import java.io.IOException;
  
  class ByteArrayInputOutputTest {
  
      public static void main(String[] args) throws IOException {
          FileInputStream fis = null;
          ByteArrayInputStream bais = null;
          ByteArrayOutputStream baos = null;
  
          fis = new FileInputStream("data.txt");
          baos = new ByteArrayOutputStream();
          byte[] buffer = new byte[512];
          int readCount = 0;
          // 파일로부터 읽어 들인 바이트 배열을 ByteArrayOutputStream 에 쓴다
          while ((readCount = fis.read(buffer)) != -1) {
              baos.write(buffer, 0, readCount);
              System.out.println("buffed end");
          }
  
          byte[] fileArray = baos.toByteArray();
          System.out.println("파일의 내용을 모두 읽어 들여 byte[]로 만들었다");
          System.out.println("읽어 들인 byte 수: " + fileArray.length);
  
          // InputStream 생성
          bais = new ByteArrayInputStream(fileArray);
          // ByteArrayInputStream 통해 읽어 들인 byte 배열을 모니터 출력
          while ((readCount = bais.read(buffer)) != -1) {
              System.out.write(buffer, 0 , readCount);
          }
          System.out.println("\n\n");
          System.out.println("모두 출력");
  
      }
  }
  
  ```

  ```java
  package org.example.ch4;
  
  import java.io.*;
  
  class FileArrayInputStreamTest {
  
      public static void print(InputStream in) throws IOException {
          byte[] buffer = new byte[512];
          int readCount = 0;
  
          while ((readCount = in.read(buffer)) != -1) {
              System.out.write(buffer, 0, readCount);
          }
      }
      public static void main(String[] args) throws IOException {
          FileInputStream fis = null;
          fis = new FileInputStream("data.txt");
          print(fis);
          System.out.println("\n");
          byte[] abc = new byte[26];
          for (int i = 0; i < 26; i++) {
              abc[i] = (byte) ('a' + i);
          }
          ByteArrayInputStream bais = null;
          System.out.println("Array print");
          bais = new ByteArrayInputStream(abc);
          print(bais);
      }
  }
  
  ```

- PipedInputStream과 PipeOutputStream 

  - 이것을 이해하기 위해서는 파이프가 무엇인지 이해해야 한다. 파이프는 기호('|') 를 나타내며, 유닉스에서 `ls | wc` 와 비슷하다.

  



왜 종류별 클래스를 소개했을까?

재사용하기 위해 자바는 입출력 관련 클래스들을 조합해서 사용함으로써 재사용성을 높이고, 사용자 입맛에 맞는 입출력을 수행할 수 있다.

만약 통으로 나왔다면? 예를 들어 FileDataInputStream 이렇게 나왔다면 좀 더 이해하기 쉽겠지만, 클래스가 더 많아졌을 것이다. 



추가적으로 알면 좋은 것?

Files 버전이 올라감에 따라 자바IO 추가된 내역이 있을까?