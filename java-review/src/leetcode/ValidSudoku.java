package leetcode;
import java.util.HashSet;
import java.util.Set;

public class ValidSudoku {
	
	public static void main(String args[]) {
		Solution s = new Solution();
		s.isValidSudoku(null);
	}
	
	static class Solution {
		Set<Character>[] rows = new Set[9];
		Set<Character>[] cols = new Set[9];
		Set<Character>[][] blocks = new Set[3][3];
	    public boolean isValidSudoku(char[][] board) {
	    	for (int row = 0; row < 9; row++ ) {
	    		for (int col = 0; col < 9; col++) {
	    			char currChar = board[row][col];
	    			if (currChar == '.') {
	    				continue;
	    			}
	    			Set<Character> rowSet = getRow(row);
	    			Set<Character> colSet = getCol(col);
	    			Set<Character> blockSet = getBlock(row / 3, col / 3);
	    			
	    			if (rowSet.contains(currChar)) {
	    				return false;
	    			}
	    			if (colSet.contains(currChar)) {
	    				return false;
	    			}
	    			if (blockSet.contains(currChar)) {
	    				return false;
	    			}
	    			rowSet.add(currChar);
	    			colSet.add(currChar);
	    			blockSet.add(currChar);
	    		}
	    	}
	        return true;
	    }
	    
	    Set<Character> getRow(int i) {
	    	if (rows[i] == null) {
	    		rows[i] = new HashSet<>();
	    	}
	    	return rows[i];
	    }
	    
	    Set<Character> getCol(int i) {
	    	if (cols[i] == null) {
	    		cols[i] = new HashSet<>();
	    	}
	    	return cols[i];
	    }
	    
	    Set<Character> getBlock(int i, int j) {
	    	if (blocks[i][j] == null) {
	    		blocks[i][j] = new HashSet<>();
	    	}
	    	return blocks[i][j];
	    }
	}
	
	
}
