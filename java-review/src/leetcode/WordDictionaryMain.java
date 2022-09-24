package leetcode;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class WordDictionaryMain {

	public static void main(String args[]) {
		WordDictionary dic = new WordDictionary();
		
		dic.addWord("foo");
		dic.addWord("foobar");
		dic.addWord("foot");
		dic.addWord("footman");
		dic.addWord("apple");
		dic.addWord("app");
		dic.addWord("appis");
		System.out.println(dic);
		System.out.println(dic.search("app"));
		System.out.println(dic.search("appi"));
		System.out.println(dic.search("appis"));
		System.out.println(dic.search("f..t."));
		System.out.println(dic.search("f..t.an"));
	}
	
	static class WordDictionary {
		static class TrieNode {
			char c;
			boolean isEnd;
			Map<Character, TrieNode> nextMap = new LinkedHashMap<>();
			
			TrieNode(char c) {
				this.c = c;
			}
			TrieNode addChild(char c, boolean isEnd) {
				TrieNode child = nextMap.get(c);
				if (child == null) {
					child = new TrieNode(c);
				}
				if (isEnd) {
					child.isEnd = true;
				}
				nextMap.put(child.c, child);
				return child;
			}
			
			@Override
			public String toString() {
				return "(" + c + " " + isEnd + " " + nextMap.keySet() + ")";
			}
		}
		
		private TrieNode root = new TrieNode('*');
		private int maxLen = 0;
	    public WordDictionary() {
	        
	    }
	    
	    public void addWord(String word) {
	        TrieNode currNode = root;
	        maxLen = Math.max(maxLen, word.length());
	        for (int i = 0; i < word.length(); i++) {
	        	boolean isEnd = i == word.length() - 1;
	        	currNode = currNode.addChild(word.charAt(i), isEnd);
	        }
	    }
	    
	    public boolean search(String word) {
	    	return searchDFS(word, root, 0);
//	    	return searchBFS(word);
	    }
	    
	    public boolean searchDFS(String word, TrieNode node, int idx) {
	    	char currChar = word.charAt(idx);
	    	if (idx == word.length() - 1) {
	    		if (currChar == '.') {
	    			for (TrieNode nextNode : node.nextMap.values()) {
	    				if (nextNode.isEnd) return true;
	    			}
	    			return false;
	    		} else {
		    		TrieNode nextNode = node.nextMap.get(currChar);
		    		return nextNode != null && nextNode.isEnd;
	    		}
	    	}
	    	if (currChar == '.') {
	    		for (TrieNode nextNode : node.nextMap.values()) {
	    			if (searchDFS(word, nextNode, idx + 1)) {
	    				return true;
	    			}
	    		}
	    		return false;
	    	} else {
	    		TrieNode nextNode = node.nextMap.get(currChar);
	    		if (nextNode == null) {
	    			return false;
	    		}
	    		return searchDFS(word, nextNode, idx+1);
	    	}
	    }
	    
	    public boolean searchBFS(String word) {
	    	if (word.length() > maxLen) {
	    		return false;
	    	}
	    	Set<TrieNode> currNodes = new HashSet<>();
	    	currNodes.add(root);
	        for (int i = 0; i < word.length(); i++) {
	        	char currChar = word.charAt(i);
	        	Set<TrieNode> toRemove = new HashSet<>();
	        	Set<TrieNode> nextNodes = new HashSet<>();
	        	for (TrieNode currNode : currNodes) {
	        		if (currChar == '.') {
	        			nextNodes.addAll(currNode.nextMap.values());
	        		} else {
	        			TrieNode nextNode = currNode.nextMap.get(currChar);
	        			if (nextNode != null) {
	        				nextNodes.add(nextNode);
	        			}
	        		}
	        		toRemove.add(currNode);
	        	}
	        	currNodes.removeAll(toRemove);
	        	currNodes.addAll(nextNodes);
	        }
	        for (TrieNode node : currNodes) {
	        	if (node.isEnd) return true;
	        }
	        return false;
	    }
	    
	    public String toString() {
	    	return toStringRecur(root);
	    }
	    
	    public String toStringRecur(TrieNode node) {
	    	StringBuilder ret = new StringBuilder();
	    	ret.append(node.toString());
	    	ret.append(" ");

	    	for (TrieNode child : node.nextMap.values()) {
	    		ret.append(toStringRecur(child));
	    	}
	    	
	    	return ret.toString();
	    }
	}
}
