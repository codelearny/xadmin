package com.enjoyu.admin.mbp;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.enjoyu.admin.components.mbp.entity.User;
import com.enjoyu.admin.components.mbp.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDateTime;

@MybatisPlusTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MapperTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void test() {
        User entity = new User();
        entity.setUsername("test");
        entity.setPassword("kkk");
        entity.setNickname("test");
        entity.setSalt("111");
        entity.setCreateTime(LocalDateTime.now());
        userMapper.insert(entity);
    }
}
