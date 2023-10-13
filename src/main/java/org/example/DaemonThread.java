package org.example;

class DaemonThread {

    public static void main(String[] args) {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    System.out.println("MyThread 종료");
                } catch (InterruptedException e) {

                }
            }
        };
        // 데몬스레드 설정
        t.setDaemon(true);
        t.start();

        System.out.println("Main() 종료");
    }
}
