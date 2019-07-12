package algos.bits;

import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.stream.IntStream;

import org.apache.commons.lang.ArrayUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FindMaxConsecutive1s {
  public static int findMax1s(byte[] ba) {
    return findMax1s(BitSet.valueOf(ba));
  }

  static boolean isValid(BitSet bitset, int from) {
    return from >= 0 && from < bitset.size();
  }
  static int countConsecutiveOnes(long n)
  {
      long m = n;
      int k = 0;

      while (m!=0)
      {
          ++k;
          n >>= 1;
          m &= n;
      }

      return k;
  }
  public static int findMax1s(BitSet bitset) {
    int maxOnes = 0;
    for (int thisOne = bitset.nextSetBit(0); isValid(bitset, thisOne);) {
      int upcomingZero = bitset.nextClearBit(thisOne);
      if (isValid(bitset, upcomingZero)) {
        if (upcomingZero - thisOne > maxOnes)
          maxOnes = upcomingZero - thisOne;
        thisOne = bitset.nextSetBit(upcomingZero);
      } else
        thisOne = Integer.MAX_VALUE;
    }
    return maxOnes;
  }

  public static void main(String[] args) {
    BitSet bitset = new BitSet(16);
    for(int i = 0; i < 16; i++) {
         if((i % 2) == 0) bitset.set(i);
         if((i % 5) != 0) bitset.set(i);
    }
    log.debug(bitset.size() + " " + bitset.toString() + " " + findMax1s(bitset)+" "+countConsecutiveOnes(255));

  }
}
