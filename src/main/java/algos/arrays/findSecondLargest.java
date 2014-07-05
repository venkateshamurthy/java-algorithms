/**
 * 
 */
package algos.arrays;
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
public class findSecondLargest {
	static final Logger log = LogManager
			.getLogger(StringFormatterMessageFactory.INSTANCE);
	
	static int [] A = {4,5,1,2,8,7,9,6};
	
	public static void main(String [] args) {
		int max=Integer.MIN_VALUE, nextMax=Integer.MIN_VALUE;
		for(int i:A) {
			if(max<i) {
				nextMax=max;
				max=i;
			}
			else if(nextMax<max && nextMax<i)
				nextMax=i;
		}
		log.info("Max="+max+" nextMax="+nextMax);
	}
}
