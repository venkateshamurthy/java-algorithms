package algos.stringsearch;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.java.Log;
import net.sf.oval.constraint.AssertFieldConstraints;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Knuth-Morris-Pratt Algorithm for Pattern Matching
 */
@Data(staticConstructor = "of")
@Log
@Accessors(fluent = true, chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KMP {
	static String input = "abcabdabcabdabcabdabcabdabc";
	static String pat= "abcabdabc";
	static {
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"%1$tF %1$tT [%4$s]: %5$s %n");
	}
	
	@NonNull String text;
	@NonNull String pattern;
	
	@NonFinal int[] failure;
	@NonFinal int   matchPoint;

	private KMP(@AssertFieldConstraints String text, @AssertFieldConstraints String pattern) {
		this.text = text;
		this.pattern = pattern;
		failure = preProcessPattern(pattern.toCharArray());
	}

	public static void main(String[] args) {
		log.info("Running KMP String search algorithm");
		KMP kmpMatch = new KMP(input, pat);
		log.info(kmpMatch.match() + "  "+ pat+" is found at "
				+ kmpMatch.matchPoint() + " of the string :" + input);
	}

	/**
	 * Finds the first occurrence of the pattern in the text.
	 */
	List<Integer> matches=new ArrayList<>();
	
	public boolean match() {
		int i = 0, j = 0;
        while ( i < text.length()) {
            j = findMatchIndex(i, j);
            i++;
            j++;
 
            // a match is found
            if (j == pattern.length()) {
            	matchPoint = (i  - pattern.length());
                log.info("found substring at index:" + matchPoint);
                j = failure[j];
                matches.add(matchPoint);
                //break;
            }
        }
        return !matches.isEmpty();
	}
	/**
	 * Finds pattern char index that will match the text character at i
	 * @param ithTextChar
	 * @param jthPatternChar
	 * @return jthPatternChar
	 */
	private int findMatchIndex(int ithTextChar, int jthPatternChar) {
		while (jthPatternChar >= 0 && text.charAt(ithTextChar) != pattern.charAt(jthPatternChar)) 
		    jthPatternChar = failure[jthPatternChar];
		return jthPatternChar;
	}

	/**
	 * Pre processes the pattern array based on proper prefixes and proper
	 * suffixes at every position of the array
	 *
	 * @param pattern
	 *            word that is to be searched in the search string
	 * @return partial match table which indicates
	 */
	public int[] preProcessPattern(char[] pattern) {
		int i = 0, j = -1;
		failure = new int[pattern.length + 1];
		failure[0] = -1; 
		while (i < pattern.length) {
			j = findMatchIndex(i, j);
			failure[++i] = ++j;
		}
		log.info(ArrayUtils.toString(failure));
		return failure;
	}

	/**
	 * Computes the failure function using a boot-strapping process, where the
	 * pattern is matched against itself.
	 */
	private void computeFailure() {
		assert failure.length == pattern.length();
		int j = 0;
		for (int i = 1; i < pattern.length(); i++) {
			while (j > 0 && pattern.charAt(j) != pattern.charAt(i)) {
				j = failure[j - 1];
			}
			if (pattern.charAt(j) == pattern.charAt(i)) {
				j++;
			}
			failure[i] = j;
		}
		log.info("Initial failur array:" + ArrayUtils.toString(failure));
	}
}