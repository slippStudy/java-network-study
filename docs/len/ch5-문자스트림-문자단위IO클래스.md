# 문자 단위 IO 클래스

배경은 자바가 처음 세상에 등장 할 때는 포함되지 않았으나,  2바이트 문자를 입출력하는 클래스를 요청하게 되면서 등장.

그럼  바이트 단위와 무엇이 다를까?

일단 InputStream, OutputStream 의 개념은 존재하지만, 여기에 +a 로 Writer, Reader 라는 개념이 추가된다.

InputStreamReader, OutputStreamWriter 라 부른다



문자 단위 IO클래스의 상속도는 다음과 같다.

![Java Tutorials: Reader and Writer classes](https://raw.githubusercontent.com/LenKIM/images/master/2023-10-26/reader+and+writer+class+in+java.jpg)

참고 - https://javadevwannabe.blogspot.com/2012/02/reader-and-writer-classes.html



InputStream과 OutputStream 같이, Reader 와 Writer 라는 기본이 되는 클래스가 있고 당연히 추상 클래스이다.

주요 메소드를 살펴볼 필요가 있다.

| 메소드                                                       | 설명                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| abstreact void close() throws IOException                    | 문자 입력 스트림을 닫는다.                                   |
| void mark(int limit) throws IOException                      | 문자 입력 스트림의 현재 위치를 표시한다.                     |
| int read( ) throws IOException                               | 단일 문자 읽기                                               |
| int read(char buf[ ]) throws IOException                     | 문자 입력 스트림에서 buf[ ] 크기 만큼을 읽어 buf 에 저장     |
| abstract int read(char buf[], int off, int len) throws IOException | 문자 입력 스트림에서 len만큼을 읽어 buf의 off 위치에 저장하고 읽은 문자 수 반환 |
| boolean ready() throws IOException                           | 준비되었는지 확인하기 위해 리턴                              |
| void reset( ) throws IOException                             | 문자 입력 스트림을 표시(mark)된 위치로 되돌림                |
| long skip(long l) throws IOException                         | 주어진 개수 I만큼 문자 건너뜀                                |



Writer 에는 무엇이?

| 메소드                                                       | 설명                           |
| ------------------------------------------------------------ | ------------------------------ |
| abstract void close() throws IOException                     | 문자 출력 스트림을 닫는다.     |
| abstract void flush() throws IOException                     | 버퍼에 남은 출력 스트림을 출력 |
| void writer(String s) throws IOException                     |                                |
| void writer(char buf[ ]) throws IOException                  |                                |
| void writer(char buf[ ], int off, int len) throws IOException |                                |
| void writer(String s, int off, int len) throws IOException   |                                |

Reader r = new FileReader("a.txt");





## 3. InputStreamReader와 OutputStreamWriter

InputStreamReader 는 Reader 를, OutputStreamWriter는 Writer를 상속받는다.

**즉, 문자 단위 입출력에 필요한 클래스.** 각각 InputStream 과 OutputStream 생성자에서 받아들인다.



생성자가 중요한 이유는 생성자에 전달한 인자가 무엇이냐에 따라서 읽어 들여야 할 대상과 써야 할 대상이 달라진다고 했다.

![image-20231026182415021](https://raw.githubusercontent.com/LenKIM/images/master/2023-10-26/image-20231026182415021.png)



![image-20231026182742844](https://raw.githubusercontent.com/LenKIM/images/master/2023-10-26/image-20231026182742844.png)



OutputStreamWriter 와 OutputStream 의 관계를 표현하고 있다. OutputStreamWriter 에 있는 메소드를 이용해서 문자를 출력하게 되면, OutputStreamWriter 는 OutputStream 을 내부적으로 이용해서 써야 할 대상에 바이트 단위로 출력하게 되는 것



**문자 단위로 파일 내용을 읽어 들여 화면 출력**

```java
package org.example.ch5;

import java.io.*;

class StreamReaderTest {

    public static void main(String[] args) throws IOException {

        FileInputStream in = new FileInputStream("data-char.txt");
        InputStreamReader isr = new InputStreamReader(in);
        OutputStreamWriter osw = new OutputStreamWriter(System.out);

        char[] buffer = new char[512];
        int readCount = 0;
        while ((readCount = isr.read(buffer)) != -1) {
            osw.write(buffer, 0 , readCount);
        }

        isr.close();
        osw.close();

    }
}

```



**FileReader와 FileWriter**

FileReader(String filepath) throws FileNotFountException - filepath로 지정한 파일에 대한 FileReader 객체를 생성

FileReader(File fileObj) throws FileNotFountException - fileObj로 지정한 파일에 대한 FileReader 객체 생성



FileWriter(String filepath)

FileWriter(String filepath, boolean append)

FileWriter(String fileObj)



예시

// 썼다 날라감..



5. BufferedReader와 BufferedWriter

 파일 복사 예제에서 FileReader 와 FileWriter 클래스는 버퍼가 없기 때문에 큰 파일으로 복사를 시도할 경우, 병목현상이 발생할 수 있다. 그러므로, 버퍼를 활용해 이 문제를 해결할 수 있다.



6. PrintWriter 

PrintWriter클래스에는 print(), println() 등의 이름을 가진 다양한 출력 메서드가 있다.

| 생성자                                           | 설명                                            |
| ------------------------------------------------ | ----------------------------------------------- |
| PrintWriter(OutputStream out)                    | OutputStream out을 인자로 PrintWriter 객체 생성 |
| PrintWriter(OutputStream out, boolean autoFlush) | a                                               |
| PrintWriter(Writer out)                          | Writer 인자로 받아 PrintWriter 객체 생성        |
| PrintWriter(Writer out, boolean authFlush)       | 자동으로 flush() 메소드를 호출한 효과 발생      |



**CharArrayReader 와 CharArrayWriter**

```java
package org.example.ch5;

import java.io.*;

class CharArrayInputOutputTest {

    public static void main(String[] args) throws IOException {
        FileReader fis = new FileReader("data.txt");
        CharArrayWriter baos = new CharArrayWriter();

        char[] buffer = new char[512];
        int readCount = 0;
        while ((readCount = fis.read(buffer)) != -1) {
            baos.write(buffer, 0, readCount);
        }
        char[] charArray = baos.toCharArray();
        System.out.println("파일의 내용을 모두 읽어들여 Char[] 로 만듬");
        System.out.println("크기" + charArray.length);

        CharArrayReader bais = new CharArrayReader(charArray);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        while ((readCount = bais.read(buffer)) != -1) {
            bw.write(buffer, 0, readCount);
            bw.flush();
        }
        System.out.println("\n\n");
        System.out.println("읽어들인 내용 모두 출력");

        fis.close();
        bais.close();
        baos.close();
    }
}

```

