/**
 * 
 */
package algos.graphs;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;//Using lombok annotation for log4j handle
/**
 * @author vmurthy
 *
 */
//Log4j Handle creator (from lombok)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum Color {WHITE, GRAY, BLACK};