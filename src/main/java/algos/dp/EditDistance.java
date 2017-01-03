package algos.dp;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.math3.util.FastMath;
import org.springframework.util.Assert;
@Data
@Slf4j
public class EditDistance {
	
	public static void main(String[] args){
		EditDistance ed=new EditDistance();
		log.info("Distance between infosys and invensys is {}",ed.computeLevenshteinDistance("infosys","invensys"));
		log.info("Distance between qualcom and broadcom is {}",ed.computeLevenshteinDistance("qualcom","broadcom"));
		log.info("Distance between alcatel and nortel is {}",ed.computeLevenshteinDistance("alcatel","nortel"));
		log.info("Distance between yajna and vydya is {}",ed.computeLevenshteinDistance("yajna","vydya"));
	}
	private static int minimum(int a, int b, int c) {
		return FastMath.min(FastMath.min(a, b), c);
	}

	public int computeLevenshteinDistance(String str1, String str2) {
		Assert.notNull(str1);
		Assert.notNull(str2);
		int[][] distance = new int[str1.length() + 1][str2.length() + 1];

		for (int i = 0; i <= str1.length(); i++)
			distance[i][0] = i;//b'cos from 0 to so many characters to be added
		for (int j = 1; j <= str2.length(); j++)
			distance[0][j] = j;//b'cos from 0 to so many characters to be added

		for (int i = 1; i <= str1.length(); i++)
			for (int j = 1; j <= str2.length(); j++)
				distance[i][j] = minimum(
						distance[i - 1][j] + 1,
						distance[i][j - 1] + 1,
						distance[i - 1][j - 1]
								+ ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0
										: 1));

		return distance[str1.length()][str2.length()];
	}
}
