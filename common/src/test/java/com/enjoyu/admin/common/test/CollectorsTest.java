package com.enjoyu.admin.common.test;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CollectorsTest {
    static List<Data> dataList;

    @BeforeAll
    public static void init() {
        dataList = Lists.newArrayList(
                new Data(1, "a", "pig"),
                new Data(1, "b", "cow"),
                new Data(2, "a", "ant"),
                new Data(2, "b", "fox"),
                new Data(13, "c", "duck"),
                new Data(12, "d", "chicken"),
                new Data(10, "f", "fish"),
                new Data(10, "f", "butterfly")
        );
    }

    @Test
    public void testGroupingBy() {
        Map<String, List<Data>> collect = dataList.stream().collect(
                Collectors.groupingBy(data -> data.id + data.tag,
                        Collectors.toList())
        );
        System.out.println(collect.getClass());
        System.out.println(collect);

        Map<String, Long> counting = dataList.stream().collect(
                Collectors.groupingBy(data -> data.id + data.tag,
                        Collectors.counting())
        );
        System.out.println(counting);

        Map<String, Set<String>> collect1 = dataList.stream().collect(
                Collectors.groupingBy(data -> data.id + data.tag,
                        Collectors.mapping(data -> data.name,
                                Collectors.toSet()))
        );
        Map<Integer, Map<String, List<Data>>> collect2 = dataList.stream().collect(
                Collectors.groupingBy(data -> data.id,
                        Collectors.groupingBy(data -> data.tag,
                                Collectors.toList()
                        )
                )
        );
        System.out.println(collect1);
        System.out.println(collect2);

    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCollect() {
        HashMap<String, Object> collect = dataList.stream().collect(
                HashMap::new, (x, y) -> {
                    x.compute(y.id + "", (k, v) -> {
                        if (v == null) {
                            v = new HashMap<>();
                        }
                        HashMap<String,String> v1 = (HashMap<String,String>) v;
                        v1.put(y.tag, y.name);
                        return v1;
                    });
                    x.compute("count", (s, c) -> {
                        if (c != null) {
                            Integer c1 = (Integer) c;
                            return c1 + 1;
                        }
                        return 1;
                    });
                },
                (hashMap, hashMap2) -> {
                    System.out.println(hashMap + "-----" + hashMap2);
                }

        );
        System.out.println(collect);
    }

    static class Data {
        private int id;
        private String tag;
        private String name;

        public Data(int id, String tag, String name) {
            this.id = id;
            this.tag = tag;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", tag=" + tag +
                    ", name='" + name + "'" +
                    '}';
        }
    }
}
