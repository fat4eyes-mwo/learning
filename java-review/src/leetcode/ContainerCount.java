package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContainerCount {

	public static void main(String args[]) {
		String s = "**|****|*|**|*";
		
		ContainerCountSolution soln = new NaiveContainerCount();
		System.out.println(soln.containerCount(s, 1, 14));

		ContainerCountSolution listSoln = new ContainerListCount();
		System.out.println(listSoln.containerCount(s, 1, 14));
		
		for (int i = 0; i < 10; i++) {
			generateTestAndRun(10000, 0.5, 1000000, new NaiveContainerCount(), new ContainerListCount());
		}
	}
	
	private static void generateTestAndRun(int len, double wallProb, int numRuns, ContainerCountSolution... solnArr) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < len; i++) {
			char nextChar = Math.random() < wallProb ? '|' : '*';
			s.append(nextChar);
		}
		
		String input = s.toString();
//		System.out.println("input: " + input);
		
		List<int[]> queries = new ArrayList<>();
		for (int i = 0; i < numRuns; i++) {
			int start = (int) Math.floor(Math.random() * (double)len) + 1;
			int end = start + (int) Math.floor(Math.random() * (double)(len - start));
			int[] query = new int[] {start, end};
			queries.add(query);
		}
		
		for (ContainerCountSolution soln : solnArr) {
			System.gc();
			long startTime = System.currentTimeMillis();
			for (int[] query : queries) {
				int start = query[0];
				int end = query[1];
				int ret = soln.containerCount(input, start, end);
//				System.out.println(ret);
			}
			long endTime = System.currentTimeMillis();
			System.out.println("Time: " + (endTime-startTime) +" length: " + len + " numRuns: " + numRuns + " soln: " + soln.getClass().getName());
		}
	}
}

interface ContainerCountSolution {
	public int containerCount(String s, int start, int end);
}

class NaiveContainerCount implements ContainerCountSolution {
	@Override
	public int containerCount(String s, int start, int end) {
		start = start - 1;
		end = end - 1;
		int prevContainer = -1;
		int sum = 0;
		for (int i = start; i <= end; i++) {
			if (s.charAt(i) != '|') {
				continue;
			}
			if (prevContainer != -1) {
				sum += i - prevContainer - 1;
			}
			prevContainer = i;
		}
		return sum;
	}
}

class ContainerListCount implements ContainerCountSolution {
	static class Container implements Comparable<Container>{
		int start;
		int count;
		Container(int start, int count) {
			this.start = start;
			this.count = count;
		}
		@Override
		public String toString() {
			return "(" + start + ", " + count + ")";
		}
		@Override
		public int compareTo(Container o) {
			return Integer.compare(start, o.start);
		}
	}
	private List<Container> cList = new ArrayList<>();
	private boolean isPopulated = false;
	
	//assumes only 1 string is ever used for each instance of ContainerListCount
	@Override
	public int containerCount(String s, int start, int end) {
		int sum = 0;
		start = start - 1;
		end = end - 1;
		if (!isPopulated) {
			generateCList(s);
		}
		if (cList.size() == 0) {
			return 0;
		}
		
		Container startContainer = new Container(start, -1);
		int pos = Collections.binarySearch(cList, startContainer);
		if (pos < 0) {
			pos = -(pos + 1);
		}
		while ( pos < cList.size()) {
			Container currContainer = cList.get(pos);
			if (currContainer.start + currContainer.count + 1 <= end) {
				sum += currContainer.count;
				pos++;
			} else {
				break;
			}
		}
		return sum;
	}
	
	private void generateCList(String s) {
		int start = 0;
		int end = s.length() - 1;
		int prevContainer = -1;
		for (int i = start; i <= end; i++) {
			if (s.charAt(i) != '|') {
				continue;
			}
			if (prevContainer != -1) {
				int currSum = i - prevContainer - 1;
				if (currSum > 0) {
					cList.add(new Container(prevContainer, currSum));
				}
			}
			prevContainer = i;
		}
		isPopulated = true;
	}
}