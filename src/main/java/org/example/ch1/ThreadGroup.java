package org.example.ch1;

class ThreadGroup {

    public static void main(String[] args) {
        System.out.println("ThreadGroupTest: " + Thread.currentThread());

        java.lang.ThreadGroup tg1 = new java.lang.ThreadGroup(
                Thread.currentThread().getThreadGroup(), "ThreadGroup1"
        );

        java.lang.ThreadGroup tg2 = new java.lang.ThreadGroup("ThreadGroup2");
        java.lang.ThreadGroup tg3 = new java.lang.ThreadGroup(tg1, "ThreadGroup3");

        Thread t1 = new Thread(tg1, "Thread-1");
        Thread t2 = new Thread(tg2, "Thread-2");
        Thread t3 = new Thread(tg3, "Thread-3");

        System.out.println("    t1: " + t1);
        System.out.println("    t2: " + t2);
        System.out.println("    t3: " + t3);
        System.out.println(
                "main 스레드 그룹:" + Thread.currentThread().getThreadGroup() + ", 활동 중인 스레드 개수:" + Thread.currentThread().getThreadGroup().activeCount() + ", 활동중인 스레드 그룹 개수:" + Thread.currentThread().getThreadGroup().activeGroupCount()
        );

        Thread.currentThread().getThreadGroup().list();

    }
}
