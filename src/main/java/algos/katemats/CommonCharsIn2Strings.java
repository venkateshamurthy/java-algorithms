package algos.katemats;

import java.util.LinkedHashSet;
import java.util.Set;

public class CommonCharsIn2Strings {

	public Set<Character> findCommonChars(final String first, final String second){
		Set<Character> resultSet = new LinkedHashSet<Character>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				for(Character c:first.toCharArray()) add(c);
			}
		};
		Set<Character> secondSet = new LinkedHashSet<Character>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				for(Character c:second.toCharArray()) add(c);
			}
		};
		resultSet.retainAll(secondSet);
		return resultSet;
	}
}
