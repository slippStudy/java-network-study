package org.example.len.ch11_multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class MulticastReceiver implements Runnable {

    //Attributes
    private Thread tR;
    private String tRName = "MulticastReceiver";

    MulticastSocket socket = null;
    DatagramPacket inPacket = null;
    private String addressString;
    private int port;
    byte[] inBuf = new byte[1000];

    private String msg;

    Scanner sc = new Scanner(System.in);

    public void run() {
        //System.out.println("Running "+ tRName);

        try {
            //Prepare to join multicast group

            System.out.println("Enter group address to receive from: ");
            addressString = sc.nextLine();
            System.out.println("Enter port number to receive from: ");
            port = sc.nextInt();
            System.out.println("Connected! Receiving messages...\n");

            MulticastSender McS = new MulticastSender();
            McS.start();

            socket = new MulticastSocket(port);
            InetAddress address = InetAddress.getByName(addressString);
            socket.joinGroup(address);

            while (true) {
                inPacket = new DatagramPacket(inBuf, inBuf.length);
                socket.receive(inPacket);
                msg = new String(inBuf, 0, inPacket.getLength());
                System.out.println(msg);
                if(!msg.startsWith(MulticastSender.name)) {
                    System.out.println(msg);
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR- "+e+"\n"+"Socket closing.");
        }
    }

    public void start() {
        //System.out.println("Starting "+ tRName);

        if(tR == null) {
            tR = new Thread(this, tRName);
        }
        tR.start();
    }
}

class MulticastSender implements Runnable {

    //Attributes
    private Thread tS;
    private String tSName = "MulticastSender";

    private final String EXIT = "Exit";
    public static String name;

    DatagramSocket socket;
    DatagramPacket outPacket;
    InetAddress address;
    private String addressString;
    private int port;

    private String msg;
    byte[] outBuf;

    Scanner sc = new Scanner(System.in);

    public void run() {
        //System.out.println("Running "+ tSName);

        try {
            System.out.println("Enter your name: ");
            name = sc.nextLine();
            System.out.println("Enter group address to send to: ");
            addressString = sc.nextLine();
            System.out.println("Enter port number to send to: ");
            port = sc.nextInt();
            System.out.println("Connected! Begin typing messages...\n");

            socket = new DatagramSocket();

            while (true) {
                msg = sc.nextLine();
                if(msg.equalsIgnoreCase(EXIT)) {
                    socket.close();
                    System.out.println("Session finished. Closing");
                    break;
                }
                msg = name+": "+msg;
                outBuf = msg.getBytes();

                //Send to multicast IP address and port
                address = InetAddress.getByName(addressString);
                outPacket = new DatagramPacket(outBuf, outBuf.length, address, port);

                socket.send(outPacket);
            }
        } catch (IOException e) {
            System.out.println("ERROR- "+e+"\n"+"Socket closing.");
        }
    }

    public void start() {
        //System.out.println("Starting "+ tSName);

        if(tS == null) {
            tS = new Thread(this, tSName);
        }
        tS.start();
    }
}