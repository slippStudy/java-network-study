package org.example.ch5;

import java.io.*;
import java.util.Objects;

class LineWriter {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("LineWriter.txt")));

        String line = null;
        while (!Objects.equals(line = br.readLine(), "null")) {
            System.out.println("읽어들인 문자열: " + line);
            pw.println(line);

        }
        pw.close();
    }
}
