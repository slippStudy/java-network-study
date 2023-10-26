package org.example.ch1;

class PriorityThreadMain extends Thread {

    @Override
    public void run() {
        System.out.println("Inside run method");
    }

    public static void main(String[] args) {
        PriorityThreadMain t1 = new PriorityThreadMain();
        PriorityThreadMain t2 = new PriorityThreadMain();
        PriorityThreadMain t3 = new PriorityThreadMain();


        // Thread 1 우선순위 5
        System.out.println("t1 thread priority : " + t1.getPriority());

        // Thread 2 우선순위 5
        System.out.println("t2 thread priority : " + t2.getPriority());

        // Thread 3 우선순위 5
        System.out.println("t3 thread priority : " + t3.getPriority());

        // Setting priorities of above threads by
        // passing integer arguments
        t1.setPriority(2);
        t2.setPriority(5);
        t3.setPriority(8);
        // t3.setPriority(21); 같이 1~10 사이로 지정하지 않게되면 throw IllegalArgumentException 발생된다

        // 2
        System.out.println("t1 thread priority : " + t1.getPriority());

        // 5
        System.out.println("t2 thread priority : " + t2.getPriority());

        // 8
        System.out.println("t3 thread priority : " + t3.getPriority());

        // Main thread
        // 현재 실행중인 스레드 이름 반환
        System.out.println("Currently Executing Thread : " + Thread.currentThread().getName());

        System.out.println("Main thread priority : " + Thread.currentThread().getPriority());

        // 메인 스레드의 우선 순위를 10으로 세팅
        Thread.currentThread().setPriority(10);

        System.out.println("Main thread priority : " + Thread.currentThread().getPriority());
    }
}