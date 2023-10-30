package org.example.ch6.book;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

class BookObjectOutputTest {

    public static void main(String[] args) throws IOException {

        ArrayList list = new ArrayList();
        Book b1 = new Book("a1");
        Book b2 = new Book("a2");
        Book b3 = new Book("a3");

        list.add(b1);
        list.add(b2);
        list.add(b3);

        FileOutputStream fileOutputStream = new FileOutputStream("booklist.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
        oos.writeObject(list);
        oos.reset();
        System.out.println("저장완료");


    }
}
