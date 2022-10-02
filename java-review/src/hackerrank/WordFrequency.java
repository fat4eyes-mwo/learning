package hackerrank;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class WordFrequency {

	public static void main(String args[]) {
		naiveFrequency("the quick brown fox jumps over the lazy dog");
		naiveFrequency("We shall fight in France, we shall fight on the seas and oceans, we shall fight with growing confidence and growing strength in the air, we shall defend our island, whatever the cost may be. We shall fight on the beaches, we shall fight on the landing grounds, we shall fight in the fields and in the streets, we shall fight in the hills; we shall never surrender. And even if, which I do not for a moment believe, this island or a large part of it were subjugated and starving, then our Empire beyond the seas, armed and guarded by the British Fleet, would carry on the struggle, until, in God's good time, the New World, with all its power and might, steps forth to the rescue and the liberation of the old.");
		
		onlineFrequency("the quick brown fox jumps over the lazy dog");
		onlineFrequency("We shall fight in France, we shall fight on the seas and oceans, we shall fight with growing confidence and growing strength in the air, we shall defend our island, whatever the cost may be. We shall fight on the beaches, we shall fight on the landing grounds, we shall fight in the fields and in the streets, we shall fight in the hills; we shall never surrender. And even if, which I do not for a moment believe, this island or a large part of it were subjugated and starving, then our Empire beyond the seas, armed and guarded by the British Fleet, would carry on the struggle, until, in God's good time, the New World, with all its power and might, steps forth to the rescue and the liberation of the old.");
	}
	
	private static class Word implements Comparable<Word>{
		String word;
		int count;
		
		public Word(String word, int count) {
			this.word = word;
			this.count = count;
		}
		
		@Override
		public boolean equals(Object obj) {
			Word other = (Word)obj;
			return word.compareTo(other.word) == 0;
		}
		
		@Override
		public int hashCode() {
			return word.hashCode();
		}
		
		public int compareTo(Word o) {
			int compare = Integer.compare(o.count, this.count);
			if (compare != 0) {
				return compare;
			} else {
				return word.compareTo(o.word);
			}
		}
		
		@Override
		public String toString() {
			return word + " = " + count;
		}
	}
	
	private static void onlineFrequency(String words) {
		System.out.println("Online: " + words);
		Map<String, Word> wordCountMap = new LinkedHashMap<>();
		SortedSet<Word> wordCounts = new TreeSet<>();
		
		String[] wordArr = words.split(" ");
		for (String word : wordArr) {
			word = word.toLowerCase();
			word = word.replaceAll("\\p{Punct}", "");

			Word wordCount = wordCountMap.computeIfAbsent(word, (w) -> new Word(w, 0));
			wordCounts.remove(wordCount);
			wordCount.count++;
			wordCounts.add(wordCount);
		}
		
		for (Word word : wordCounts) {
			System.out.println(word);
		}
	}
	
	private static void naiveFrequency(String words) {
		System.out.println("Naive: " + words);
		String[] wordArr = words.split(" ");
		Map<String, Integer> freq = new LinkedHashMap<>();
		for (String word : wordArr) {
			word = word.toLowerCase();
			word = word.replaceAll("\\p{Punct}", "");
			int count = freq.computeIfAbsent(word, (String w) -> 0);
			freq.put(word, count + 1);
		}
		String[] sortedWords = freq.keySet().toArray(new String[0]);
		Arrays.sort(sortedWords, (String a, String b) -> Integer.compare(freq.get(b), freq.get(a)));
		for (String word : sortedWords) {
			System.out.println(word + " = " + freq.get(word));
		}
	}
}
