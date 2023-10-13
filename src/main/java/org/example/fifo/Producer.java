package org.example.fifo;

class Producer implements Runnable {
    private  JobQueue queue = null;

    public Producer(JobQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("[ START PRODUCER...]");
        try {
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) {
                queue.put(Integer.toString(i++));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("[ END PRODUCER.. ]");
        }
    }
}
