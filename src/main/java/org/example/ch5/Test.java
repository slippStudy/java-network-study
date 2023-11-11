package org.example.ch5;

import java.io.*;

class Test {

    public static void main(String[] args) throws IOException {
        String a = "abcdf가나라다foo";
        write(a);
    }

    private static BufferedWriter textOut;
    private static OutputStreamWriter charOut;

    private static void write(String a) throws IOException {
        System.out.println("xx");
        FileOutputStream writer = new FileOutputStream("data-char-demo.txt");
        charOut = new OutputStreamWriter(writer);
        textOut = new BufferedWriter(charOut);
        textOut.write(a);
        textOut.flush();
        charOut.flush();

    }
}
