package com.tet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Ace {
    private String[] strArr;
    private List<String> listStr;
    private List<Map<String, Object>> listMap;

    @Override
    public String toString() {
        return "Ace{" +
                "strArr=" + Arrays.toString(strArr) +
                ", listStr=" + listStr +
                ", listMap=" + listMap +
                '}';
    }
}
