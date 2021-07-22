package maybank.project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TransactionUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionUiApplication.class, args);
	}
	
	@Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<Cache> caches = new ArrayList<Cache>();
        caches.add(new ConcurrentMapCache("jsMessages"));
        caches.add(new ConcurrentMapCache("findAll"));
        
        
        cacheManager.setCaches(caches);
        return cacheManager;
    }

}
