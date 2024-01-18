/**
 * 
 */
package algos.utils;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

//Using lombok annotation for log4j handle
/**
 * @author vmurthy
 *
 */
//Log4j Handle creator (from lombok)

@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Utils {
	
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
