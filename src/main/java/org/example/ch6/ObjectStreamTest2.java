package org.example.ch6;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

class ObjectStreamTest2 {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        FileInputStream fin = new FileInputStream("object.txt");
        ObjectInputStream ois = new ObjectInputStream(fin);

        LinkedList linkedList = (LinkedList) ois.readObject();
        String data1 = (String) linkedList.get(0);
        String data2 = (String) linkedList.get(1);
        String data3 = (String) linkedList.get(2);
        LinkedList linkedList1 = (LinkedList) linkedList.get(3);
        LinkedList linkedList2 = (LinkedList) linkedList.get(4);

        System.out.println(data1);
        System.out.println(data2);
        System.out.println(data3);

        fin.close();
        ois.close();
    }
}
