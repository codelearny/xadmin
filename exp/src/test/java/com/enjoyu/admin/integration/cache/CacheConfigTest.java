package com.enjoyu.admin.integration.cache;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


@AutoConfigureCache(cacheProvider = CacheType.SIMPLE)
@SpringJUnitConfig({CacheService.class})
public class CacheConfigTest {
    @Autowired
    private CacheService cacheService;
    @Autowired
    CacheManager cacheManager;

    @Test
    void testNotNull() {
        System.out.println(cacheManager.getCacheNames());
    }

    @ParameterizedTest
    @ValueSource(strings = {"静夜思", "静夜思", "相思"})
    void testCache(String name) {
        String fromDB = cacheService.getFromDB(name);
        System.out.println(fromDB);
    }

}
