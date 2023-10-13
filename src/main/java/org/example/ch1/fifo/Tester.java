package org.example.ch1.fifo;

class Tester {

    public static void main(String[] args) throws InterruptedException {
        JobQueue queue = JobQueue.getInstance();

        //소비자 생성 후 시작
        Thread con1 = new Thread(new Consumer(queue, "1"));
        Thread con2 = new Thread(new Consumer(queue, "2"));
        Thread con3 = new Thread(new Consumer(queue, "3"));
        con1.start();
        con2.start();
        con3.start();

        //생성자 생성 후 시작
        Thread pro = new Thread(new Producer(queue));
        pro.start();

        Thread.sleep(500);
        // 생성자 종료
        pro.interrupt();

        Thread.sleep(500);
        // 소비자 종료
        con1.interrupt();
        con2.interrupt();
        con3.interrupt();


    }
}
