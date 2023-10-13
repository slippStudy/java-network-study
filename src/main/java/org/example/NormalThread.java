package org.example;

class NormalThread {

    public static void main(String[] args) {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("MyThread 종료");
                } catch (InterruptedException e) {

                }
            }
        };
        t.start();

        System.out.println("Main() 종료");
    }
}
