package leetcode;

public class LongestPalindrome {

	public static void main(String args[]) {
		Solution s = new Solution();
		String input = "foooof";
		String input2 = "oo";
		String input3 = "o";
		String input4 = "abcdedcba";
		String false1 = "abc";
		String false2 = "ab";
		String false3 = "abcd";
		System.out.println(s.isPalindrome(input, 0, input.length() - 1));
		System.out.println(s.isPalindrome(input2, 0, input2.length() - 1));
		System.out.println(s.isPalindrome(input3, 0, input3.length() - 1));
		System.out.println(s.isPalindrome(input4, 0, input4.length() - 1));
		System.out.println(s.isPalindrome(false1, 0, false1.length() - 1));
		System.out.println(s.isPalindrome(false2, 0, false2.length() - 1));
		System.out.println(s.isPalindrome(false3, 0, false3.length() - 1));
		
		System.out.println(s.longestPalindrome("abaaabcbaaafg"));
		
	}
	
	static class Solution {
	    public String longestPalindrome(String s) {
	        return naive(s);
	    }
	    
	    public String naive(String s) {
	    	String longest = "";
	        for (int start = 0; start < s.length(); start++) {
	            for (int end = s.length() - 1; end >= start + longest.length(); end--) {
	                if (isPalindrome(s, start, end)) {
	                	String candidate = s.substring(start, end + 1); 
	                	if (longest == null || longest.length() < candidate.length()) {
	                		longest = candidate;
	                	}
	                }
	            }
	        }
	        return longest;
	    }
	    public boolean isPalindrome(String s, int start, int end) {
	        for (int i = 0; i < (end - start) / 2 + 1; i++) {
	            if (s.charAt(start + i) != s.charAt(end - i)) {
	                return false;
	            }
	        }
	        return true;
	    }
	}
}
