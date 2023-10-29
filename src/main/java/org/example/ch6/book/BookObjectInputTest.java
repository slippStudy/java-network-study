package org.example.ch6.book;

import java.io.*;
import java.util.ArrayList;

class BookObjectInputTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        FileInputStream fileInputStream = new FileInputStream("booklist.txt");
        ObjectInputStream ois = new ObjectInputStream(fileInputStream);
        ArrayList arrayList = (ArrayList) ois.readObject();
        Book book = (Book) arrayList.get(0);
        Book book1 = (Book) arrayList.get(1);
        Book book2 = (Book) arrayList.get(2);

        System.out.println(book.getIsbn());
        System.out.println(book1.getIsbn());
        System.out.println(book2.getIsbn());


    }
}
