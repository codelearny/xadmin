package com.enjoyu.admin.component.cache;

import com.enjoyu.admin.component.auth.entity.User;
import com.google.common.collect.Lists;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;


@CacheConfig(cacheNames = {"demoCache"})
public class CacheDemoService {

    @Cacheable(key = "'l12'")
    public List<String> list() {
        System.out.println("@Cacheable缓存数据：key为l12，value为返回值List");
        return Lists.newArrayList("1", "2");
    }

    @CacheEvict(key = "'l12'")
    public List<String> rm() {
        System.out.println("@CacheEvict从缓存中删除key为l12的数据");
        return Lists.newArrayList("1", "2");
    }

    @Cacheable(key = "#id")
    public User getById(Integer id) {
        System.out.println("@Cacheable缓存数据：key为" + id + " value为返回值User");
        return new User();
    }

    @CachePut(key = "#user.id")
    public void save(User user) {
        System.out.println("@CachePut缓存新增的或更新的数据到缓存，key为" + user.getId());
    }

    @CacheEvict(allEntries = true)
    public void rmAll() {
        System.out.println("清空所有缓存");
    }
}
