/**
 * 
 */
package algos.utils;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
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
public class Utils {
	static final Logger log = LogManager
			.getLogger(StringFormatterMessageFactory.INSTANCE);
	
	public static <T> void swap(T[] t,int i,int j) {
		if(i!=j) {
			T temp=t[i];
			t[i]=t[j];
			t[j]=temp;
		}
	}
	public static  void swap(int[] t,int i,int j) {
		if(i!=j && t[i] != t[j]) {
			int temp=t[i];
			t[i]=t[j];
			t[j]=temp;
		}
	}
}
