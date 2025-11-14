package algos.newtrees;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class Tree <T>{
    public static void main(String[] ar) {
        System.out.println(count(5));
        System.out.println(calculate_power(10000L, 10000000L));
    }
    static AtomicLong result = new AtomicLong(0);
    static long count(int n) {
            switch(n) {
                case 0:
                case 1: return 1L;
                case 2: return 2L;
                case 3: return 5L;
                default: {
                    return IntStream.range(0,n)
                            .mapToLong(i->
                                    count(i)*count(n-i-1))
                            .reduce(Long::sum)
                            .getAsLong();
                }
            }
        }
    static long mod = 1000000007L;
    static Integer calculate_power(Long a, Long b) {
        // Write your code here.
        if (b <  0L) return 0;
        if (b == 0L) return 1;
        if (b == 1) return (int) (a%mod);
        long tmp = calculate_power(a, b/2) ;
        //tmp *= tmp;
        int result = (int) (b % 2 == 0 ? (tmp%mod *tmp%mod) % mod  : (a%mod * tmp%mod * tmp%mod) % mod);
        return result;
    }
}
