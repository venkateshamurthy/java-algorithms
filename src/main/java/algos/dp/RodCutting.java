package algos.dp;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

import org.springframework.util.StringUtils;
@Slf4j
public class RodCutting {
	int p[] = { Integer.MIN_VALUE, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30 };
	Integer r[] = new Integer[p.length];
	Integer s[] = new Integer[p.length];

	public RodCutting() {
		Arrays.fill(r, Integer.MIN_VALUE);
		Arrays.fill(s, 0);
	}

	public void cutRod(int n) {
		r[0] = 0;
		for (int j = 1; j < n; j++) {
			int q = Integer.MIN_VALUE;
			for (int i = 1; i <= j; i++)
				if (q < p[i] + r[j - i]) {
					q = p[i] + r[j - i];
					s[j] = i;
					r[j] = q;
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
	}
}
