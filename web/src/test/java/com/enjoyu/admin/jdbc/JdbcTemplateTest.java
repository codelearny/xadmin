package com.enjoyu.admin.jdbc;

import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JdbcTemplateTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void clob() {
        List<Map<String, Object>> maps = jdbcTemplate.query("select * from t_clob", (rs, rowNum) -> {
            Object object = rs.getObject(++rowNum);
            Map<String, Object> map = Maps.newHashMap();
            map.put(String.valueOf(rowNum), object);
            return map;
        });
        for (Map<String, Object> map : maps) {
            System.out.println(map.getClass());
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " ==== " + entry.getValue().getClass());
            }
        }
        System.out.println(maps);
    }
}
