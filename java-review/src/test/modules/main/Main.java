package test.modules.main;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Tests for new java features
 * @author Neil
 *
 */
public class Main {
	public static void main(String[] args) {
		Map<String, Integer> map = 
			Map.copyOf(Map.of("One",1,
					"Two", 2,
					"Three", 3,
					"Four", 4,
					"Five", 5,
					"Six",6,
					"Seven", 7,
					"Eight", 8,
					"Nine", 9,
					"Ten", 10));
		Deque<String> deque = new ArrayDeque<String>();
				
		System.out.println("Hello world");
		Circle c = new Circle(10.2345);
		Rectangle r = new Rectangle(new Pair<Double, Double>(12.56123, 13.4123));
		Shape s[] = {c, r};
		for (Shape currShape : s) {
			System.out.println(currShape);
			String stateStr = switch (currShape.getS()) {
				case State1 -> "STATE1";
				case State2 -> "STATE2";
				default -> null;
			};
			System.out.println(stateStr);
		}
		
		deque.addFirst("foo");
		deque.addLast("bar");
		String dqString = deque.getFirst();
		dqString = deque.getLast();
		
		//map.put("Eleven", 11); //error since Map.of returns an immutable
		for (String key : map.keySet()) {
			System.out.println(String.format("%s %s", key, map.get(key)));
		}
		
		testCollections();
		
		System.out.println(fib(10));
	}
	
	synchronized public void doAtomic() {
		System.out.println("Foo");
	}
	
	public void notQuiteAtomic() {
		synchronized(this) {
			System.out.println("bar");
		}
	}
	
	Runnable r = () -> {
		System.out.println("runnable lambda");
	};
	
	interface FuncInterface {
		public int f(int x, int y);
	}
	FuncInterface fi = (int x, int y) -> {
		System.out.println("xy"); 
		return x + y;
	};
	
	Function<Integer, Integer> f = (x) -> 12;
	
	static void testCollections() {
		Map<String, List<String>> map = new LinkedHashMap<String ,List<String>>();
		
		map.put("foobar", Arrays.asList(new String[] {"foo", "bar", "baz"}));
		map.put("fizzbuzz", Arrays.asList(new String[] {"fizz", "buzz"}));
		map.put("beepboop", Arrays.asList(new String[] {"beep", "boop"}));
		
		for (String key : map.keySet()) {
			var l = map.get(key);
			System.out.print(key + " : ");
			boolean first = true;
			for (var s : l) {
				if (!first) {
					System.out.print(", ");
				}
				System.out.print(s);
				first = false;
			}
			System.out.println();
		}
		
		System.out.println(map);
	}
	
	static int fib(int n) {
		if (n == 0) {
			return 0;
		} else if (n == 1) {
			return 1;
		} else {
			return fib(n-1) + fib(n-2);
		}
	}
}

enum State {
	State1, State2;
}

abstract class Shape {
	public abstract State getS();
}

class Pair<X, Y> {
	private X x;
	private Y y;
	
	public Pair(X x, Y y) {
		this.x = x;
		this.y = y;
	}
	public String toString() {
		return String.format("%1$s, %2$s", this.x, this.y); 
	}
	public X getX() {
		return x;
	}
	public Y getY() {
		return y;
	}
}

class Circle extends Shape {
	private double r;
	private State s = State.State1;
	public Circle(double r) {
		this.r = r;
	}
	public String toString() {
		return String.format(this.getClass().getSimpleName() + " %1$.2f", this.r);
	}
	public double getR() {
		return this.r;
	}
	public State getS() {
		return s;
	}
}

class Rectangle extends Shape {
	private Pair<Double, Double> dim;
	private State s = State.State2;
	public Rectangle(Pair<Double, Double> dim) {
		this.dim = dim;
	}
	
	public String toString() {
		return String.format("Rectangle %1$.2f %2$.2f", this.dim.getX(), this.dim.getY());
		
	}
	public Pair<Double ,Double> getDimensions() {
		return this.dim;
	}
	public State getS() {
		return this.s;
	}
}