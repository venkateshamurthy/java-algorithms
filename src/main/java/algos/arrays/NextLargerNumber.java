package algos.arrays;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NextLargerNumber {

	/**
	 * given a number whose digits are unique find the next bigger number formed
	 * by those digits
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		log.info("insert one number whose digits are unique");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		try {
			Integer.parseInt(input);
		} catch (NumberFormatException ex) {
			log.error("Not a valid number");
			return;
		}
		if (input.length() == 1) {
			log.error("no greater number possible");
			return;
		}
		
		List<Integer> digits = new ArrayList<Integer>();
		for (int i = 0; i < input.length(); ++i) {
			Integer digit = Integer.parseInt(input.substring(i, i + 1));
			if (digits.contains(digit)) {
				log.info("All digits are not unique");
				return;
			} else {
				digits.add(digit);
			}
		}

		int rightBiggerIndex = -1;
		int leftSmallerIndex = -1;
		for (int rightDigit = digits.size() - 1; rightDigit > 0
				&& rightBiggerIndex == -1; --rightDigit) {
			for (int leftDigit = rightDigit - 1; leftDigit >= 0; --leftDigit) {
				if (digits.get(rightDigit) > digits.get(leftDigit)) {
					rightBiggerIndex = rightDigit;
					leftSmallerIndex = leftDigit;
					log.info(leftDigit+"  "+rightDigit);
					break;
				}
			}
		}
		if (rightBiggerIndex == -1 || leftSmallerIndex == -1) {
			log.info("no greater number possible");
			return;
		}
		swap(digits, rightBiggerIndex, leftSmallerIndex);
		
		sort(digits, leftSmallerIndex + 1, digits.size() - 1);
		StringBuilder sb = new StringBuilder();
		for (int num : digits) {
			sb.append(num);
		}
		log.info("{}",sb.toString());

	}

	private static void sort(List<Integer> digits, int startIndex, int endIndex) {
		if (startIndex == endIndex)
			return;
		for (int k = startIndex; k < endIndex; ++k)
			for (int l = startIndex + 1; l <= endIndex; ++l) {
				if (digits.get(k) > digits.get(l))
					swap(digits, k, l);
			}

	}

	private static void swap(List<Integer> digits, int i, int j) {
		Integer temp = digits.get(i);
		digits.set(i, digits.get(j));
		digits.set(j, temp);
	}
}