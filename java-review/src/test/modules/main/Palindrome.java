package test.modules.main;


public class Palindrome {
	public static void main(String[] args) {
		System.out.println("hello");
		
		String[] tests = new String[] {
				"ABCBA",
				null,
				"",
				"abCBA",
				"abc defg",
				"      this           si       ht     ",
				"This is not a palindrome",
				"A man a plan a canal Panama"
		};
		for (var test : tests) {
			System.out.println(test + ":" + isPalindrome(test));	
		}
		
	}
	
	//A palindrome is a string that reads the same way backwards or forwards (ignoring case and spaces)
	public static boolean isPalindrome(String s) {
		if (s == null) { 
			return false;
		}
		//Normalize the string. Change everything to uppercase and remove spaces
		s = s.toUpperCase();
		s = s.replaceAll("\s", "");
		
		//Now check each character in the string from left to right (up to length/2) and see
		//if the matching character on the other side is the same
		for (int leftIndex = 0; leftIndex < s.length() / 2; leftIndex++) {
			int rightIndex = s.length() - leftIndex - 1;
			if (s.charAt(leftIndex) != s.charAt(rightIndex)) {
				return false;
			}
		}
		//If all characters matched
		return true;
	}
}
