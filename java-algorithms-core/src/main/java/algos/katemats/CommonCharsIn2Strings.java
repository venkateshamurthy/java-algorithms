package algos.katemats;

import com.google.common.collect.Sets;

import java.util.Set;
import java.util.stream.Collectors;

public class CommonCharsIn2Strings {

	public static void main(String[] args) {
		System.out.println("Common:"+findCommonChars("gilberth","therbligs"));
	}

	public static Set<Character> findCommonChars(final String first, final String second){
		final Set<Character> A = first.chars().mapToObj(i->(char)i)
				.collect(Collectors.toSet());

		final Set<Character> B = second.chars().mapToObj(i->(char)i)
				.collect(Collectors.toSet());

		return Sets.intersection(A, B);
	}
}
