package com.enjoyu.admin.integration.cache;

import com.enjoyu.admin.cache.config.RedisCacheConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest(classes = {RedisCacheConfig.class, RedisAutoConfiguration.class})
public class RedisTemplateTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, Object> serializableRedisTemplate;

    @Test
    public void testString() {
        stringRedisTemplate.opsForValue().set("strKey", "zwqh");
        System.out.println(stringRedisTemplate.opsForValue().get("strKey"));
    }

    @Test
    public void testSerializable() {
        User user = new User();
        user.setId(321);
        user.setName("叶良辰");
        serializableRedisTemplate.opsForValue().set("user", user);
        User user2 = (User) serializableRedisTemplate.opsForValue().get("user");
        Assertions.assertEquals(user, user2);
    }

}
