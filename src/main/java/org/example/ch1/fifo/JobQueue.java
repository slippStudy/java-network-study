package org.example.ch1.fifo;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class JobQueue {

    private static final String NAME = "JOB QUEUE";
    private static final Object monitor = new Object();

    private LinkedList jobs = new LinkedList();

    // 하나의 객체만을 생성해서 사용할 수 있도록 싱글톤 패턴 사용
    private static JobQueue instance = new JobQueue();

    private JobQueue() {
    }

    public static JobQueue getInstance() {
        if (instance == null) {
            synchronized (JobQueue.class) {
                instance = new JobQueue();
            }
        }
        return instance;
    }

    public String getName() {
        return NAME;
    }

    public LinkedList getLinkedList() {
        return jobs;
    }

    public void clear() {
        synchronized (monitor) {
            jobs.clear();
        }
    }

    public void put(Object o){
        synchronized (monitor) {
            jobs.addLast(o);
            monitor.notify();
        }
    }

    public Object pop() throws InterruptedException {
        Object o = null;
        synchronized (monitor) {
            if (jobs.isEmpty()) {
                monitor.wait();
            }
            o = jobs.removeFirst();
        }
        if (o == null) throw new NoSuchElementException();
        return o;
    }

    public int size() {
        return jobs.size();
    }
}
