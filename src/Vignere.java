import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

public class Vignere {
	
	private static int getCharNumber(Character c) {
		Character[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		for(int i = 0; i < chars.length; i++) {
			if(chars[i] == c) {
				return i; 
			}
		}
		return -1; 
	}
	
	//Searches through the ciphertext to the group of characters of the given length that 
	//is repeated the largest number of times. 
	private static ArrayList<String> findRepeatedStrings(String ciphertext, int length) {
		HashMap<String, Integer> occurances = new HashMap<String, Integer>(); 
		
		for(int i = length; i < ciphertext.length(); i++) {
			String s = ciphertext.substring(i - length, i);
			if(occurances.containsKey(s)) {
				occurances.put(s, occurances.get(s) + 1);
			} else {
				occurances.put(s, 1);
			}
		}
		
		ArrayList<String> repeatedStrings = new ArrayList<String>();
		for(String s : occurances.keySet()) {
			if (occurances.get(s) > 2) {
				repeatedStrings.add(s);
			}
		}
		return repeatedStrings; 
	}
	
	//returns an arraylist of the spacing between the given arraylist of words. 
	//Assumes that the word repeats
	private static ArrayList<Integer> findSpaceBetweenWord(ArrayList<String> words, String ciphertext) {
		ArrayList<Integer> allSpacings = new ArrayList<Integer>(); 
		for(String word : words) {
			ArrayList<Integer> positions = new ArrayList<Integer>(); 
			for(int i = word.length(); i < ciphertext.length(); i++) {
				String s = ciphertext.substring(i - word.length(), i); 
				if(s.equals(word)) {
					positions.add(i - word.length());
				}
			}
			
			ArrayList<Integer> spacing = new ArrayList<Integer>(); 
			int lastPosition = -1; 
			for(Integer i : positions) {
				if(lastPosition == -1) {
					lastPosition = i; 
				} else {
					spacing.add(i - lastPosition);
					lastPosition = i; 
				}
			}
			
			for(Integer i : spacing) {
				allSpacings.add(i);
			}
		}
		
		return allSpacings; 
	}
	
	//given a set of numbers this finds all factors 
	private static ArrayList<Integer> findCommonFactors(ArrayList<Integer> nums) {
		ArrayList<Integer> factors = new ArrayList<Integer>(); 
		for(Integer n : nums) {
			for(int f = 2; f < Math.floor(Math.sqrt(n)); f++) {
				if((double)n / (double)f - Math.floor((double)n / (double)f) < 0.00001) {
					factors.add(f); 
					factors.add(n / f);
				}
			}
		}
		return factors; 
	}
	
	//given an arraylist of factors, order them by most common occurance
	private static ArrayList<Integer> orderFactors(ArrayList<Integer> factors) {
		HashMap<Integer, Integer> condensedFactors = new HashMap<Integer, Integer>(); 
		
		for(Integer i : factors) {
			if(condensedFactors.containsKey(i)) {
				condensedFactors.put(i, condensedFactors.get(i) + 1);
			} else {
				condensedFactors.put(i, 1);
			}
		}
		
		LinkedList<Entry<Integer, Integer>> orderedList = sortFactors(condensedFactors);
		ArrayList<Integer> orderedFactors = new ArrayList<Integer>(); 
		for(Entry<Integer, Integer> e : orderedList) {
			orderedFactors.add(e.getKey());
		}
		return orderedFactors;  
	}
	
	private static LinkedList<Entry<Integer, Integer>> sortFactors(HashMap<Integer, Integer> map) {
		LinkedList<Entry<Integer, Integer>> list = new LinkedList<Entry<Integer, Integer>>(map.entrySet());
		
		Collections.sort(list, new Comparator<Entry<Integer, Integer>>() {
			public int compare(Entry<Integer, Integer> c1, Entry<Integer, Integer> c2) {
				return c2.getValue().compareTo(c1.getValue());
			}
		});
		
		return list; 
	}
	
	//breaks up the ciphertext into length-many strings.
	private static String[] breakUpCipherText(String ciphertext, int length) {
		String[] strings = new String[length];
		
		for(int i = 0; i < length; i++) {
			strings[i] = "";
		}
		
		for(int i = 0; i < ciphertext.length(); i++) {
			strings[i % length] += ciphertext.charAt(i);
		}
		
		return strings; 
	}
	
	private static Character applyShift(Character c, int shift) {
		Character[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		if(shift > 0) {
			return chars[(getCharNumber(c) + shift) % 26]; 
		} else {
			return chars[(getCharNumber(c) + shift + 26) % 26];
		}
	}
	
	private static String decryptNShift(String ciphertext) {
		HashMap<Character, Double> freq = FrequencyAnalyzer.generateFrequencyHashMap(ciphertext); 
		
		double max = 0; 
		char maxChar = 0; 
		
		for(Character c : freq.keySet()) {
			if(freq.get(c) > max) {
				max = freq.get(c);
				maxChar = c; 
			}
		}
		//assume the max character is e
		int shift = getCharNumber('e') - getCharNumber(Character.toLowerCase(maxChar)); 
		String ret = "";
		for(int i = 0; i < ciphertext.length(); i++) {
			ret += applyShift(ciphertext.charAt(i), shift);
		}
		
		return ret; 
	}
	
	private static String interleaveStrings(String[] strings) {
		String ret = "";
		
		int[] positions = new int[strings.length];
		for(int i = 0; i < positions.length; i++) {
			positions[i] = 0;
		}
		
		int plaintextLength = 0; 
		for(int i = 0; i < strings.length; i++) {
			plaintextLength += strings[i].length(); 
		}
		
		for(int i = 0; i < plaintextLength; i++) {
			ret += strings[i % strings.length].charAt(positions[i % strings.length]);
			positions[i % strings.length]++; 
		}
		
		return ret; 
	}
	
	public static void main(String args[]) {
		if(args.length != 1) {
			System.out.println("Arg 1 should be the ciphertext to decrypt");
			return; 
		}
		
		String ciphertext = args[0];
		
		ArrayList<String> words = findRepeatedStrings(ciphertext, 3); 
		
		ArrayList<Integer> spacing = findSpaceBetweenWord(words, ciphertext); 

		ArrayList<Integer> factors = findCommonFactors(spacing);
		
		ArrayList<Integer> orderedFactors = orderFactors(factors);
		
		String bestString = "";
		double bestFrequency = 101; 
		int bestKeySize = 0; 
		FrequencyAnalyzer fa = new FrequencyAnalyzer(); 
		
		for(int i = 0; i < orderedFactors.size(); i++) {
			String[] strings = breakUpCipherText(ciphertext, orderedFactors.get(i));
			
			String[] decryptedStrings = new String[orderedFactors.get(i)]; 
		
			for(int j = 0; j < orderedFactors.get(i); j++) {
				decryptedStrings[j] = decryptNShift(strings[j]);
			}
			
			String plainText = interleaveStrings(decryptedStrings);
			
			double freqDiff = fa.findFrequenceDifference(FrequencyAnalyzer.generateFrequencyHashMap(plainText));
			if(freqDiff < bestFrequency) {
				bestFrequency = freqDiff; 
				bestString = plainText; 
				bestKeySize = orderedFactors.get(i); 
			}
		}
		
		System.out.println("Attempted Decryption: ");
		System.out.println("\tKey Size = " + bestKeySize);
		System.out.println("\tPlainText = " + bestString);
	}
}
