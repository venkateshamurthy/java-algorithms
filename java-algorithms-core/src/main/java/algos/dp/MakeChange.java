package algos.dp;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class MakeChange {
	public static void main(String args[]) {
		makeChange(new int[] {1,2,5,10,20,50,100,500,1000,2000}, 12688);
	}

	public static void makeChange(int d[], int n) {
		Map<Integer,Integer> map= new LinkedHashMap<>();
		for (int i = d.length - 1; i >= 0; i--) {
			if (n >= d[i]) {
				map.put(d[i], n / d[i]);
				n %= d[i];
			}
		}
		System.out.println("Coins=" + ToStringBuilder.reflectionToString(d) );
		System.out.println("Coins=" + (map));
	}
}
