package algos.dp;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

import org.springframework.util.StringUtils;
@Slf4j
public class RodCutting {
	static int p[] = { Integer.MIN_VALUE, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30 };
	Integer r[] = new Integer[p.length];
	Integer s[] = new Integer[p.length];

	public RodCutting() {
		Arrays.fill(r, Integer.MIN_VALUE);
		Arrays.fill(s, 0);
	}
	
	public static int profitDP(int[] value, int length) {
		int[] solution = new int[length + 1];
		for (int i = 1; i <= length; i++) {
			for (int j = 0,max = Integer.MIN_VALUE; j < i; j++) {
				solution[i] =  Math.max(max, value[j] + solution[i - (j + 1)]);
			}
		}
		return solution[length];
	}

	public void cutRod(int n) {
		r[0] = 0;
		for (int j = 1; j < n; j++) {
			for (int i = 1, q = Integer.MIN_VALUE; i <= j; i++)
				if (q < p[i] + r[j - i]) {
					r[j] = q = p[i] + r[j - i];
					s[j] = i;
				}
		}
		log.info("r="+StringUtils.arrayToCommaDelimitedString(r));
		log.info("s="+StringUtils.arrayToCommaDelimitedString(s));
		
	}
	
	public void print(int n){
		while(n>0){
			log.info("{}",s[n]);
			n=n-s[n];
		}
	}
	
	public static void main(String[] args) {
		RodCutting rod = new RodCutting();
		rod.cutRod(11);
		rod.print(6);
		
		log.info("profitDP:"+RodCutting.profitDP(p, 11));
	}
}
