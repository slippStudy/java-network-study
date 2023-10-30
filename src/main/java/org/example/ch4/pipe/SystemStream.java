package org.example.ch4.pipe;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

class SystemStream {
    public static void main(String[] args) throws IOException {
        int ch;
        while (true) {
            PipedInputStream writeIn = new PipedInputStream();
            PipedOutputStream readOut = new PipedOutputStream(writeIn);

            ReadThread rt = new ReadThread(System.in, readOut);
            ReadThread wt = new ReadThread(writeIn, System.out);

            rt.start();
            wt.start();
        }
    }
}
