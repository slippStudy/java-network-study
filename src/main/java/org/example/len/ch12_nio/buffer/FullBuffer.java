package org.example.len.ch12_nio.buffer;

import java.io.*;

class FullBuffer {

    // 1.5초
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        copy("/Users/len/study/java-network-study/src/main/resources/Zoomm.pkg", "/Users/len/study/java-network-study/src/main/resources/Zoomm2.pkg");
//        Files.copy(Path.of("Zoomm.pkg"), Path.of("Zoomm2.pkg"));
        long endTime = System.currentTimeMillis();
        System.out.println("NonBuffer 처리시간 : " + (endTime - startTime) + " milli seconds");
    }

    public static void copy(String fileFrom, String fileTo) throws IOException {
        InputStream in = new FileInputStream(fileFrom);
        OutputStream out = new FileOutputStream(fileTo);
        int available = in.available();
        byte[] bytes = new byte[available];
        int bytedata = in.read(bytes);
        out.write(bytedata);
        in.close();
        out.close();
    }
}
