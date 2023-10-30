package org.example.ch4;

import java.io.*;

class DataInputStreamTest {

    public static void main(String[] args) throws IOException {
        FileInputStream fis = null;
        DataInputStream dis = null;

        try {
            fis = new FileInputStream("data.bin");
            dis = new DataInputStream(fis);

            boolean b = dis.readBoolean();
            byte b1 = dis.readByte();
            int i = dis.readInt();
            double d = dis.readDouble();
            String s = dis.readUTF();
            System.out.println("boolean : " + b);
            System.out.println("byte : " + b1);
            System.out.println("int : " + i);
            System.out.println("double : " + d);
            System.out.println("String : " + s);

        } catch (IOException e) {
            System.out.println(e);
        } finally {
            fis.close();
            dis.close();
        }
    }
}
