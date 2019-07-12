package algos.strings;

import algos.dp.EditDistance;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
/**
 * This class is present for the purpose being a string related algorithm and hence rightly placed in this package.
 * however this algorithm is essentially a dynamic-programming technique and wherefore make use of {@link EditDistance}.
 * @author vemurthy
 *
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LevenshtienDistance {
	EditDistance editDistance = new EditDistance();

	public int distance(String str1, String str2) {
		return editDistance.computeLevenshteinDistance(str1, str2);
	}
}
