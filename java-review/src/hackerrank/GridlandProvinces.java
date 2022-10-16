package hackerrank;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class GridlandProvinces {
	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		final String fileName = "GridlandProvinces_03";
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(GridlandProvinces.class.getResourceAsStream(fileName + ".in")));
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName + ".out"));

		int p = Integer.parseInt(bufferedReader.readLine().trim());

		IntStream.range(0, p).forEach(pItr -> {
			try {
				int n = Integer.parseInt(bufferedReader.readLine().trim());

				String s1 = bufferedReader.readLine();

				String s2 = bufferedReader.readLine();

				int result = Result.gridlandProvinces(s1, s2);

				bufferedWriter.write(String.valueOf(result));
				bufferedWriter.newLine();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		});

		bufferedReader.close();
		bufferedWriter.close();
		long endTime = System.currentTimeMillis();
		System.out.println("Time: " + (endTime - startTime));
	}

	static class Result {

		/*
		 * Complete the 'gridlandProvinces' function below.
		 *
		 * The function is expected to return an INTEGER. The function accepts following
		 * parameters: 1. STRING s1 2. STRING s2
		 */
		// seems like compute all hamiltonian paths, and then check for uniqueness of
		// the generated string
		// but doing it naively will be too slow (around 2^2n total paths, because each
		// move after the first will mostly have 2 choices to go to)
		// Need to find a way to generate the paths without doing full exhaustive path
		// Maybe reduce the number of choices (make a move unchoosable if it blocks off
		// the rest of the map)
		// cases: start in a non-corner: only choices for valid paths are clockwise and
		// counter clockwise, since going up or down will block off part of the map
		// start on a corner: clockwise, counter clockwise and different levels of
		// snake-like paths
		// maybe recursive is still doable, but with special rules for going up/down:
		// can only go up/down if either the left column or right column is already
		// completely visited (or is outside the map)
		public static int gridlandProvinces(String s1, String s2) {
			// Write your code here
			return recursivePaths(s1, s2);
		}

		// Test case 2: 3.5s
		// Test case 5: 64.9s
		// Test case 3: 73.8s
		// How to make this faster? I don't see any search space reduction anymore...
		private static int recursivePaths(String s1, String s2) {
			int n = s1.length();
			char[][] grid = new char[2][n];
			boolean[][] visited = new boolean[2][n];
			Set<String> fullPaths = new LinkedHashSet<>();
			Set<Character> uniqueChars = new LinkedHashSet<>();
			for (int i = 0; i < s1.length(); i++) {
				grid[0][i] = s1.charAt(i);
				grid[1][i] = s2.charAt(i);
				uniqueChars.add(s1.charAt(i));
				uniqueChars.add(s2.charAt(i));
			}
			// degenerate case, if only 1 char then only 1 possible unique path
			if (uniqueChars.size() == 1) {
				return 1;
			}
			for (int row : new int[] { 0, 1 }) {
				for (int col = 0; col < n; col++) {
					StringBuffer currPath = new StringBuffer();
					findPath(row, col, grid, visited, currPath, fullPaths);
				}
			}
			System.out.println(fullPaths.size());
			return fullPaths.size();
		}

		// recursive solution not fast enough for expected runtime. Need to trim down
		// paths even more
		// input sizes seems like it is posible with DP, but not sure what to DP on.
		// maybe DP all possible paths from the corners, then use the recursion for the
		// paths starting from the middle?
		private static void findPath(int row, int col, char[][] grid, boolean[][] visited, StringBuffer currPath,
				Set<String> fullPaths) {
			int n = grid[0].length;
			if (!isValid(row, col, visited)) {
				return;
			}
			try {
				// System.out.println("Visiting " + row + ", " + col + " char: " +
				// grid[row][col]);
				visited[row][col] = true;
				currPath.append(grid[row][col]);

				// fully visited
				if (currPath.length() == 2 * n) {
					String fullPath = currPath.toString();
					fullPaths.add(fullPath);
					// System.out.println(fullPath);
					return;
				}
				int otherRow = row == 0 ? 1 : 0;
				findPath(row, col + 1, grid, visited, currPath, fullPaths);
				findPath(row, col - 1, grid, visited, currPath, fullPaths);
				findPath(otherRow, col, grid, visited, currPath, fullPaths);
			} finally {
				// were reusing the visited grid and currpath, so clean it up before return
				visited[row][col] = false;
				currPath.deleteCharAt(currPath.length() - 1);
			}
		}

		// Check these conditions:
		// If trying to move horizontally, just check if it is already visited
		// If trying to move vertically, make sure that that it does not block off the
		// rest of the map (i.e.)
		// the left or right col is already fully visited
		private static boolean isValid(int row, int col, boolean[][] visited) {
			int n = visited[0].length;
			if (row < 0 || row >= visited.length) {
				return false;
			}
			if (col < 0 || col >= visited[0].length) {
				return false;
			}
			if (visited[row][col]) {
				return false;
			}
			int otherRow = row == 0 ? 1 : 0;
			int leftCol = col - 1;
			int rightCol = col + 1;
			if (visited[otherRow][col]) {
				if (leftCol < 0)
					return true;
				if (rightCol >= n)
					return true;
				return (visited[row][leftCol] && visited[otherRow][leftCol])
						|| (visited[row][rightCol] && visited[otherRow][rightCol]);
			}
			return true;
		}

		// attempt at DP from corners.
		// This won't work because it does not take long loops into consideration
		// it just grows the paths from the left/right one square at a time (snake path)
		// How to generate the loops?
		private static void cornerDP(int row, int col, char[][] grid, Set<String> fullPaths) {
			int n = grid[0].length;
			Set<String>[][] possiblePaths = new Set[2][grid.length];
			possiblePaths[row][col] = new LinkedHashSet<>();
			int colDelta = col == 0 ? 1 : -1;
			int colEnd = col == 0 ? grid.length : -1;

			for (int colIter = col;; col += colDelta) {
				if (col == colEnd)
					break;
				int otherRow = row == 0 ? 1 : 0;
				possiblePaths[row][colIter] = new LinkedHashSet<>();
				possiblePaths[otherRow][colIter] = new LinkedHashSet<>();
				int prevCol = colIter + colDelta;
				String currChar = Character.toString(grid[row][colIter]);
				String otherChar = Character.toString(grid[otherRow][colIter]);
				Set<String> thisPaths = possiblePaths[row][colIter];
				Set<String> otherPaths = possiblePaths[otherRow][colIter];
				if (prevCol < 0 || prevCol >= n) {
					thisPaths.add(currChar);
					otherPaths.add(currChar + otherChar);
				} else {
					Set<String> prevThisPaths = possiblePaths[row][prevCol];
					Set<String> prevOtherPaths = possiblePaths[otherRow][prevCol];
				}
			}
		}

	}
}
