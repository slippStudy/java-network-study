package org.example;

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
        // 데몬스레드 설정
        t.start();

//        try {
            t.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.out.println("Main() 종료");
    }
}
