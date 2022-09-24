package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutation {
    
    
    public static void main(String args[]) {
    	Solution s = new Solution();
    	List<List<Integer>> ret = s.permute(new int[] {1,2,3,4,5,6});
    	for (List<Integer> perm : ret) {
    		System.out.println(perm);
    	}
    	System.out.println(ret.size());
    }
    
    static class Solution {
    	public List<List<Integer>> permute(int[] nums) {
            List<Integer> numArr = new ArrayList<>();
            for (int n : nums) {
            	numArr.add(n);
            }
            
            return permuteInternal(numArr);
        }
    	
    	private List<List<Integer>> permuteInternal(List<Integer> nums) {
    		if (nums.size() == 1) {
    			List<List<Integer>> ret = new ArrayList<>();
    			ret.add(nums);
    			return ret;
    		}
    		
    		List<List<Integer>> ret = new ArrayList<>();
    		for (int i = 0; i < nums.size() ;i++) {
    			Integer curr = nums.get(i);
    			List<Integer> recur = new ArrayList<>(nums);
    			recur.remove(i);
    			List<List<Integer>> sumPermsList = permuteInternal(recur);
    			for (List<Integer> subPerms : sumPermsList) {
    				subPerms.add(curr);
    			}
    			ret.addAll(sumPermsList);
    		}
    		return ret;
    	}
    	
    	
    }
}
