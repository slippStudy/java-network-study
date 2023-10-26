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
