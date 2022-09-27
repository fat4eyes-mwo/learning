package hackerrank;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.toList;

public class MagicSquareForming {

	public static void main(String[] args) throws IOException {
		final String filename = "MagicSquareForming";
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(MagicSquareForming.class.getResourceAsStream(filename + ".in")));
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename + ".out"));

		List<List<Integer>> s = new ArrayList<>();

		IntStream.range(0, 3).forEach(i -> {
			try {
				s.add(Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" ")).map(Integer::parseInt)
						.collect(toList()));
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		});

		int result = Result.formingMagicSquare(s);

		bufferedWriter.write(String.valueOf(result));
		bufferedWriter.newLine();

		bufferedReader.close();
		bufferedWriter.close();
	}

	static class Result {

		/*
		 * Complete the 'formingMagicSquare' function below.
		 *
		 * The function is expected to return an INTEGER. The function accepts
		 * 2D_INTEGER_ARRAY s as parameter.
		 */

		public static int formingMagicSquare(List<List<Integer>> s) {
			// Write your code here
			int startSquare[][] = new int[3][3];
			int row = 0;
			for (List<Integer> rowList : s) {
				int col = 0;
				for (Integer i : rowList) {
					startSquare[row][col] = i;
					col++;
				}
				row++;
			}
			
			printSquare(startSquare);

			int[][] base = magic;
			int[][] rot1 = rotate(magic);
			int[][] rot2 = rotate(rot1);
			int[][] rot3 = rotate(rot2);
			int[][] tr = transpose(magic);
			int[][] tr_rot1 = rotate(tr);
			int[][] tr_rot2 = rotate(tr_rot1);
			int[][] tr_rot3 = rotate(tr_rot2);

			List<int[][]> squares = new ArrayList<>();
			squares.add(base);
			squares.add(rot1);
			squares.add(rot2);
			squares.add(rot3);
			squares.add(tr);
			squares.add(tr_rot1);
			squares.add(tr_rot2);
			squares.add(tr_rot3);
			List<int[][]> reflects = new ArrayList<>();
			for (int[][] magicsq : squares) {
				reflects.add(reflectH(magicsq));
				reflects.add(reflectV(magicsq));
			}
			squares.addAll(reflects);
			
			printSquare(reflectH(base));
			printSquare(reflectV(base));
			printSquare(transpose(base));
			printSquare(rotate(base));

			int minCost = Integer.MAX_VALUE;
			for (int[][] magicsq : squares) {
				int cost = computeCost(startSquare, magicsq);
				if (cost < minCost) {
					printSquare(magicsq);
					minCost = cost;
				}
			}
			System.out.println(minCost);
			return minCost;
		}

		private static int[][] magic = new int[][] { 
				new int[] { 8, 1, 6 }, 
				new int[] { 3, 5, 7 },
				new int[] { 4, 9, 2 } };

		private static int[][] transpose(int[][] square) {
			int[][] ret = new int[3][3];

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					ret[j][i] = square[i][j];
				}
			}

			return ret;
		}

		private static int[][] rotate(int[][] square) {
			int[][] ret = new int[3][3];

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					ret[i][j] = square[2-j][i];
				}
			}
			return ret;
		}

		private static int computeCost(int[][] square, int[][] magic) {
			int cost = 0;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					cost += Math.abs(square[i][j] - magic[i][j]);
				}
			}
			return cost;
		}
		
		private static int[][] reflectH(int[][] square) {
			int[][] ret = new int[3][3];
			
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					ret[i][j] = square[i][2-j];
				}
			}
			
			return ret;
		}
		
		private static int[][] reflectV(int[][] square) {
			int[][] ret = new int[3][3];
			
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					ret[i][j] = square[2-i][j];
				}
			}
			
			return ret;
		}

		private static void printSquare(int[][] square) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					System.out.print(square[i][j] + " ");
				}
				System.out.println();
			}
		}

	}
}
