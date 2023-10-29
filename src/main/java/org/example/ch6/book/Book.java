package org.example.ch6.book;

import java.io.Serializable;

class Book implements Serializable {
    private transient String isbn;

    public Book(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
