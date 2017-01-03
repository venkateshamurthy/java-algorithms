package algos.stringsearch;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BoyerMooreNew {

	public static void main(String[] args) {
		String text = "myexperimentsandexperiencewiththetruth";
		/**
		 * <pre>
		 * // "experience"
		 * // "experience"
		 * </pre>
		 */
		String pattern = "experience";// lastOccurance=
										// {e=9,x=1,p=2,r=4,i=5,n=7,c=8}
		log.debug(text);
		int index = findBoyerMoore(text.toCharArray(), pattern.toCharArray());
		log.info("BM search:{}, {}", index, text.substring(index, index + pattern.length()));
	}

	/**
	 * Returns the lowest index at which substring pattern begins in text (or
	 * else ?1).
	 */
	public static int findBoyerMoore(char[] text, char[] pattern) {
		int n = text.length;
		int m = pattern.length;
		if (m == 0)
			return 0; // trivial search for empty string
		Map<Character, Integer> lastOccuranceMap = new HashMap<>(); // the 'last' map
		for (int k = 0; k < m; k++)
			lastOccuranceMap.put(pattern[k], k); // rightmost occurrence in pattern is last
		log.info(lastOccuranceMap.toString());
		// start with the end of the pattern aligned at index m?1 of the text
		int t = m - 1; // an index into the text
		int p = m - 1; // an index into the pattern
		while (t < n) {
			if (text[t] == pattern[p]) { // a matching character
				if (p == 0)
					return t; // entire pattern has been found
				t--; // otherwise, examine previous
				p--; // characters of text/pattern
			} else {
				int lastOccuranceIndex = MapUtils.getIntValue(lastOccuranceMap, text[t], -1);
				t += m - Math.min(p, 1 + lastOccuranceIndex);
				p = m - 1; // restart at end of pattern
			}
		}
		return -1; // pattern was never found
	}

}
