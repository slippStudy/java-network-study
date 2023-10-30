package org.example.ch1.priority;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Main {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 10; i >= 1; i--) {
            MyThread command = new MyThread(i);
            command.setPriority(i);
            executorService.execute(command);
        }


    }
}