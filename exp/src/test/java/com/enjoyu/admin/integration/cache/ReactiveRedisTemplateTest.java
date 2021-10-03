package com.enjoyu.admin.integration.cache;

import com.enjoyu.admin.config.RedisCacheConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import reactor.core.publisher.Mono;

@SpringBootTest(classes = {RedisCacheConfig.class, RedisAutoConfiguration.class, RedisReactiveAutoConfiguration.class})
public class ReactiveRedisTemplateTest {

    @Autowired
    private ReactiveStringRedisTemplate reactiveStringRedisTemplate;
    @Autowired
    private ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

    @Test
    public void testString() {
        Mono<Boolean> set = reactiveStringRedisTemplate.opsForValue().set("strKey", "黄河之水天上来");
        set.subscribe(System.out::println);
        Mono<String> mono = reactiveStringRedisTemplate.opsForValue().get("strKey");
        mono.subscribe(System.out::println);
    }

    @Test
    public void testSerializable() {
        User user = new User();
        user.setId(123);
        user.setName("龙傲天");
        Mono<Boolean> set = reactiveRedisTemplate.opsForValue().set("user", user);
        set.subscribe(System.out::println);
        Mono<Object> mono = reactiveRedisTemplate.opsForValue().get("user");
        mono.subscribe(System.out::println);
    }

}
