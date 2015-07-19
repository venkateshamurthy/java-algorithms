package algos.strings;

import lombok.Data;
import lombok.extern.java.Log;
import net.sf.oval.constraint.CheckWithCheck;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

@Log
@Data(staticConstructor="of")
public class ResverseWordsInAString {
//Remember: its easier to reverse the entire string and then re-reverse the the words.
	public static void main(String[] args) {
		char[] chars = "rama went to forrest".toCharArray();
		log.info("As per string utils:"+StringUtils.reverseDelimited(new String(chars), ' '));
		ResverseWordsInAString reverser = ResverseWordsInAString.of();
		reverser.reverseWords(chars);
		log.info("As Per our logic:"+new String(chars));
		
	}
	//its better first to reverse the character array before reversing the words
	private char[] reverse(@MinSize(1) @NotNull char[] chars, int start, int end) {
		if (start < 0 || end >= chars.length)
			throw new IllegalArgumentException();
		while (start < end) {
			swap(chars, start++, end--);
		}
		return chars;
	}

	public void reverseWords(@NotNull @MinSize(1) char[] chars) {
		//first reverse entirely
		chars = reverse(chars, 0, chars.length - 1);
		for (int start = 0, spaceIdx = start; spaceIdx < chars.length; spaceIdx++) {
			// while (spaceIdx<chars.length && chars[spaceIdx] != ' ')
			// spaceIdx++;
			spaceIdx = ArrayUtils.indexOf(chars, ' ', start);
			spaceIdx = spaceIdx == ArrayUtils.INDEX_NOT_FOUND ? chars.length : spaceIdx;
			//re-reverse only the words will now lead "rama went to forrest" to "forrest to went rama"
			chars = reverse(chars, start, spaceIdx - 1);
			start = spaceIdx + 1;
		} 
	}

	private static void swap(@MinSize(1) @NotNull char[] es, int left, int right) {
		if (left > right || right >= es.length)
			throw new IllegalArgumentException();
		char temp = es[left];
		es[left] = es[right];
		es[right] = temp;
	}

}