package basics;
import java.util.*;
public class TreeSetExample {
    public static void main (String[] args) {
        TreeSet ts = new TreeSet();
        ts.add(3);
        ts.add(1);
        ts.add(2);
        System.out.println(ts);
        for (Iterator i = ts.iterator(); i.hasNext();) {
            System.out.println("TS:"+i.next());
        }
    }

}

class HashSetExample {
    public static void main (String[] args) {
        HashSet nonDuplicate = new HashSet();
        HashSet duplicate = new HashSet();
        String s = "Big black bug bit Big Black Dog On His big black Nose";
        String s1 = s.toLowerCase();
        String[] words = s1.split(" ");

        for (int i = 0; i < words.length; i++) {
            //if (!nonDuplicate.add(words[i])) duplicate.add(words[i]);
            if (! nonDuplicate.contains(words[i])) {
                nonDuplicate.add(words[i]);
            } else {
                duplicate.add(words[i]);
            }
        }
        System.out.println("Duplicate:"+duplicate);
    }
}