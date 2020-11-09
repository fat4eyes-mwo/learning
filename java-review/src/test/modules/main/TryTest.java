package test.modules.main;

import java.io.IOException;

public class TryTest {
	public static void main(String[] args) {
		try {
			f();
			g();
		} catch(IOException | NumberFormatException e) {
			System.out.println(e.getClass());
		}
	}
	
	static void f() throws IOException {
	}
	
	static void g() throws NumberFormatException {
	}
}

class TryA {
	public void a() {}
}
class TryB {
	public void b() {}
}