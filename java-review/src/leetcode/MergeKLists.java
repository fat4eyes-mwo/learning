package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class MergeKLists {
	
	private ListNode[] data(Integer[][] data) {
		List<ListNode> ret = new ArrayList<>();
		
		for (int i = 0; i < data.length; i++) {
			ListNode currList = null;
			ListNode currEnd = null;
			Integer[] col = data[i];
			for (int j = 0; j < col.length; j++) {
				Integer currData = col[j];
				if (currList == null) {
					currList = new ListNode(currData);
					currEnd = currList;
				} else {
					currEnd.next = new ListNode(currData);
					currEnd = currEnd.next;
				}
			}
			ret.add(currList);
		}
		
		return ret.toArray(new ListNode[0]);
	}
	
	public static void main(String[] args) {
		new MergeKLists().runSolution();
	}
	
	private void runSolution() {
//		Solution s = new Solution();
		Solution s = new SolutionCheat();
		Integer[][] arrData = new Integer[][] {
				new Integer[] {1,3,5,7,9},
				new Integer[] {2,4,6,8,10},
				new Integer[] {1,3,5,7,9,12,13,14,15},
				new Integer[] {}
		};
		
		ListNode[] listData = data(arrData);
		
		ListNode mergedList = s.mergeKLists(listData);
		
		System.out.println("Result: " + s.listToString(mergedList));
	}
	
	class Solution {
		public ListNode mergeKLists(ListNode[] lists) {
			ListNode ret = null;
			ListNode currEnd = null;
			
			System.out.println("Input size: " + lists.length);
			for (ListNode n : lists) {
				System.out.println(listToString(n));	
			}

			SortedSet<ListNode> sortedLists = new TreeSet<>((ListNode a, ListNode b) -> {
				if (a.val != b.val) {
					return Integer.compare(a.val, b.val);
				} else {
					return Integer.compare(a.hashCode(), b.hashCode());
				}
			});
			for (ListNode list : lists) {
				if (list != null) {
					sortedLists.add(list);
				}
			}
			
			while (sortedLists.size() > 0){
				ListNode first = sortedLists.first();
				ListNode firstNext = first.next;
				sortedLists.remove(first);
				if (firstNext != null) {
					sortedLists.add(firstNext);
				}
				if (ret == null) {
					ret = first;
					currEnd = ret;
				} else {
					currEnd.next = first;
					currEnd = currEnd.next;
				}
			} 
			
			return ret;
		}
		
		
		public String listToString(ListNode l) {
			StringBuilder ret = new StringBuilder();
			ListNode curr = l;
			while (curr != null) {
				ret.append(curr.val + ", ");
				curr = curr.next;
			};
			return ret.toString();
		}
	}
	
	class SolutionCheat extends Solution {
		@Override
		public ListNode mergeKLists(ListNode[] lists) {
			ArrayList<ListNode> sortArr = new ArrayList<>();
			
			System.out.println("Input size: " + lists.length);
			for (ListNode n : lists) {
				System.out.println(listToString(n));	
			}
			
			for (ListNode n : lists) {
				ListNode currNode = n;
				while (currNode != null) {
					sortArr.add(currNode);
					currNode = currNode.next;
				}
			}
			ListNode[] arr = sortArr.toArray(new ListNode[0]);
			Arrays.sort(arr, (ListNode a, ListNode b) -> {
				if (a.val != b.val) {
					return Integer.compare(a.val, b.val);
				} else {
					return Integer.compare(a.hashCode(), b.hashCode());
				}
			});
			
			ListNode head = null;
			ListNode prevNode = null;
			for (ListNode n : arr) {
				if (head == null) {
					head = n;
				}
				if (prevNode != null) {
					prevNode.next = n;
				}
				prevNode = n;
			}
			if (prevNode != null) {
				prevNode.next = null;
			}
			
			return head;
		}
	}
	
	//This is untested and probably broken
	class ArrayHeap {
		private ArrayList<ListNode> heap = new ArrayList<>();
		int heapMax = -1;
		public ArrayHeap() {
			//do nothing
		}
		
		public ListNode getFirst() {
			if (heapMax >= 0) {
				return heap.get(0);
			} else {
				return null;
			}
		}
		public ListNode removeFirst() {
			ListNode first = getFirst();
			if (first == null) {
				return first;
			}
			ListNode last = heap.get(heapMax);
			heap.set(0, last);
			heap.set(heapMax, null);
			heapMax--;
			
			if (heapMax == -1) {
				return first;
			}
			int currIdx = 0;
			do {
				ListNode curr = heap.get(currIdx);
				int leftIdx = (2 * currIdx) + 1;
				int rightIdx = (2 * currIdx) + 2;
				ListNode leftChild = heap.get(leftIdx);
				ListNode rightChild = heap.get(rightIdx);
				if (curr.val > leftChild.val) {
					heap.set(currIdx, leftChild);
					heap.set(leftIdx, curr);
					currIdx = leftIdx;
				} else if (curr.val > rightChild.val) {
					heap.set(currIdx, rightChild);
					heap.set(rightIdx, curr);
					currIdx = rightIdx;
				} else {
					break;
				}
			} while (currIdx < heapMax);
			return first;
		}
		
		public void add(ListNode node) {
			heapMax++;
			if (heapMax > heap.size()) {
				heap.add(node);
			} else {
				heap.set(heapMax, node);
			}
			int currIdx = heapMax;
			while (currIdx > 0) {
				int parentIdx = (currIdx - 1) / 2;
				ListNode parent = heap.get(parentIdx);
				if (parent.val > node.val) {
					heap.set(parentIdx, node);
					heap.set(currIdx, parent);
					currIdx = parentIdx;
				} else {
					break;
				}
			}
		}
		
		public int size() {
			return heapMax + 1;
		}
	}
	

	static class ListNode {
		int val;
		ListNode next;

		ListNode() {
		}

		ListNode(int val) {
			this.val = val;
		}

		ListNode(int val, ListNode next) {
			this.val = val;
			this.next = next;
		}
		
		public String toString() {
			return "val = " + val + " next = " + (next != null ? next.val : null);
		}
	}
}

/**
 * Definition for singly-linked list. public class ListNode { int val; ListNode
 * next; ListNode() {} ListNode(int val) { this.val = val; } ListNode(int val,
 * ListNode next) { this.val = val; this.next = next; } }
 */
