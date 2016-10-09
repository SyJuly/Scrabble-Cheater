import java.util.HashSet;
import java.util.Set;

public class Permutations {
	private Set<String> perms;
	private String str;
	
	public Permutations(String str){
		perms = new HashSet<String>();
		this.str = str;
	}
	
	public void perms(String s){
		if(s.length()==2){
			return;
		}
		for(int i=0; i<s.length();i++){
			String p = "";
			p += s.substring(0, i)+s.substring(i+1, s.length());
			p = p.toLowerCase();
			p = Dictionary.normalize(p);
			perms.add(p);
			perms(p);
		}
	}
	
	public Set<String> getPerms(){
		perms(str);
		Set<String> permutations = perms;
		permutations.add(Dictionary.normalize(str.toLowerCase()));
		perms = null;
		return permutations;
	}
}
