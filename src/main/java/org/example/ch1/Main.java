package org.example.ch1;

public class Main {

    public static void main(String[] args) {

        Main stopThread = new Main();
        stopThread.process();
    }

    public void process() {
        // StopThread 인스턴스를 생성한 후 이 인자를 파라미터로 받는 스레드 인스턴스를 생성한 후에 출발
        StopThread st = new StopThread();
        Thread thread = new Thread(st);
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        st.stop();
    }

    class StopThread implements Runnable {
        private boolean stoped = false;

        @Override
        public void run() {
            while (!stoped) {
                System.out.println("Thread is alive ... ");
                // 0.5초 간 멈춘다.
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Thread is dead...");
        }

        public void stop() {
            stoped = true;
        }
    }
}


//    public static void propagateException() throws InterruptedException {
//        Thread.sleep(1000);
//        Thread.currentThread().interrupt();
//        if (Thread.interrupted()) {
//            throw new InterruptedException();
//        }
//    }
