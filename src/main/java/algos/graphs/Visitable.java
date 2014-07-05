/**
 * 
 */
package algos.graphs;
//Using lombok annotation for log4j handle
/**
 * @author vmurthy
 *
 */
public interface Visitable<T>{
	//void pre();
	void accept(Visitor<Visitable<T>,?,?> visitor);
	//void post();
}
