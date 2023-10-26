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
