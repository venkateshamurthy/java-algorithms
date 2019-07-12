package javamemory;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import lombok.extern.java.Log;
@Log
public class TestWeakHashMap {
	public static void main(String[] args) throws InterruptedException{
		TestWeakHashMap mapTest = new TestWeakHashMap();
		mapTest.testWeakHashMap();
	}
	public void testWeakHashMap() throws InterruptedException {
		// -- Fill a weak hash map with one entry
		WeakHashMap<Data, String> map = new WeakHashMap<Data, String>();
		Data someDataObject = new Data("foo");
		map.put(someDataObject, someDataObject.value);
		log.info("map contains someDataObject ? "
				+ map.containsKey(someDataObject));

		// -- now make someDataObject elligible for garbage collection...
		someDataObject = null;

		for (int i = 0; i < 10000; i++,TimeUnit.SECONDS.sleep(1)) {
			if (map.size() != 0) {
				log.info("At iteration "
								+ i
								+ " the map still holds the reference on someDataObject");
				if(i>10){
					log.info("Oh!, it is still clinging!! so let a GC trigger happen!!!");
					System.gc();
				}
				
			} else {
				log.info("somDataObject has finally been garbage collected at iteration "
								+ i + ", hence the map is now empty");
				break;
			}
		}
	}

	private static class Data {
		String value;

		Data(String value) {
			this.value = value;
		}
	}
}
