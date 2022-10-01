package hackerrank;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class FraudulentActivityNotifications {
	
	public static void main(String[] args) throws IOException {
		final String fileName = "FraudulentActivityNotifications01";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(FraudulentActivityNotifications.class.getResourceAsStream(fileName + ".in")));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName + ".out"));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int d = Integer.parseInt(firstMultipleInput[1]);

        List<Integer> expenditure = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        int result = Result.activityNotifications(expenditure, d);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
	
	static class Result {

	    /*
	     * Complete the 'activityNotifications' function below.
	     *
	     * The function is expected to return an INTEGER.
	     * The function accepts following parameters:
	     *  1. INTEGER_ARRAY expenditure
	     *  2. INTEGER d
	     */

	    public static int activityNotifications(List<Integer> expenditure, int d) {
	        int ret = twoHeapsMedian(expenditure, d);
//	        int ret = naiveMedian(expenditure, d);
	        System.out.println(ret);
	        return ret;
	    }
	    
	    
	    static class Expense implements Comparable<Expense>{
	        int value;
	        int index;
	        Expense(int value, int index) {
	            this.value = value;
	            this.index = index;
	        }
	        @Override
	        public boolean equals(Object obj) {
	            Expense other = (Expense) obj;
	            return value == other.value && index == other.index;
	        }
	        @Override
	        public int hashCode() {
	            return Objects.hash(value, index);
	        }
	        @Override
	        public int compareTo(Expense o) {
	            int compare = Integer.compare(value, o.value);
	            if (compare != 0) {
	                return compare;
	            } else {
	                return Integer.compare(index, o.index);
	            }
	        }
	        @Override
	        public String toString() {
	            return "(" + value + ", " + index + ")";
	        }
	    }
	    
	    //two heaps solution modified to have removal. Used Tree sets for lg(n) removal, lg(n) insert
	    private static int twoHeapsMedian(List<Integer> expenditure, int d) {
	        SortedSet<Expense> highHeap = new TreeSet<>(Comparator.reverseOrder());
	        SortedSet<Expense> lowHeap = new TreeSet<>(Comparator.naturalOrder());

	        int count = 0;
	        for (int i = 0; i < expenditure.size(); i++) {
	            Expense currExpense = new Expense(expenditure.get(i), i);
	            if (i < d) {
	                addToHeaps(currExpense, highHeap, lowHeap, d);  
	                printHeaps(highHeap, lowHeap);
	            } else {
	                double median = computeMedian(highHeap, lowHeap, d);
	                printHeaps(highHeap, lowHeap);
	                // System.out.println("median: " + median + " curr: " + currExpense.value);
	                if (currExpense.value >= 2* median) {
	                    count++;
	                }
	                addToHeaps(currExpense, highHeap, lowHeap, d);  
	                
	                Expense toRemove = new Expense(expenditure.get(i-d), i-d);
	                // System.out.println("toRemove: " + toRemove);
	                if (highHeap.contains(toRemove)) {
	                    highHeap.remove(toRemove);
	                } else if (lowHeap.contains(toRemove)) {
	                    lowHeap.remove(toRemove);
	                } else {
	                    // System.out.println("Error, cant find " + toRemove);
	                }
	                balanceHeaps(highHeap, lowHeap);
	                printHeaps(highHeap, lowHeap);
	            }
	            
	            
	        }
	        
	        return count;
	    }
	    
	    private static double computeMedian(SortedSet<Expense> highHeap, SortedSet<Expense> lowHeap, int d) {
	        double median = 0;
	        if (highHeap.size() > 0 && lowHeap.size() > 0) {
	            median = d % 2 == 0 ? 
	                    ((double)lowHeap.first().value + (double) highHeap.first().value) / 2 :
	                    highHeap.first().value;
	            
	        } else if (highHeap.size() == 0 && lowHeap.size() > 0) {
	            return lowHeap.first().value;
	        } else if (highHeap.size() > 0 && lowHeap.size() == 0) {
	            return highHeap.first().value;
	        } else if (highHeap.size() == 0 && lowHeap.size() == 0) {
	            return 0;
	        }
	        
	        return median;
	    }
	    
	    private static void printHeaps(SortedSet<Expense> highHeap, SortedSet<Expense> lowHeap) {
	        // System.out.println(highHeap + " | " + lowHeap);   
	    }
	    
	    private static void addToHeaps(Expense expense, SortedSet<Expense> highHeap, SortedSet<Expense> lowHeap, int d) {
	        double median = computeMedian(highHeap, lowHeap, d);
	        if (expense.value <= median) {
	            highHeap.add(expense);
	        } else {
	            lowHeap.add(expense);
	        }
	        balanceHeaps(highHeap, lowHeap);
	    }
	    
	    private static void balanceHeaps(SortedSet<Expense> highHeap, SortedSet<Expense> lowHeap) {
	        while (highHeap.size() > lowHeap.size() + 1) {
	            Expense toMove = highHeap.first();
	            lowHeap.add(toMove);
	            highHeap.remove(toMove);
	        } 
	        while (lowHeap.size() > highHeap.size()) {
	            Expense toMove = lowHeap.first();
	            highHeap.add(toMove);
	            lowHeap.remove(toMove);
	        }
	    }
	    
	    //sorts the trail every time, but at least does not recreate the trail array every time
	    private static int naiveMedian(List<Integer> expenditure, int d) {
	        int[] trail = new int[d];
	        int count = 0;
	        for (int i = 0; i < expenditure.size(); i++) {
	            int currEx = expenditure.get(i);
	            if (i < d) {
	                trail[i] = currEx;
	            } else {
	                Arrays.sort(trail);
	                
	                double median = d % 2 == 0 ? ((double)trail[d/2-1] + (double)trail[d/2])/2: trail[d/2];
	                // System.out.println(Arrays.toString(trail) + " median: " + median + " currEx: " + currEx);
	                if (currEx >= median * 2) {
	                    count++;
	                }
	                int toReplace = expenditure.get(i-d);
	                int toReplaceIdx = Arrays.binarySearch(trail, toReplace);
	                trail[toReplaceIdx] = currEx;
	            }
	        }
	        return count;
	    }

	}
}
