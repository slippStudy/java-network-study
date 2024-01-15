package org.example.len.ch14_channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

class SharedFileChannelInstanceTest {

    public static void main(String[] args) throws IOException {
        RandomAccessFile raf = new RandomAccessFile("/Users/len/study/java-network-study/data-char-demozxc.txt","rw");

        raf.seek(1000);
        FileChannel fc =raf.getChannel();
        System.out.println("File position: " + fc.position());
        raf.seek(500);
        System.out.println("File position: " + fc.position());
        raf.seek(100);
        System.out.println("File position: " + fc.position());
    }
}
