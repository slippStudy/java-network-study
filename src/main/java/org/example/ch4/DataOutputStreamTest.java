package org.example.ch4;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class DataOutputStreamTest {

    public static void main(String[] args) {
        FileOutputStream fos = null;
        DataOutputStream dos = null;

        try {
            fos = new FileOutputStream("data.txt");
            dos = new DataOutputStream(fos);
            dos.writeBoolean(true);
            dos.writeByte((byte)5);
            dos.writeInt(100);
            dos.writeDouble(200.5);
            dos.writeUTF("hello world");
            System.out.println("저장했습니다.");
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                fos.close();
            } catch (IOException ignored) {

            }
            try {
                dos.close();
            } catch (IOException ignored) {

            }
        }
    }
}
