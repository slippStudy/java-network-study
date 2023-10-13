package org.example.ch1.fifo;

class Consumer implements Runnable {
    private JobQueue queue = null;
    private String name = null;

    public Consumer(JobQueue queue, String index) {
        this.queue = queue;
        this.name = "Consumer-" + index;
    }

    @Override
    public void run() {
        System.out.println("[ Start " + name + "...");
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(name + " : " + queue.pop().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("[ End " + name + ".. ]");
        }
    }
}
