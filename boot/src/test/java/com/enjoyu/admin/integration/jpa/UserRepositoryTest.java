package com.enjoyu.admin.integration.jpa;

import com.enjoyu.admin.auth.entity.User;
import com.enjoyu.admin.jpa.dao.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void add1(){
        User user = new User();
        user.setId(1);
        user.setUsername("aa");
        user.setPhone("1211133445");
        userRepository.save(user);
    }
    @Test
    public void find1() {
        User u1 = userRepository.findFirstByUsername("u1");
        System.out.println(u1);
    }
}
