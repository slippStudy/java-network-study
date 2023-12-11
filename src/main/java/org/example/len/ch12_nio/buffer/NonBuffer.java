package org.example.len.ch12_nio.buffer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

class NonBuffer {

    //1분 17초
    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();
        copy("/Users/len/study/java-network-study/src/main/resources/Zoomm.pkg", "/Users/len/study/java-network-study/src/main/resources/Zoomm2.pkg");
//        Files.copy(Path.of("Zoomm.pkg"), Path.of("Zoomm2.pkg"));
        long endTime = System.currentTimeMillis();
        System.out.println("NonBuffer 처리시간 : " + (endTime - startTime) + " milli seconds");
    }

    public static void copy(String fileFrom, String fileTo) throws IOException {
        InputStream in = null;
        OutputStream out = null;

        in = new FileInputStream(fileFrom);
        out = new FileOutputStream(fileTo);

        while (true) {
            int bytedata = in.read();
            if (bytedata == -1) break;
            out.write(bytedata);
        }

        in.close();
        out.close();
    }
}
