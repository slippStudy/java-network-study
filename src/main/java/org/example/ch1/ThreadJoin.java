package org.example.ch1;

class ThreadJoin {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("MyThread 종료");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();

        t.join();


        System.out.println("Main() 종료");
    }
}
