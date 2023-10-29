class Parent2 {
	int i = 7;
	public int get() {
		return i;
	}
}

class Child2 extends Parent2 {
	int i = 5;
	public int get() {
		return i + 100;
	}
}

public class ChildTest {
	public static void print (Parent2 p) {
		System.out.println(p.i);
		System.out.println(p.get());
	}

	public static void main(String args[]) {
		Parent2 p = new Parent2();
		System.out.println("-----1-----");
		System.out.println(p.i);
		System.out.println(p.get());

		Child2 c = new Child2();
		System.out.println("-----2-----");
		System.out.println(c.i);
		System.out.println(c.get());
		
		Parent2 p2 = new Child2();
		System.out.println("-----3-----");
		System.out.println(p2.i);
		System.out.println(p2.get());

		System.out.println("-----4-----");
		print(c);
		print(p);
		print(p2);
	}
}
