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
