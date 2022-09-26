package hackerrank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinCandies {
	public static void main(String[] args) {
		int[] ratings = new int[] { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };

		ArrayList<Integer> ratingsArr = new ArrayList<>();
		for (int rate : ratings) {
			ratingsArr.add(rate);
		}

		System.out.println(Result.candies(ratings.length, ratingsArr));
	}

	static class Result {

		/*
		 * Complete the 'candies' function below.
		 *
		 * The function is expected to return a LONG_INTEGER. The function accepts
		 * following parameters: 1. INTEGER n 2. INTEGER_ARRAY arr
		 */

		public static long candies(int n, List<Integer> arr) {
			// Write your code here
			int prevCandy = 0;
			int candyArr[] = new int[arr.size()];
			for (int i = 0; i < n; i++) {
				int leftRating = i > 0 ? arr.get(i - 1) : -1;
				int currRating = arr.get(i);
				int currCandy = 1;
				prevCandy = i >= 1 ? candyArr[i - 1] : 0;
				if (currRating > leftRating) {
					currCandy = prevCandy + 1;
				} else {
					currCandy = 1;
				}
				candyArr[i] = currCandy;
				// int j = i;
				// while (j > 0 && arr.get(j) < arr.get(j-1) && candyArr[j-1] <= candyArr[j]) {
				// candyArr[j-1]++;
				// j--;
				// }
			}
			for (int i = n - 2; i >= 0; i--) {
				int currRating = arr.get(i);
				int prevRating = arr.get(i + 1);
				if (currRating > prevRating && candyArr[i] <= candyArr[i + 1]) {
					candyArr[i] = candyArr[i + 1] + 1;
				}
			}
			long count = 0;
			for (int candy : candyArr) {
				count += candy;
			}
			System.out.println();
			return count;

		}

	}
}
