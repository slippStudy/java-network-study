package org.example;

public class AdvancedStopThread implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        AdvancedStopThread ast = new AdvancedStopThread();
        Thread thread = new Thread(ast);
        thread.start();
        
        Thread.sleep(1000);

        thread.interrupt();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Thread is alive..");
                // 0.5초 멈춘다
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {

        } finally {
            System.out.println("Thread is dead...");
        }
    }
}