package org.example.ch6;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

class ObjectStreamTest1 {

    public static void main(String[] args) throws IOException {
        FileOutputStream fout = null;

        LinkedList list = new LinkedList<>();
        LinkedList list2 = new LinkedList<>();
        LinkedList list3 = new LinkedList<>();

        list.add("data 1");
        list.add("data 2");
        list.add("data 3");

        list.add(list2);
        list.add(list3);

        fout = new FileOutputStream("object.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fout);

        oos.writeObject(list);
        oos.reset();
        System.out.println("저장되었습니다.");

        fout.close();
        oos.close();
    }
}
