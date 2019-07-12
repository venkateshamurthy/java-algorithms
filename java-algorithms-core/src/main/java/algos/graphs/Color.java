/**
 * 
 */
package algos.graphs;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;//Using lombok annotation for log4j handle

/**
 * @author vmurthy
 *
 */
// Log4j Handle creator (from lombok)
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum Color {
	WHITE("Unvisited"), GRAY("Visit Initiated"), BLACK("Visited");
	@Getter
	String meaning;
};