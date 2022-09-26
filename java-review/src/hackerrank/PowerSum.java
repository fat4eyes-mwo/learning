package hackerrank;

public class PowerSum {

	public static void main(String args[]) {
		System.out.println(Result.powerSum(100, 2));
	}

	static class Result {

		/*
		 * Complete the 'powerSum' function below.
		 *
		 * The function is expected to return an INTEGER. The function accepts following
		 * parameters: 1. INTEGER X 2. INTEGER N
		 */

		public static int powerSum(int X, int N) {
			// Write your code here
			return countSum(1, X, N);
		}

		private static int countSum(int base, int x, int exp) {
			int count = 0;
			int pow = (int) (Math.pow(base, exp));
			if (x - pow == 0) {
				return 1;
			} else if (x - pow < 0) {
				return 0;
			}
			count += countSum(base + 1, x, exp);
			count += countSum(base + 1, x - pow, exp);
			return count;
		}
	}
}
