package test.modules.main;

public class DefaultInterfaceTest {
	public static void main(String[] args) {
		A a = new A();
		B b = new B();
		C c = new C();
		D d = new D();
		
		a.f();
		b.f();
		c.f();
		d.f();
	}
}

interface I {
	public default void f() {
		System.out.println("I.f");
	}
}

interface J {
	public default void f() {
		System.out.println("J.f");
	}
}

class A implements I {}
class B implements J {}
class C implements I, J {
	public void f() {
		System.out.println("C start");
		I.super.f();
		J.super.f();
		System.out.println("C end");
	}
}

class D extends C {
	public void f() {
		System.out.println("D start");
		super.f();
		System.out.println("D end");
	}
}