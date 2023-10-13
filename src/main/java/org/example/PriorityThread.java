package org.example;

public class PriorityThread implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Start main");

        Thread t = new Thread(new PriorityThread());
        t.start();
        Thread.sleep(500);

        t.setPriority(Thread.MIN_PRIORITY);
        Thread.sleep(500);

        t.setPriority(8);
        Thread.sleep(500);

        t.setPriority(10);
        Thread.sleep(500);

        t.interrupt();

        System.out.println("End Main");
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Priority : " + Thread.currentThread().getPriority());
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {

        } finally {
            System.out.println("Thread is dead...");
        }
    }
}