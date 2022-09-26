package leetcode;

import java.util.HashMap;

public class FirstMissingPositive {

	class Solution {
		public int firstMissingPositive(int[] nums) {
			return kindasmart(nums);
		}

		private int kindasmart(int[] nums) {
			HashMap<Integer, Boolean> found = new HashMap<>();

			for (int num : nums) {
				if (num <= 0)
					continue;
				found.put(num, true);
			}
			for (int i = 0; i < 100000; i++) {
				if (found.get(i) == null) {
					return i;
				}
			}
			return 100001;
		}

		private int dumb(int[] nums) {
			boolean[] found = new boolean[100000]; // this is so dumb, only works because input set is limited
			for (int num : nums) {
				if (num <= 0)
					continue;
				if (num < found.length + 1) {
					found[num - 1] = true;
				}
			}
			for (int i = 0; i < found.length; i++) {
				if (!found[i]) {
					return i + 1;
				}
			}
			return found.length + 1;
		}
	}
}
