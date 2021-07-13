package com.enjoyu.admin.integration.cache;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.cache.annotation.Cacheable;

@TestComponent
public class CacheService {
    @Cacheable(cacheNames = "poem", key = "#name")
    public String getFromDB(String name) {
        System.out.printf("db search %s%n", name);
        return "床前明月光，疑是地上霜。举头望明月，低头思故乡。";
    }
}
