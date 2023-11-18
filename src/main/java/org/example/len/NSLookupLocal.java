package org.example.len;

import java.net.InetAddress;
import java.net.UnknownHostException;

class NSLookupLocal {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress inetAddr = null;

        inetAddr = InetAddress.getLocalHost();

        System.out.println(inetAddr.getHostName());
        System.out.println(inetAddr.getHostAddress());

        System.out.println("Byte[] 형식의 ip 주소 값을 출력");
        byte[] ip = inetAddr.getAddress();
        for (byte b : ip) {

            System.out.print(b);
        }
    }
}
