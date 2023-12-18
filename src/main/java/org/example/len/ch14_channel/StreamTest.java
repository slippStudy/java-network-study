package org.example.len.ch14_channel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ScatteringByteChannel;

class StreamTest {

    static FileInputStream in = null;
    public static void main(String[] args) throws IOException, InterruptedException {
        in = new FileInputStream("/Users/len/study/java-network-study/data-char-demo.txt");
        TestThread t = new TestThread(in);
        t.start();
        Thread.sleep(2000);
        System.out.println(in.available());

        t.interrupt();

        System.out.println("--------------------");

        Thread.sleep(2000);
        System.out.println(in.available());
    }

    static class TestThread extends Thread {
        FileInputStream in = null;

        public TestThread(FileInputStream in) {
            this.in = in;
        }

        @Override
        public void run() {
            try {
                int v = 0;
                while ((v = in.read()) != -1) {
                    System.out.println("Thread start..");
                    System.out.println(v);
                    Thread.sleep(1000);
                }
                in.close();
            } catch (Exception e) {
                System.out.println("Thread End...");
            }
        }
    }

}
