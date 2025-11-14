package yajna;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TestLoadingCache {
    public static void main(String[] args) {
        LoadingCache<Integer, String> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(5000,TimeUnit.MILLISECONDS)
                .refreshAfterWrite(5000, TimeUnit.MILLISECONDS)
                .build(
                        new CacheLoader<Integer, String>() {
                            @Override
                            public String load(Integer key) throws Exception {
                                TimeUnit.SECONDS.sleep(1);
                                return key+"";
                            }
                        });
        Random random = new Random();
        while(true) {
            int key = random.nextInt(100);
            System.out.println("key:"+key+":"+cache.getUnchecked(key));
            System.out.println("Map:"+cache.asMap());
        }
    }
}
