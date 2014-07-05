/**
 * 
 */
package algos.lists;
import java.util.Arrays;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;//Using lombok annotation for log4j handle

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;
/**
 * @author vmurthy
 *
 */
//Log4j Handle creator (from lombok)
@Log4j2
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NextHigherNumberinArray {
	static final Logger log = LogManager
			.getLogger(StringFormatterMessageFactory.INSTANCE);
	
	final int[] a= {8,7,1,3,4,5,6,10,13,9,11};
	public void printNextHigherNumber() {
		class IndexedA implements Comparable<IndexedA>{
			public IndexedA(int i, int i2) {
				value=i;index=i2;
			}
			int value;
			int index;
			
			@Override public int compareTo(IndexedA o) {
				if(value==o.value)return 0;
				else return(value>o.value)?1:-1;
			}
		}
		IndexedA[] b = new IndexedA[a.length];
		for(int i=0;i<a.length;i++)
			b[i]=new IndexedA(a[i],i);
		Arrays.sort(b);
		for(int i=0;i<a.length-1;i++)
			log.debug(a[i]+" "+b[i+1].value+" "+b[i+1].index);
	}
	
	public static void main(String [] args) {
		new NextHigherNumberinArray().printNextHigherNumber();
	}
}
