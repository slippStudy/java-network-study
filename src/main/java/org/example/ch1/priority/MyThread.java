package org.example.ch1.priority;

public class MyThread extends Thread{

    int num;

    public MyThread(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        System.out.println(this.num+" thread  start");

        for(int i=0;i<10000;i++){
            for(int j=0;j<10000;j++){
                for(int k=0;k<10000;k++){

                }
            }
        }
        System.out.println(this.num+" thread  end");
    }
}