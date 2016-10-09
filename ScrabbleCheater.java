import java.util.*;
import java.io.*;

public class ScrabbleCheater {
	private Dictionary dict;
	
	public ScrabbleCheater() throws IOException{
		dict = new Dictionary("C:\\Users\\Jule\\Documents\\UNI\\Semester 2\\Info2\\Eclipse-Workplace\\Scrabble Cheater\\src\\scrabble_words.txt");
	}
	
	public List<String> getPermutations(String str){
		ArrayList<String> permutations = new ArrayList<String>();
		LinkedList<String> sameHashed = dict.findSameHashed(str);
		
		for(String word: sameHashed){
			if(dict.isPermutation(str, word))
				permutations.add(word);
		}
		return permutations;
	}
	
	public Dictionary getDict() {
		return dict;
	}

	public static void main(String[] args) throws IOException{
		ScrabbleCheater scrab = new ScrabbleCheater();
		List<String> perms = scrab.getPermutations("gaiastn");
		System.out.println(scrab.getDict().chainToString(perms));
	}
}
