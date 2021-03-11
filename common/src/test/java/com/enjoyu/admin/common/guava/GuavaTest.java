package com.enjoyu.admin.common.guava;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GuavaTest {
    @Test
    void name() {
        ArrayList<Object> objects = Lists.newArrayListWithCapacity(12);
        System.out.println(objects.size());
    }
}
