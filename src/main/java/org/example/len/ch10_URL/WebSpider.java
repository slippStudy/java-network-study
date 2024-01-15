package org.example.len.ch10_URL;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

class WebSpider {

    public static void main(String[] args) throws IOException {
        URL url = new URL("https://ceo.baemin.com/");
        InputStream inputStream = url.openStream();
        FileOutputStream fos = new FileOutputStream("index.html");

    }
}
