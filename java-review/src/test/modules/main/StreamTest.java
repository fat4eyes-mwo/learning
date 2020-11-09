package test.modules.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.stream.IntStream.range;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StreamTest {
	public static void main(String[] args) {
		streamTest();
		flatmapTest();
		parallelStreamTest();
		threadpoolParallelStreamTest();
		filesort();
	}
	
	private static void filesort() {
		final String fileIn = "in.txt";
		final String fileOut = "out.txt";
		try (BufferedReader r = new BufferedReader(new FileReader(fileIn));
			BufferedWriter w = new BufferedWriter(new FileWriter(fileOut))) {
			String line = r.readLine();
			List<String> lines = new ArrayList<String>();
			do {
				lines.add(line);
				line = r.readLine();
			} while (line != null);
			lines.stream().sorted((a, b) -> {
				return Integer.parseInt(a) - Integer.parseInt(b);
			}).forEach((s) -> {
				try {
					w.write(s + "\n");
				} catch (IOException e) {
					System.err.println(e); //weird that this is not caught by the outer catch. Because stream processing could be parallel?
				}
			});
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
	private static void threadpoolParallelStreamTest() {
		System.out.println("threadpoolParallelStreamTest");
		ForkJoinPool customThreadPool = new ForkJoinPool(4);
		try {
			customThreadPool.submit(() -> {
				range(0, 100)
					.parallel()
					.filter((i) -> i > 50)
					.forEach(System.out::println);
			}).get();
		}
		catch (Exception e) { 
			System.err.println(e);
		}
	}
	
	private static void parallelStreamTest() {
		System.out.println("parallelStreamTest");
		range(0, 100).parallel().filter((i) -> i > 50).forEach(System.out::println);
	}
	
	private static void streamTest() {
		List<Car> carList = Arrays.asList(new Car[] { new Car("corvette", 1955), new Car("mustang", 1960),
				new Car("pinto", 1970), new Car("civic", 1980), new Car("corolla", 1975), new Car("model t", 1908) });
		Stream<Car> carStream = carList.stream();

		carStream.filter((Car c) -> c.getYear() > 1970).sorted().forEach((Car c) -> {
			System.out.println(c);
		});

//		carStream
//			.filter((Car c) -> c.getYear() < 1970)
//			.sorted()
//			.map(Car::getModel)
//			.collect(Collectors.toList())
//			.forEach((String s) -> {System.out.print(s);});
	}

	private static void flatmapTest() {
		List<List<String>> list = Arrays.asList(
				  Arrays.asList("a", "b"),
				  Arrays.asList("c", "d"));
			list
				.stream()
				.flatMap(Collection::stream)
				.collect(Collectors.toList())
				.forEach((String s) -> {System.out.println(s);});
	}
}

class Car implements Comparable<Car> {
	private String model;
	private int year;

	Car(String model, int year) {
		this.model = model;
		this.year = year;
	}

	public String getModel() {
		return model;
	}

	public int getYear() {
		return year;
	}

	@Override
	public String toString() {
		return String.format("%s %s", year, model);
	}

	@Override
	public int compareTo(Car other) {
		if (year != other.getYear()) {
			return year - other.getYear();
		} else {
			return model.compareTo(other.getModel());
		}
	}
}