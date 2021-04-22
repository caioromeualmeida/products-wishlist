package com.example.products_wishlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableCaching
public class ProductsWishlistApplication {

	@Autowired
	private CacheManager cacheManager;
	
	//Limpa o cache do redis ao iniciar a aplicação
	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationEvent(ApplicationReadyEvent event) {
		cacheManager.getCacheNames()
					.parallelStream()
					.forEach(n -> cacheManager.getCache(n).clear());
	}

	public static void main(String[] args) {
		SpringApplication.run(ProductsWishlistApplication.class, args);
	}
}
