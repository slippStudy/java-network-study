package org.example.len.ch12_nio.buffer;

import java.io.*;

class SmallBuffer {

    // 53초
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        copy("/Users/len/study/java-network-study/src/main/resources/Zoomm.pkg", "/Users/len/study/java-network-study/src/main/resources/Zoomm2.pkg");
//        Files.copy(Path.of("Zoomm.pkg"), Path.of("Zoomm2.pkg"));
        long endTime = System.currentTimeMillis();
        System.out.println("NonBuffer 처리시간 : " + (endTime - startTime) + " milli seconds");
    }

    public static void copy(String fileFrom, String fileTo) throws IOException {
        // 파일을 읽고 쓸 때 2048Byte 크기를 갖는 버퍼스트림으로 버퍼링하면서 파일 복사
        InputStream inBuffer = null;
        OutputStream outBuffer = null;

        InputStream in = new FileInputStream(fileFrom);
        inBuffer = new BufferedInputStream(in, 2048);
        OutputStream out = new FileOutputStream(fileTo);
        outBuffer = new BufferedOutputStream(out, 2048);

        while (true) {
            int bytedata = inBuffer.read();

            if (bytedata == -1) break;
            out.write(bytedata);
        }

        inBuffer.close();
        outBuffer.close();
    }
}
