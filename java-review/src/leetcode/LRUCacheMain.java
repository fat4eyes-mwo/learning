package leetcode;

import java.util.Iterator;
import java.util.LinkedHashMap;

public class LRUCacheMain {
	public static void main(String args[]) {
		LegitLRUCache lRUCache  = new LegitLRUCache(10);
		System.out.println(lRUCache);
		lRUCache.put(10,13);
		System.out.println(lRUCache);
		lRUCache.put(3,17);
		System.out.println(lRUCache);
		lRUCache.put(6,11);
		System.out.println(lRUCache);
		lRUCache.put(10,5);
		System.out.println(lRUCache);
		lRUCache.put(9,10);
		System.out.println(lRUCache);
		lRUCache.get(13);
		System.out.println(lRUCache);
		lRUCache.put(2,19);
		System.out.println(lRUCache);
		lRUCache.get(2);
		System.out.println(lRUCache);
		lRUCache.get(3);
		System.out.println(lRUCache);
		lRUCache.put(5,25);
		System.out.println(lRUCache);
		lRUCache.get(8);
		System.out.println(lRUCache);
		lRUCache.put(9,22);
		System.out.println(lRUCache);
		lRUCache.put(5,5);
		System.out.println(lRUCache);
		lRUCache.put(1,30);
		System.out.println(lRUCache);
		lRUCache.get(11);
		System.out.println(lRUCache);
		lRUCache.put(9,12);
		System.out.println(lRUCache);
		lRUCache.get(7);
		System.out.println(lRUCache);
		lRUCache.get(5);
		System.out.println(lRUCache);
		lRUCache.get(8);
		System.out.println(lRUCache);
		lRUCache.get(9);
		System.out.println(lRUCache);
		lRUCache.put(4,30);
		System.out.println(lRUCache);
		lRUCache.put(9,3);
		System.out.println(lRUCache);
		lRUCache.get(9);
		System.out.println(lRUCache);
		lRUCache.get(10);
		System.out.println(lRUCache);
		lRUCache.get(10);
		System.out.println(lRUCache);
		lRUCache.put(6,14);
		System.out.println(lRUCache);
		lRUCache.put(3,1);
		System.out.println(lRUCache);
		lRUCache.get(3);
		System.out.println(lRUCache);
		lRUCache.put(10,11);
		System.out.println(lRUCache);
		lRUCache.get(8);
		System.out.println(lRUCache);
		lRUCache.put(2,14);
		System.out.println(lRUCache);
		lRUCache.get(1);
		System.out.println(lRUCache);
		lRUCache.get(5);
		System.out.println(lRUCache);
		lRUCache.get(4);
		System.out.println(lRUCache);
		lRUCache.put(11,4);
		System.out.println(lRUCache);
		lRUCache.put(12,24);
		System.out.println(lRUCache);
		lRUCache.put(5,18);
		System.out.println(lRUCache);
		lRUCache.get(13);
		System.out.println(lRUCache);
		lRUCache.put(7,23);
		System.out.println(lRUCache);
		lRUCache.get(8);
		System.out.println(lRUCache);
		lRUCache.get(12);
		System.out.println(lRUCache);
		lRUCache.put(3,27);
		System.out.println(lRUCache);
		lRUCache.put(2,12);
		System.out.println(lRUCache);
		lRUCache.get(5);
		System.out.println(lRUCache);
		lRUCache.put(2,9);
		System.out.println(lRUCache);
		lRUCache.put(13,4);
		System.out.println(lRUCache);
		lRUCache.put(8,18);
		System.out.println(lRUCache);
		lRUCache.put(1,7);
		System.out.println(lRUCache);
		lRUCache.get(6);
		System.out.println(lRUCache);
		lRUCache.put(9,29);
		System.out.println(lRUCache);
		lRUCache.put(8,21);
		System.out.println(lRUCache);
		lRUCache.get(5);
		System.out.println(lRUCache);
		lRUCache.put(6,30);
		System.out.println(lRUCache);
		lRUCache.put(1,12);
		System.out.println(lRUCache);
		lRUCache.get(10);
		System.out.println(lRUCache);
		lRUCache.put(4,15);
		System.out.println(lRUCache);
		lRUCache.put(7,22);
		System.out.println(lRUCache);
		lRUCache.put(11,26);
		System.out.println(lRUCache);
		lRUCache.put(8,17);
		System.out.println(lRUCache);
		lRUCache.put(9,29);
		System.out.println(lRUCache);
		lRUCache.get(5);
		System.out.println(lRUCache);
		lRUCache.put(3,4);
		System.out.println(lRUCache);
		lRUCache.put(11,30);
		System.out.println(lRUCache);
		lRUCache.get(12);
		System.out.println(lRUCache);
		lRUCache.put(4,29);
		System.out.println(lRUCache);
		lRUCache.get(3);
		System.out.println(lRUCache);
		lRUCache.get(9);
		System.out.println(lRUCache);
		lRUCache.get(6);
		System.out.println(lRUCache);
		lRUCache.put(3,4);
		System.out.println(lRUCache);
		lRUCache.get(1);
		System.out.println(lRUCache);
		lRUCache.get(10);
		System.out.println(lRUCache);
		lRUCache.put(3,29);
		System.out.println(lRUCache);
		lRUCache.put(10,28);
		System.out.println(lRUCache);
		lRUCache.put(1,20);
		System.out.println(lRUCache);
		lRUCache.put(11,13);
		System.out.println(lRUCache);
		lRUCache.get(3);
		System.out.println(lRUCache);
		lRUCache.put(3,12);
		System.out.println(lRUCache);
		lRUCache.put(3,8);
		System.out.println(lRUCache);
		lRUCache.put(10,9);
		System.out.println(lRUCache);
		lRUCache.put(3,26);
		System.out.println(lRUCache);
		lRUCache.get(8);
		System.out.println(lRUCache);
		lRUCache.get(7);
		System.out.println(lRUCache);
		lRUCache.get(5);
		System.out.println(lRUCache);
		lRUCache.put(13,17);
		System.out.println(lRUCache);
		lRUCache.put(2,27);
		System.out.println(lRUCache);
		lRUCache.put(11,15);
		System.out.println(lRUCache);
		lRUCache.get(12);
		System.out.println(lRUCache);
		lRUCache.put(9,19);
		System.out.println(lRUCache);
		lRUCache.put(2,15);
		System.out.println(lRUCache);
		lRUCache.put(3,16);
		System.out.println(lRUCache);
		lRUCache.get(1);
		System.out.println(lRUCache);
		lRUCache.put(12,17);
		System.out.println(lRUCache);
		lRUCache.put(9,1);
		System.out.println(lRUCache);
		lRUCache.put(6,19);
		System.out.println(lRUCache);
		lRUCache.get(4);
		System.out.println(lRUCache);
		lRUCache.get(5);
		System.out.println(lRUCache);
		lRUCache.get(5);
		System.out.println(lRUCache);
		lRUCache.put(8,1);
		System.out.println(lRUCache);
		lRUCache.put(11,7);
		System.out.println(lRUCache);
		lRUCache.put(5,2);
		System.out.println(lRUCache);
		lRUCache.put(9,28);
		System.out.println(lRUCache);
		lRUCache.get(1);
		System.out.println(lRUCache);
		lRUCache.put(2,2);
		System.out.println(lRUCache);
		lRUCache.put(7,4);
		System.out.println(lRUCache);
		lRUCache.put(4,22);
		System.out.println(lRUCache);
		lRUCache.put(7,24);
		System.out.println(lRUCache);
		lRUCache.put(9,26);
		System.out.println(lRUCache);
		lRUCache.put(13,28);
		System.out.println(lRUCache);
		lRUCache.put(11,26);
		System.out.println(lRUCache);
	}
	
	static class LRUCache {
	    private LinkedHashMap<Integer, Integer> cache;
	    private int capacity;
	    public LRUCache(int capacity) {
	        System.out.println("LRUCache " + capacity);
	        cache = new LinkedHashMap<>(capacity * 2);
	        this.capacity = capacity;
	    }

	    public int get(int key) {
	        System.out.println("get " + key);
	        Integer val = cache.get(key);
	        if (val != null) {
	            cache.remove(key);
	            cache.put(key, val);
	            return val;
	        } else {
	            return -1;
	        }
	    }

	    public void put(int key, int value) {
	        System.out.println("put " + key + " " + value);
	        Integer val = cache.get(key);
	        if (val != null) {
	            cache.remove(key);
	        }
	        cache.put(key, value);
	        if (cache.size() > capacity) {
	            Iterator<Integer> iter = cache.keySet().iterator();
	            Integer evicted = iter.next();
	            cache.remove(evicted);
	            System.out.println("evict " + evicted);
	        }
	    }
	}
	
	static class LegitLRUCache {
		private LinkedHashMap<Integer, CacheEntry> cache;
		
		static class CacheEntry {
			CacheEntry prev = null;
			CacheEntry next = null;
			int key;
			int val;
			CacheEntry(int key, int val) {
				this.key = key;
				this.val = val;
			}
			@Override
			public String toString() {
				return "(" + key + ", " + val + ")";
			}
		}
		
		private CacheEntry head = null;
		private CacheEntry tail = null;
		
	    private int capacity;
	    public LegitLRUCache(int capacity) {
	        System.out.println("LRUCache " + capacity);
	        cache = new LinkedHashMap<>(capacity * 2);
	        this.capacity = capacity;
	    }

	    public int get(int key) {
	        CacheEntry entry = cache.get(key);
	        if (entry != null) {
	        	removeFromList(entry);
	        	addToList(entry);
	        	System.out.println("get " + key + " " + entry.val);
	        	return entry.val;
	        } else {
	        	System.out.println("get " + key + " " + -1);
	        	return -1;	
	        }
	    }

	    public void put(int key, int value) {
	        System.out.println("put " + key + " " + value);
	        CacheEntry entry = cache.get(key);
	        if (entry != null) {
	        	removeFromList(entry);
	        	entry.val = value;
	        } else {
	        	entry = new CacheEntry(key, value);
	        }
        	cache.put(key, entry);
        	addToList(entry);
        	if (cache.size() > capacity) {
        		evict(head.key);
        	}
	    }
	    
	    private void evict(int key) {
	    	System.out.println("evict " + key);
	    	CacheEntry entry = cache.get(key);
	    	removeFromList(entry);
	    	cache.remove(key);
	    }
	    
	    private void addToList(CacheEntry entry) {
	    	if (head == null && tail == null) {
	    		head = entry;
	    		tail = entry;
	    	} else if (tail != entry){
	    		tail.next = entry;
	    		entry.prev = tail;
	    	}
	    	tail = entry;
	    }
	    
	    private void removeFromList(CacheEntry entry) {
	    	if (head == entry) {
	    		head = entry.next;
	    	}
	    	if (tail == entry) {
	    		tail = entry.prev;
	    	}
	    	if (entry.prev != null) {
	    		entry.prev.next = entry.next;
	    	} 
	    	if (entry.next != null) {
	    		entry.next.prev = entry.prev;
	    	}
	    	entry.next = null;
	    	entry.prev = null;
	    }
	    
	    public String toString() {
	    	StringBuilder ret = new StringBuilder();
	    	CacheEntry entry = head;
	    	while (entry != null) {
	    		ret.append(entry);
	    		ret.append(" ");
	    		entry = entry.next;
	    	}
	    	ret.append(" head: " + head);
	    	ret.append(" tail: " + tail);
	    	return ret.toString();
	    }
	}
}
