package org.example.ch5;

import java.io.*;
import java.nio.charset.Charset;

class LineWriter {

    public static void main(String[] args) throws IOException {

        FileReader fr = new FileReader("zxc.gif", Charset.defaultCharset());
        FileWriter fw = new FileWriter("zxc3.gif", Charset.defaultCharset());
        char[] buffer = new char[512];


        FileOutputStream fw2 = new FileOutputStream("xxx.gif");
//
//        byte[] buffer = new byte[512];
//        int readCount = 0;
//        while ((readCount = fr2.read(buffer)) != -1) {
//            fw2.write(buffer, 0, readCount);
//        }
//        fr2.close();
//        fw2.close();
        int readCount = 0;
        System.out.println("buffer1");
        System.out.println(buffer);
        fw.write(buffer, 0, readCount);
        fw.close();

        System.out.println("xx");

        FileReader fr2 = new FileReader("zxc2.gif");
        char[] buffer2 = new char[512];
        int read = fr2.read(buffer2);
        System.out.println("buffer2");
        System.out.println(buffer2);
        fr.close();
        fr2.close();

    }
}
