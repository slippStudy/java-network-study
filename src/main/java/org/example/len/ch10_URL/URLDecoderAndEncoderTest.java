package org.example.len.ch10_URL;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

class URLDecoderAndEncoderTest {

    public static void main(String[] args) {
        String zxc = URLEncoder.encode("테스트", Charset.defaultCharset());
        System.out.println(zxc);

        String decode = URLDecoder.decode(zxc);
        System.out.println("테스트 == " + decode);
    }
}
