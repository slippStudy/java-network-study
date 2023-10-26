package org.example.ch2;

class ChildTest {

    class Parent2 {
        int i = 7;

        public Parent2() {
        }

        public int get() {
            return i;
        }
    }

    class Child2 extends Parent2 {

        int i = 5;

        public Child2() {
            System.out.println("xx");
        }

        @Override
        public int get() {
            return i;
        }
    }

    public static void main(String[] args) {
        ChildTest childTest = new ChildTest();
        childTest.execute();


    }

    private void execute() {
//        Parent2 p = new Parent2();
//        System.out.println("--1--");
//        System.out.println(p.i);
//        System.out.println(p.get());
//
//        Child2 c = new Child2();
//        System.out.println("--2--");
//        System.out.println(c.i);
//        System.out.println(c.get());

        Parent2 p2 = new Child2();
        System.out.println("--3--");
        System.out.println(p2.i);
        System.out.println(p2.get());

//        System.out.println("--4--");
//        print(c);
//        print(p2);
    }

    public static void print(Parent2 p) {
        System.out.println(p.i);
        System.out.println(p.get());
    }
}
