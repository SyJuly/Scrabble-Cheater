import java.io.*;
import java.util.*;

public class Dictionary {
	private String filename;
	private int prime;
	private LinkedList<String>[] hashTable;
	
	public Dictionary(String filename) throws IOException{
		this.filename = filename;
		prime = 0;
		hashWords(getWords());
	}
	
	public String[] getWords() throws IOException{
		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<String> words = new ArrayList<String>();
		String line = "";
		while(br.ready()){
			line += br.readLine();
			words.addAll(Arrays.asList(line.split(" ")));
			
		}
		br.close();
		
		String[] wordArray = words.toArray(new String[words.size()]);

		return wordArray;
	}
	
	public LinkedList<String>[] hashWords(String[] words){
		prime = getLowestAbovePrime(words.length);
		hashTable = new LinkedList[prime];
		
		for(String word: words){
			int hashValue = getHashValue(word);
			if(hashTable[hashValue] == null){
				LinkedList<String> newList = new LinkedList<String>();
				newList.addFirst(word);
				hashTable[hashValue] = newList;
			} else{
				LinkedList<String> values = hashTable[hashValue];
				if(values.size()<16){
					values.add(word);
					hashTable[hashValue] = values;
				} else{
					if(hashValue<0){
						hashValue = prime-1;
					}
					while(hashTable[hashValue]!=null &&
						  hashTable[hashValue].size()>=16){
						if(hashValue>0)
							hashValue--;
						else
							hashValue = prime-1;
					}
					if(hashTable[hashValue]==null){
						LinkedList<String> newList=new LinkedList<String>();
						newList.addFirst(word);
						hashTable[hashValue] = newList;
					} else
						hashTable[hashValue].add(word);
				}
			}
		}
		
		return hashTable;
	}
	
	public String[] findWord(String word){
		int hashValue = getHashValue(word);
		while(hashTable[hashValue]!=null){
			for(String str: hashTable[hashValue]){
				if(word.equalsIgnoreCase(str)){
					return hashTable[hashValue].toArray(new String[hashTable[hashValue].size()]);
				}
			}
			if(hashValue>=0)
				hashValue--;
			else
				hashValue = prime-1;
		}
		return new String[]{"The word is not in here."};
		
	}
	
	public LinkedList<String> findSameHashed(String str){
		int hashValue = getHashValue(str);
		int origHashValue = getHashValue(str);
		LinkedList<String> values = new LinkedList<String>(); 
		if(hashTable[hashValue]==null)
			return null;
		int nextHashValue = hashValue+1;
		if(nextHashValue>=prime){
			nextHashValue=0;
		}
		if(hashTable[hashValue].size()<16&&hashTable[nextHashValue].size()<16)
			return hashTable[hashValue];
		else if(hashTable[hashValue].size()<16){
			for(String word: hashTable[hashValue]){
				if(getHashValue(word)==origHashValue)
					values.add(word);
			}
			return values;
		}
		while(hashTable[hashValue]!=null){
			for(String word: hashTable[hashValue]){
				if(getHashValue(word)==origHashValue)
					values.add(word);
			}
			if(hashTable[hashValue].size()==16){
				if(hashValue>0){
					hashValue--;
				}
				else{
					hashValue = prime-1;
				}
			}

			else
				return values;
			// termination condition for the case that all lists are filled
			if(origHashValue==hashValue)
				return null;
			
		}
		return null;
	}
	
	public void printTableStats(){
		int entries = 0;
		int collisions = 0;
		int maxChain = 0;
		int maxChainPos = 0;
		for(int i = 0; i<prime; i++){
			if(hashTable[i]!=null){
				int chainSize = hashTable[i].size();
				if(chainSize>1){
					collisions += chainSize-1;
					// chain length calculation
					if(chainSize>maxChain){
						maxChain = chainSize;
						maxChainPos = i;
					}
				}
				entries += chainSize;
			}
		}
		String longestChain = chainToString(hashTable[maxChainPos]);
		
		System.out.println("Number of entries: "+entries);
		System.out.println("Number of collisions: "+collisions);
		System.out.println("Position of longest chain: "+maxChainPos
				          +", Length: "+maxChain);
		System.out.println("Longest chain: \n"+ longestChain);
		System.out.println("Prime: "+prime);
	}
	
	public String chainToString(String[] words){
		String result = "";
		for(String word: words){
			result += word + "\n";
		}
		return result;
	}
	
	public String chainToString(List<String> words){
		String result = "";
		for(String word: words){
			result += word + "\n";
		}
		return result;
	}
	
	public int getHashValue(String word){
		int asciiSum = 0;
		word = word.toLowerCase();
		for(int i=0; i<word.length();i++){
			asciiSum += (int)word.charAt(i);
		}
		int hashValue = asciiSum%prime;
		return hashValue;
	}
	
	public boolean isPermutation(String str1, String str2){
		str1 = normalize(str1.toLowerCase());
		str2 = normalize(str2.toLowerCase());
		return str1.equals(str2);
	}
	
	
	public static String normalize(String str){
		if(str.length()<2){
			return str;
		}
		char[] chars = str.toCharArray();
		Arrays.sort(chars);
		return new String(chars);
	}
	
	public static boolean isPrime(int n){
		if(n<2){
			return false;
		}
		else if(n==2){
			return true;
		}
		else{
			for(int i=2; i < Math.sqrt(n); i++){
				if((n%i)==0){
					return false;
				}
			}
			return true;
		}
	}
	
	public int getLowestAbovePrime(int n){
		for(int i=(n); i<Integer.MAX_VALUE; i++){
			if(isPrime(i)){
				return i;
			}
		}
		return -1;
	}
	
//	public static void main(String[] args) throws IOException{
//		Dictionary s = new Dictionary("C:\\Users\\Jule\\Documents\\UNI\\Semester 2\\Info2\\Eclipse-Workplace\\Scrabble Cheater\\src\\scrabble_words.txt");
//		s.printTableStats();
//	}
}
