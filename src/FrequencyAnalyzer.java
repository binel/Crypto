import java.util.HashMap;

public class FrequencyAnalyzer {
	private HashMap<Character, Double> englishFrequencies; 
	public FrequencyAnalyzer() {
		englishFrequencies = new HashMap<Character, Double>();
		initalizeEnglishFrequencies(englishFrequencies);
		
	}
	
	public static HashMap<Character, Double> initalizeEnglishFrequencies(HashMap<Character, Double> frequencies) {
		frequencies.put('A', 0.08167);
		frequencies.put('B', 0.01492);
		frequencies.put('C', 0.02782);
		frequencies.put('D', 0.04253);
		frequencies.put('E', 0.12702);
		frequencies.put('F', 0.02228);
		frequencies.put('G', 0.02015);
		frequencies.put('H', 0.06094);
		frequencies.put('I', 0.06996);
		frequencies.put('J', 0.00153);
		frequencies.put('K', 0.00772);
		frequencies.put('L', 0.04025);
		frequencies.put('M', 0.02406);
		frequencies.put('N', 0.06749);
		frequencies.put('O', 0.07507);
		frequencies.put('P', 0.01929);
		frequencies.put('Q', 0.00095);
		frequencies.put('R', 0.05987);
		frequencies.put('S', 0.06327);
		frequencies.put('T', 0.09056);
		frequencies.put('U', 0.02758);
		frequencies.put('V', 0.00978);
		frequencies.put('W', 0.0236);
		frequencies.put('X', 0.0015);
		frequencies.put('Y', 0.01974);
		frequencies.put('Z', 0.00074);
		
		return frequencies; 
	}
	
	public static HashMap<Character, Double> generateFrequencyHashMap(String cipherText) {
		String text = cipherText.toUpperCase();
		HashMap<Character, Double> frequencies = new HashMap<Character, Double>(); 
		
		for(int i = 0; i < text.length() - 1; i++) {
			if(frequencies.containsKey(text.charAt(i))) {
				frequencies.put(text.charAt(i), frequencies.get(text.charAt(i)) + 1);
			} else {
				frequencies.put(text.charAt(i), 1.0);
			}
			
		}
		
		for(Character c : frequencies.keySet()) {
			frequencies.put(c, frequencies.get(c) / text.length());
		}
		
		return frequencies; 
	}
	
	public Double findFrequenceDifference(HashMap<Character, Double> frequencies) {
		double difference = 0; 
		
		for(Character c : this.englishFrequencies.keySet()) {
			if(frequencies.containsKey(c)) {
				difference += Math.abs(englishFrequencies.get(c) - frequencies.get(c)); 	
			} else {
				difference += Math.abs(englishFrequencies.get(c) - 0);
			}
		}
		
		return difference;
	}
}