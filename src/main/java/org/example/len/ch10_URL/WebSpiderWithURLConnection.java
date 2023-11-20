package org.example.len.ch10_URL;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

class WebSpiderWithURLConnection {

    public static void main(String[] args) throws MalformedURLException {

        URL url = new URL("https://ceo.baemin.com/");
        FileOutputStream fos = null;

        URLConnection urlcon = null;
        try {
            urlcon = url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String contentType = urlcon.getContentType();
        long d1 = urlcon.getDate();
        Date date = new Date(d1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
        String sDate = format.format(date);

        try {
            InputStream in = urlcon.getInputStream();
            fos = new FileOutputStream("index.html");
            byte[] buffer = new byte[512];
            int readCount = 0;

            System.out.println("읽어오기 시작");
            while ((readCount = in.read(buffer)) != -1) {
                fos.write(buffer, 0, readCount);
            }
            System.out.println("파일 저장");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
