package test.modules.main;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class FutureTest {
	public static void main(String[] args) {
		completableTestBasic();
		completableTestApply();
	}
	
	private static void completableTestApply() {
		CompletableFuture<String> loadString = CompletableFuture.supplyAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (Exception e) {
				System.err.println(e);
			}
			return "The String";
		});
		
		try {
			System.out.println("Waiting for loadString...");
			String s = loadString.get();
			System.out.println("Load string: " + s);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	private static void completableTestBasic() {
		CompletableFuture<Void> sleep = CompletableFuture.runAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (Exception e) {
				System.err.println(e);
			}
			return;
		});
		
		try {
			System.out.println("Waiting for sleep...");
			sleep.get();
			System.out.println("Sleep over");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
