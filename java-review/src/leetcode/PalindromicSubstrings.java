package leetcode;

public class PalindromicSubstrings {
	public static void main(String args[]) {
		Solution s = new Solution();
		System.out.println(s.countSubstrings("aaa"));
	}
}


class Solution {
    public int countSubstrings(String s) {
    	int count = 0;
//    	System.out.println("input = " + s);
    	for (int i = 0; i < s.length(); i++) {
    		count += countPalindromeOdd(s, i);
    		count += countPalindromeEven(s, i);
    	}
        return count;
    }
    
    private int countPalindromeOdd(String s, int mid) {
    	int count = 0;
    	int left = mid;
    	int right = mid;
    	while (left >= 0 && right <s.length()) {
    		if (s.charAt(left) == s.charAt(right)) {
    			printSubString(s, left, right);
    			count++;
    		} else{
    			break;
    		}
    		left--;
    		right++;
    	}
    	return count;
    }
    
    private int countPalindromeEven(String s, int mid) {
    	int count = 0;
    	int left = mid;
    	int right = mid + 1;
    	while (left >=0 && right < s.length()) {
    		if (s.charAt(left) == s.charAt(right)) {
    			printSubString(s, left, right);
    			count++;
    		} else {
    			break;
    		}
    		left--;
    		right++;
    	}
    	return count;
    }
    
    private boolean printToOut = true;
    private void printSubString(String s, int left, int right) {
    	if (printToOut) {
    		System.out.println(s.substring(left, right + 1));
    	}
    }
}