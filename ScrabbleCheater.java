import java.util.*;
import java.io.*;

public class ScrabbleCheater {
	private Dictionary dict;
	
	public ScrabbleCheater() throws IOException{
		dict = new Dictionary("scrabble_fullwords.txt");
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
		
	public List<String> getMatches(String str){
		ArrayList<String> matches = new ArrayList<String>();
		Permutations perm = new Permutations(str);
		Set<String> perms = perm.getPerms();
		for(String s: perms){
			List<String> permutations = getPermutations(s);
			for(String p: permutations){
				matches.add(p);
			}
		}
		
		return matches;
	}
	
	private String generate7String(){
		String result = "";
		for(int i=0; i<7; i++){
			result += randomLetter();
		}
		return result;
	}
	
	private String randomLetter(){
		Random rand = new Random();
		int n = rand.nextInt(26)+97;
		Character c = (char) n;
		return c.toString();
	}
	
	public Dictionary getDict() {
		return dict;
	}

	public static void main(String[] args) throws IOException{
		ScrabbleCheater scrab = new ScrabbleCheater();
		String random = scrab.generate7String(); //Select 7 random letters
		List<String> perms = scrab.getMatches(random); //Calculate matching words
		System.out.println("Your letters: "+random.toUpperCase());
		System.out.println(scrab.getDict().chainToString(perms));
//		String str = "Java";
//		Set<String> bag = new Permutations(str).getPerms();
//		System.out.println(Arrays.toString(bag.toArray(new String[bag.size()])));
	}
}
