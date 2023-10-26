package org.example.ch1;

import java.util.Random;

class ThreadLocalTest {

    // 카운터 변수 생성
    static volatile int counter = 0;

    // 임의 클래스 생성
    static Random random = new Random();

    // ThreadLocal 상속한 ThreadLocalObject 클래스 생성

    private static class ThreadLocalObject extends ThreadLocal<Integer> {
        Random random = new Random();
        // 초기값은 0~999 사이여야 한다.

        @Override
        protected Integer initialValue() {
            return random.nextInt(1000);
        }
    }

    //ThreadLocal 변수 생성
    static ThreadLocal<Integer> threadLocal = new ThreadLocalObject();
    private static void displayValue() {
        System.out.println("Thread Name:" + Thread.currentThread().getName() +
                ", initialValue:" + threadLocal.get() +
                ", counter:" + counter);

    }

    public static void main(String[] args) {
        displayValue();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                synchronized (ThreadLocalTest.class) {
                    counter++;
                }
                displayValue();
                try {
                    Thread.sleep((int) threadLocal.get());
                    displayValue();
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        for (int i = 0; i < 3; i++) {
            Thread t = new Thread(runnable);
            t.start();
        }
    }
}
