package org.example.len.ch10_URL;

import java.net.MalformedURLException;
import java.net.URL;

class URLInfo {

    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("https://ceo.baemin.com/");

        System.out.println("프로토콜: " + url.getProtocol());
        System.out.println("호스트명: " + url.getHost());
        System.out.println("포트번호: " + url.getPort());
        System.out.println("파일명: " + url.getPath());
        System.out.println("사용자 쿼리: " + url.getQuery());

    }
}
