package org.example.ch4;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;

class BuffedInputStreamTest {

    public static void main(String[] args) throws IOException {


        byte [] bytes = new byte[10000];
        byte [] size = new byte[10];
        Arrays.fill(bytes, (byte) 1);

        BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(bytes));

        byte[] bytes1 = bis.readAllBytes();

        System.out.println(bytes1.length);

    }
}
