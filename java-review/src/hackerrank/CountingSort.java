package hackerrank;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.toList;

public class CountingSort {
	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(CountingSort.class.getResourceAsStream("CountingSort.in")));

		int n = Integer.parseInt(bufferedReader.readLine().trim());

		List<List<String>> arr = new ArrayList<>();

		IntStream.range(0, n).forEach(i -> {
			try {
				arr.add(Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" ")).collect(toList()));
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		});

		Result.countSort(arr);

		bufferedReader.close();
	}

	static class Result {

		/*
		 * Complete the 'countSort' function below.
		 *
		 * The function accepts 2D_STRING_ARRAY arr as parameter.
		 */

		public static void countSort(List<List<String>> arr) {
			int n = arr.size();

			int[] counts = new int[100];
			int[] inputKeys = new int[n];
			String[] inputValues = new String[n];

			String[] outputValues = new String[n];

			for (int i = 0; i < n; i++) {
				List<String> inputStr = arr.get(i);
				inputKeys[i] = Integer.parseInt(inputStr.get(0));
				inputValues[i] = i < n / 2 ? "-" : inputStr.get(1);
//                System.out.println("k: " + inputKeys[i] + " v: " +inputValues[i]);
			}

			for (int i = 0; i < n; i++) {
				counts[inputKeys[i]]++;
			}
			// System.out.println("Counts " + counts);

			for (int i = 1; i < counts.length; i++) {
				counts[i] += counts[i - 1];
			}
			// System.out.println("Counts Prefix " + counts);

			for (int i = n - 1; i >= 0; i--) {
				int key = inputKeys[i];
				counts[key]--;
				outputValues[counts[key]] = inputValues[i];
			}

			for (String s : outputValues) {
				System.out.print(s + " ");
			}
		}

	}
}
