package org.example.len.ch7_basic;

import java.net.InetAddress;
import java.net.UnknownHostException;

class NSLookup {

    public static void main(String[] args) throws UnknownHostException {

        InetAddress inetAddress[] = null;
        inetAddress = InetAddress.getAllByName("ceo.baemin.com");
        for (InetAddress address : inetAddress) {
            System.out.println(address.getHostName());
            System.out.println(address.getHostAddress());
            System.out.println(address);
            System.out.println("--------------------");
        }

    }
}
