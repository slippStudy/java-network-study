package org.example.ch4.performance;

import org.apache.commons.io.IOUtils;

import java.io.*;

public class LearnJavaIoNioApplication3 {
    public static void main(String[] args) throws Exception {
        InputStream in = new FileInputStream("data.txt"); // filePath
        OutputStream out = new FileOutputStream("data2.txt"); // filePath
        BufferedInputStream bin = new BufferedInputStream(in);
        BufferedOutputStream bout = new BufferedOutputStream(out);


        int copyByte = 0;
        int bData;
        long stime = System.currentTimeMillis();
        byte[] buffer = new byte[8192];
        while (true) {
            bData = bin.read(buffer);
            if (bData == -1) {
                break;
            }

            bout.write(buffer, 0, bData);
            copyByte++;
        }
        long etime = System.currentTimeMillis();
        bin.close();
        bout.close();
        System.out.println("---------------버퍼 스트림 전송시간--------------------");
        System.out.println("복사 시간: " + (etime - stime));
        System.out.println("복사된 바이트의 크기: " + copyByte);


    }
}