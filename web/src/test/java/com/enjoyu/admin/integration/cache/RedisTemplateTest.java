package com.enjoyu.admin.integration.cache;

import com.enjoyu.admin.config.RedisCacheConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = {RedisCacheConfig.class, RedisAutoConfiguration.class})
public class RedisTemplateTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, Object> serializableRedisTemplate;

    @Test
    public void testStringValue() {
        String testKey = "hello";
        // SET hello "123"
        stringRedisTemplate.opsForValue().set(testKey, "123");
        // INCRBY hello 2
        stringRedisTemplate.opsForValue().increment(testKey, 2L);
        // SETEX hello 5 "has expire time"
        stringRedisTemplate.boundValueOps(testKey).set("has expire time", Duration.ofSeconds(5L));
        // GET hello
        System.out.println(stringRedisTemplate.opsForValue().get(testKey));
        // TTL hello
        Long aLong = stringRedisTemplate.getExpire(testKey);
        System.out.println(aLong);
        // EXPIRE hello 10
        stringRedisTemplate.expire(testKey, Duration.ofSeconds(10L));
        // EXISTS hello
        Boolean hasKey = stringRedisTemplate.hasKey(testKey);
        System.out.println(hasKey);
        // DEL hello
        stringRedisTemplate.delete(testKey);
    }

    @Test
    public void testStringHash() {
        String testKey = "hello";
        String hashKey = "hash-key-";
        // HSET hello hash-key-1 "前不见古人"
        stringRedisTemplate.opsForHash().put(testKey, hashKey + 1, "前不见古人");
        // HGET hello hash-key-1
        System.out.println(stringRedisTemplate.opsForHash().get(testKey, hashKey + 1));
        HashMap<String, String> kvm = new HashMap<>();
        kvm.put(hashKey + 2, "后不见来者");
        kvm.put(hashKey + 3, "念天地之悠悠");
        kvm.put(hashKey + 4, "独怆然而涕下");
        // HMSET hello hash-key-2 "后不见来者" hash-key-3 "念天地之悠悠" hash-key-4 "独怆然而涕下"
        stringRedisTemplate.boundHashOps(testKey).putAll(kvm);
        // HMGET hello hash-key-1 hash-key-2
        List<Object> objects = stringRedisTemplate.boundHashOps(testKey).multiGet(Arrays.asList(hashKey + 1, hashKey + 2));
        System.out.println(objects);
        // HGETALL hello
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(testKey);
        System.out.println(entries);
        // PEXPIRE hello 10000
        stringRedisTemplate.expire(testKey, 10L, TimeUnit.SECONDS);
        // HKEYS hello
        Set<Object> keys = stringRedisTemplate.opsForHash().keys(testKey);
        System.out.println(keys);
        // HVALS hello
        List<Object> values = stringRedisTemplate.boundHashOps(testKey).values();
        System.out.println(values);
        // HEXISTS hello hash-key-5
        Boolean hasKey = stringRedisTemplate.boundHashOps(testKey).hasKey(hashKey + 5);
        System.out.println(hasKey);
        // HDEL hello hash-key-1
        stringRedisTemplate.opsForHash().delete(testKey, hashKey + 1);
        // DEL hello
        stringRedisTemplate.delete(testKey);
    }

    @Test
    public void testStringSet() {
        String testKey = "hello";
        // SADD hello "临" "兵" "斗" "者"
        stringRedisTemplate.opsForSet().add(testKey, "临", "兵", "斗", "者");
        // SMEMBERS hello
        Set<String> members = stringRedisTemplate.opsForSet().members(testKey);
        System.out.println(members);
        // SISMEMBER hello "皆"
        Boolean isMember = stringRedisTemplate.opsForSet().isMember(testKey, "皆");
        System.out.println(isMember);
        // SREM hello "斗"
        stringRedisTemplate.boundSetOps(testKey).remove("斗");
        // SSCAN hello 0 COUNT 4
        stringRedisTemplate.opsForSet().scan(testKey, ScanOptions.scanOptions().count(4L).build());
        // DEL hello
        stringRedisTemplate.delete(testKey);
    }

    @Test
    public void testStringList() {
        String testKey = "hello";
        // LPUSH hello "临" "兵" "斗" "者"
        stringRedisTemplate.opsForList().leftPushAll(testKey, "临", "兵", "斗", "者");
        // RPUSHX hello "兵"
        stringRedisTemplate.opsForList().rightPushIfPresent(testKey, "兵");
        // BLPOP hello 4
        String leftPop = stringRedisTemplate.opsForList().leftPop(testKey, Duration.ofSeconds(4L));
        System.out.println(leftPop);
        // LINDEX hello 1
        String index = stringRedisTemplate.opsForList().index(testKey, 1);
        System.out.println(index);
        // LRANGE hello 1 3
        List<String> range = stringRedisTemplate.boundListOps(testKey).range(1, 3);
        System.out.println(range);
        // LREM hello -1 "兵"
        stringRedisTemplate.opsForList().remove(testKey, -1L, "兵");
        // DEL hello
        stringRedisTemplate.delete(testKey);
    }

    @Test
    public void testStringZset() {
        String testKey = "hello";
        // ZADD hello 10 "临"
        stringRedisTemplate.opsForZSet().add(testKey, "临", 10D);
        // ZADD hello 11 "斗" 12 "者"
        DefaultTypedTuple<String> t1 = new DefaultTypedTuple<>("斗", 11D);
        DefaultTypedTuple<String> t2 = new DefaultTypedTuple<>("者", 12D);
        DefaultTypedTuple<String> t3 = new DefaultTypedTuple<>("皆", 20D);
        stringRedisTemplate.boundZSetOps(testKey).add(new HashSet<>(Arrays.asList(t1, t2)));
        // ZRANGE hello 5 15
        Set<String> range = stringRedisTemplate.opsForZSet().range(testKey, 5L, 15L);
        System.out.println(range);
        // ZRANK hello "者"
        Long zhe = stringRedisTemplate.opsForZSet().rank(testKey, "者");
        System.out.println(zhe);
        // ZINCRBY hello 10 "斗"
        stringRedisTemplate.opsForZSet().incrementScore(testKey, "斗", 10D);
        // ZREMRANGEBYSCORE hello 0 10
        stringRedisTemplate.opsForZSet().removeRangeByScore(testKey, 0D, 10D);
        // DEL hello
        stringRedisTemplate.delete(testKey);
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

    @Test
    public void testExec() {
        String key = "aaa";
        String field = "pwd";
        String value = "离离原上草，一岁一枯荣";
        stringRedisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] bytes = connection.hGet(key.getBytes(), field.getBytes());
            if (bytes != null) {
                System.out.println(new String(bytes));
            }
            return connection.hSet(key.getBytes(), field.getBytes(), value.getBytes());
        });
    }


}
