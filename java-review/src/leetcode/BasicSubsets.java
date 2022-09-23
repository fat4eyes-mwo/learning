package leetcode;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class BasicSubsets {
	public static List<Set<Integer>> subsets(Integer... nums) {
		List<Set<Integer>> ret = new ArrayList<>();
		
		ret.add(new LinkedHashSet<>());
		for (Integer num : nums) {
			int retSize = ret.size();
			for (int i = 0; i < retSize; i++) {
				Set<Integer> newSet = new LinkedHashSet<Integer>(ret.get(i));
				newSet.add(num);
				ret.add(newSet);
			}
		}
		
		return ret;
	}
	
	public static void main(String args[]) {
		List<Set<Integer>> subsets = subsets(1,2,3,4,5);
		
		System.out.println(subsets.size());
		System.out.println(subsets);
	}
}
