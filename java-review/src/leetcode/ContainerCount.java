package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class ContainerCount {

	private static final boolean PRINT_COUNTS = false;
	
	public static void main(String args[]) {
		String s = "**|****|*|**|*";
		
		ContainerCountSolution soln = new NaiveContainerCount();
		System.out.println(soln.containerCount(s, 1, 14) + ", iter: " + soln.getIterCount());

		ContainerCountSolution listSoln = new ContainerListCount();
		System.out.println(listSoln.containerCount(s, 1, 14) + ", iter: " + listSoln.getIterCount());
		
		ContainerCountSolution fullcacheSoln = new ContainerCountIntervalCache();
		System.out.println(fullcacheSoln.containerCount(s, 1, 14) + ", iter: " + fullcacheSoln.getIterCount());
		System.out.println(fullcacheSoln.containerCount(s, 1, 14) + ", iter: " + fullcacheSoln.getIterCount());
		System.out.println(fullcacheSoln.containerCount(s, 2, 14) + ", iter: " + fullcacheSoln.getIterCount());
		System.out.println(fullcacheSoln.containerCount(s, 1, 8) + ", iter: " + fullcacheSoln.getIterCount());
		
		
		for (int i = 0; i < 10; i++) {
			generateTestAndRun(10000, 0.5, 50000, 
					new NaiveContainerCount(), 
					new ContainerListCount(),
					new ContainerCountIntervalCache()
					);
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
				if (PRINT_COUNTS) {
					System.out.println(ret);	
				}
			}
			long endTime = System.currentTimeMillis();
			System.out.println("Time: " + (endTime-startTime) +
					" length: " + len + 
					" numRuns: " + numRuns + 
					" iterCount: " + soln.getIterCount() +
					" soln: " + soln.getClass().getName());
		}
	}
}

interface ContainerCountSolution {
	public int containerCount(String s, int start, int end);
	public long getIterCount();
}

class NaiveContainerCount implements ContainerCountSolution {
	private long iterCount = 0;
	@Override
	public int containerCount(String s, int start, int end) {
		start = start - 1;
		end = end - 1;
		int prevContainer = -1;
		int sum = 0;
		for (int i = start; i <= end; i++) {
			iterCount++;
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
	
	@Override
	public long getIterCount() {
		return iterCount;
	}
}

class ContainerListCount implements ContainerCountSolution {
	protected long iterCount = 0;
	
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
		public int getEnd() {
			return start + count + 1;
		}
	}
	protected List<Container> cList = new ArrayList<>();
	protected boolean isCListGenerated = false;
	
	@Override
	public long getIterCount() {
		return iterCount;
	}
	
	//assumes only 1 string is ever used for each instance of ContainerListCount
	@Override
	public int containerCount(String s, int start, int end) {
		return containerCountInternal(s, start - 1, end - 1);
	}
	
	protected int containerCountInternal(String s, int start, int end) {
		int sum = 0;
		if (!isCListGenerated) {
			generateCList(s);
		}
		if (cList.size() == 0) {
			return 0;
		}
		int pos = getFirstContainerIdx(start);
		while ( pos < cList.size()) {
			iterCount++;
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

	protected int getFirstContainerIdx(int start) {
		Container startContainer = new Container(start, -1);
		int pos = Collections.binarySearch(cList, startContainer);
		iterCount += (Math.log(cList.size())/Math.log(2));
		if (pos < 0) {
			pos = -(pos + 1);
		}
		return pos;
	}
	
	protected void generateCList(String s) {
		int start = 0;
		int end = s.length() - 1;
		int prevContainer = -1;
		for (int i = start; i <= end; i++) {
			iterCount++;
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
		isCListGenerated = true;
	}
}

class ContainerCountIntervalCache extends ContainerListCount {
	
	static class IntervalCount {
		private int firstContainer;
		private int lastContainer;
		private int count;
		IntervalCount(int firstContainer, int lastContainer, int count) {
			this.firstContainer = firstContainer;
			this.lastContainer = lastContainer;
			this.count = count;
		}
		
		@Override
		public String toString() {
			return "(" + firstContainer + ", " + lastContainer + ", " + count + ")";
		}
	}
	
	TreeSet<IntervalCount> intervalCache = new TreeSet<>((IntervalCount a, IntervalCount b) -> {
		int compare = Integer.compare(a.firstContainer, b.firstContainer);
		if (compare != 0) {
			return compare;
		} else {
			return Integer.compare(b.lastContainer, a.lastContainer);
		}
	});
	
	@Override
	public int containerCount(String s, int start, int end) {
		if (!isCListGenerated) {
			generateCList(s);
		}
		return containerCountInternal(s, start - 1, end - 1);
	}
	
	@Override
	protected int containerCountInternal(String s, int start, int end) {
		int sum = 0;
		if (!isCListGenerated) {
			generateCList(s);
		}
		if (cList.size() == 0) {
			return 0;
		}
		
		IntervalCount cachedInterval = getLargestInterval(start, end);
		if (cachedInterval != null) {
			sum += cachedInterval.count;
			Container lastContainer = cList.get(cachedInterval.lastContainer);
			sum += containerCountInternal(s, lastContainer.getEnd(), end);
			return sum;
		}
		
		int firstContainer = -1;
		int lastContainer = -1;
		int pos = getFirstContainerIdx(start);
		while ( pos < cList.size()) {
			iterCount++;
			Container currContainer = cList.get(pos);
			if (currContainer.start + currContainer.count + 1 <= end) {
				sum += currContainer.count;
				if (firstContainer == -1) {
					firstContainer = pos;
				}
				lastContainer = pos;
				intervalCache.add(new IntervalCount(firstContainer, lastContainer, sum));
				pos++;
			} else {
				break;
			}
		}
		
		return sum;
	}
	
	private IntervalCount getLargestInterval(int start, int end) {
		int firstContainer = getFirstContainerIdx(start);
		if (firstContainer > cList.size()) {
			return null;
		}
		
		IntervalCount search = new IntervalCount(firstContainer, cList.size(), -1);
		IntervalCount candidate = intervalCache.higher(search);
		iterCount += intervalCache.size() > 0 ? Math.log(intervalCache.size()) / Math.log(2) : 0;
		while (candidate != null) {
			
			if (firstContainer != candidate.firstContainer) {
				return null; //passed the intervals for our first container, did not find any match
			}
			Container lastContainer = cList.get(candidate.lastContainer);
			if (lastContainer.getEnd() <= end) {
				return candidate;
			}
			candidate = intervalCache.higher(candidate);
			iterCount += intervalCache.size() > 0 ? Math.log(intervalCache.size()) / Math.log(2) : 0;
		}
		return null;
	}
	
}