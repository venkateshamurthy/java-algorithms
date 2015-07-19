/**
 * 
 */
package algos.stringsearch;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;//Using lombok annotation for log4j handle

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.FastMath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;
import org.springframework.util.Assert;

/**
 * @author vmurthy
 * 
 */
// Log4j Handle creator (from lombok)
@Log4j2
@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class BoyerMoore implements StringSearcher {
	static final Logger log = LogManager
			.getLogger(StringFormatterMessageFactory.INSTANCE);

	/** The supported character set size (ASCII). */
	static int CHARSET_SIZE = 256;

	/** The pattern for which to search. */
	String pattern;
	char[] reversePattern;
	/**
	 * The position (0, 1, 2...) of the last occurrence of each character within
	 * the pattern.
	 */
	Map<Character, Integer> lastOccurance = new LinkedHashMap<Character, Integer>();

	/**
	 * Search
	 */
	@Override public int search(String text, int from) {
		while (from <= text.length() - pattern.length()) {
			char c = 0;
			int i = pattern.length() - 1;

			while (i >= 0) {
				char p = pattern.charAt(i);
				c = text.charAt(from + i);
				if (p == c)
					--i;
				else
					break;
			}

			if (i < 0)
				return from;
			int last_Occurance = -1;
			if (c != 0) {
				Integer r = lastOccurance.get(c);
				if (r != null)
					last_Occurance = r;
			}
			log.debug("i="+i+" lastOccurance("+c+")="+last_Occurance+" jump="+FastMath.max(i - last_Occurance, 1));
			from += FastMath.max(i - last_Occurance, 1);
		}
		return -1;
	}

	protected BoyerMoore computeLastOccurance() {
		for (int index = 0; index < pattern.length(); index++)
			lastOccurance.put(pattern.charAt(index), index);
		return this;
	}

	public static void main(String[] args) {
		String text =   "myexperimentsandexperiencewiththetruth";
		/**<pre>
		              //"experience"
		                       //"experience"
		</pre>                      
		*/
		String pattern =                "experience";//lastOccurance= {e=9,x=1,p=2,r=4,i=5,n=7,c=8}
		log.debug(text);
		log.debug(pattern+" lastOccurance= {e=9,x=1,p=2,r=4,i=5,n=7,c=8}");
		BoyerMoore bm = BoyerMoore.Builder.builder().pattern(pattern).build();
		int index = bm.search(text, 0);
		Assert.isTrue(index!=-1);
	}

	@Data(staticConstructor = "builder")
	public static class Builder extends BoyerMooreBuilder {
		public BoyerMoore build() {
			Assert.hasText(super.pattern);
			super.reversePattern(StringUtils.reverse(super.pattern)
					.toCharArray());
			return super.build().computeLastOccurance();
		}
	}
}
