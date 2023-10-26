package org.example.ch1;

class PriorityThreadsMain {

    public static void main(String[] args) {
        PriorityThreadsMain threadMain = new PriorityThreadsMain();
        threadMain.process();
    }

    private void process() {
        Task1 task1 = new Task1();
        task1.start();

        Task2 task2 = new Task2();
        Thread task2Thread = new Thread(task2);
        // task2 우선 순위를 가장 높게 설정
        task1.setPriority(Thread.MAX_PRIORITY);
        task2Thread.start();

        for (int i = 0; i <= 100; i++) {
            System.out.print(i + " ");
        }
        System.out.println("\\nTask3 Finish");
    }

    class Task1 extends Thread {
        @Override
        public void run() {
            System.out.println("\\nTask1 Start");
            for (int i = 100; i <= 200; i++) {
                System.out.print(i + " ");
            }
            System.out.println("\\nTask1 Finish");
        }
    }

    class Task2 implements Runnable {
        @Override
        public void run() {
            System.out.println("\\nTask2 Start");
            for (int i = 1000; i <= 2000; i++) {
                System.out.print(i + " ");
            }
            System.out.println("\\nTask2 Finish");
        }
    }

}
