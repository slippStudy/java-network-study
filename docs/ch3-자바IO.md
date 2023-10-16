## 자바 IO 가 무엇이지?

자바 입출력 프로그래밍을 말한다. 이 때 키보드나, 네트워크 파일 등으로부터 받을 수 있다.
 출력도 마찬가지~

자바IO 를 이해하기 위해서는 상속에 대한 문법을 확실하게 이해해야 한다. 왜?

### 재사용되기 위해서 설계된 자바IO

왜 재사용일까?

상속이라는 것이 왜 재사용과 맞닿아 있는가?

```java
class ChildTest {

    class Parent2 {
        int i = 7;
        public int get() {
            return i;
        }
    }

    class Child2 extends Parent2 {
        int i = 5;

        public int get() {
            return i;
        }
    }

    public static void main(String[] args) {
        ChildTest childTest = new ChildTest();
        childTest.execute();


    }

    private void execute() {
        Parent2 p = new Parent2();
        System.out.println("--1--");
        System.out.println(p.i);
        System.out.println(p.get());

        Child2 c = new Child2();
        System.out.println("--2--");
        System.out.println(c.i);
        System.out.println(c.get());

        Parent2 p2 = new Child2();
        System.out.println("--3--");
        System.out.println(p2.i);
        System.out.println(p2.get());

        System.out.println("--4--");
        print(c);
        print(p2);
    }

    public static void print(Parent2 p) {
        System.out.println(p.i);
        System.out.println(p.get());
    }
}
```

이게 왜 중요 할까?

뒤에서 배울 자바 IO 관련 클래스를 배울 때 이것을 알고 사용하는 것과 모르고 사용하는 것이 다르다.

상속을 사용하는 경우에 상위 객체의 데이터를 가져올 수 있다는 사실을 알아야 한다.

