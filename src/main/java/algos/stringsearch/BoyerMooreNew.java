package algos.stringsearch;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BoyerMooreNew {
  static Map<Character, Integer> lastOccuranceMap;

  public static void main(String[] args) {
    String text = "myexperimentsandexperiencewiththetruth";
    /**
     * <pre>
     * ........// "experience"
     * .................// "experience"
     * </pre>
     */
    String pattern = "experience";// lastOccurance=
                                  // {e=9,x=1,p=2,r=4,i=5,n=7,c=8}
    log.debug(text);
    int index = findBoyerMoore(text, pattern);
    log.info("BM search:{}, {}", index, text.substring(index, index + pattern.length()));
    Assert.isTrue(index == text.indexOf(pattern));
  }

  /**
   * Returns the lowest index at which substring pattern begins in text (or else
   * ?1).
   */
  public static int findBoyerMoore(String textStr, String patternStr) {
    Assert.isTrue(StringUtils.isNotEmpty(textStr) && StringUtils.isNotEmpty(patternStr));
    char[] text=textStr.toCharArray(), pattern=patternStr.toCharArray();
    final int n = text.length;
    final int m = pattern.length;
    lastOccuranceMap = buildLastOccuranceMap(pattern);
    int t = m, p = m; // an index into the text and pattern
    while (t < n) {
      if (text[--t] != pattern[--p]) { // a matching character
        log.info("m-p={} m-lasstOccurance={} text={}",m-p,m - lastOccuranceIndex(text[t]),textStr.substring(m-p));
        t += Math.max(m - p, m - lastOccuranceIndex(text[t]));
        p = m; // restart at end of pattern
      } else if (p == 0)
        return t + 1; // entire pattern has been found
    }
    return -1; // pattern was never found
  }

  /**
   * @param c
   * @return
   */
  public static int lastOccuranceIndex(char c) {
    return MapUtils.getIntValue(lastOccuranceMap, c, -1);
  }

  /**
   * Last occurance
   * 
   * @param pattern
   * @return
   */
  public static Map<Character, Integer> buildLastOccuranceMap(char[] pattern) {
    Map<Character, Integer> lastOccuranceMap = new HashMap<>(); // the 'last'
                                                                // map
    for (int k = 0; k < pattern.length; k++)
      lastOccuranceMap.put(pattern[k], k); // rightmost occurrence in pattern is
                                           // last
    log.info(lastOccuranceMap.toString());
    return lastOccuranceMap;
  }

}
