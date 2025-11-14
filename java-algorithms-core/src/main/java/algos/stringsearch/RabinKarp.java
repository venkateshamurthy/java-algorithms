package algos.stringsearch;
// Following program is a Java implementation
// of Rabin Karp Algorithm given in the CLRS book

import java.util.Arrays;

public class RabinKarp {
    public static void main(String [] args) {
        var haystack = "Following program is a Java implementation of Rabin Karp Algorithm given in the CLRS book";
        var needle = "program";
        var result = -1;
        result = rabinKarp(haystack.toCharArray(), needle.toCharArray());
        System.out.println("result:"+result);
    }
    static final int CHARSET = 26, MOD = 29;
    static int rabinKarp(char[] s, char[] p) {
        int n = s.length, m = p.length;


        int pHash = 0, tHash = 0;
        for (int i = 0; i < m; i++) {
            pHash = (pHash * CHARSET + p[i]) % MOD;
            tHash = (tHash * CHARSET + s[i]) % MOD;
        }
        final int hash = getHash(m);
        for (int i = 0; i < n - m; i++) {
            if (pHash == tHash && Arrays.compare(s, i, i+m-1, p,0, m-1) == 0)
                return i;
            if (i + m < n) {
                tHash = (s[i + m] + CHARSET * (tHash - s[i] * hash)) % MOD;
                if (tHash < 0) {
                    tHash += MOD;
                }
            }
        }
        return -1;
    }

    private static int getHash(int m) {
        int hash = 1;
        for (int i = 1; i < m; i++) {
            hash *= CHARSET;
            hash %= MOD;
        }
        return hash;
    }
}