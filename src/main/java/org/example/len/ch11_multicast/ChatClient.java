package org.example.len.ch11_multicast;

class ChatClient {
    public static void main(String[] args) {
        MulticastReceiver McR = new MulticastReceiver();
        McR.start();
    }
}
