package com.enjoyu.admin.test;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CollectorsTest {
    static List<Data> dataList;

    @BeforeAll
    public static void init() {
        dataList = Lists.newArrayList(
                new Data(1, 2, "pig"),
                new Data(1, 3, "cow"),
                new Data(2, 1, "ant"),
                new Data(2, 1, "fox"),
                new Data(13, 11, "duck"),
                new Data(12, 2, "chicken"),
                new Data(10, 5, "fish"),
                new Data(10, 5, "butterfly")
        );
    }

    @Test
    public void testGroupingBy() {
        Map<Integer, List<Data>> collect = dataList.stream().collect(
                Collectors.groupingBy(data -> data.id + data.tag,
                        Collectors.toList())
        );
        System.out.println(collect.getClass());
        System.out.println(collect);

        Map<Integer, Long> counting = dataList.stream().collect(
                Collectors.groupingBy(data -> data.id + data.tag,
                        Collectors.counting())
        );
        System.out.println(counting);

        Map<Integer, Set<String>> collect1 = dataList.stream().collect(
                Collectors.groupingBy(data -> data.id + data.tag,
                        Collectors.mapping(data -> data.name,
                                Collectors.toSet()))
        );
        Map<Integer, List<String>> collect2 = dataList.stream().collect(
                Collectors.groupingBy(data -> data.id + data.tag,
                        Collectors.mapping(data -> data.name,
                                Collectors.toList()))
        );
        System.out.println(collect1);
        System.out.println(collect2);

    }

    static class Data {
        private int id;
        private int tag;
        private String name;

        public Data(int id, int tag, String name) {
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

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
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
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
