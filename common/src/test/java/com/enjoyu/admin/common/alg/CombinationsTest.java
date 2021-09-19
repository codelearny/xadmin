package com.enjoyu.admin.common.alg;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class CombinationsTest {

    @Test
    public void arrCombTest() {
        Combinations.Combination arrayCombination = new Combinations.ArrayCombination();
        Integer[] exp = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<Integer[]> comb = arrayCombination.comb(exp);
        comb.stream().map(Arrays::toString).forEach(System.out::println);
    }


    @Test
    public void ListCombTest() {
        Combinations.Combination listCombination = new Combinations.ListCombination();
        Integer[] exp = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<Integer[]> comb = listCombination.comb(exp);
        comb.stream().map(Arrays::toString).forEach(System.out::println);
    }

}