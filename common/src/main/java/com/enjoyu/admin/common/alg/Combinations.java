package com.enjoyu.admin.common.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Combinations {

    interface Combination {
        <E> List<E[]> comb(E[] data);
    }

    static class ArrayCombination implements Combination {
        @Override
        public <E> List<E[]> comb(E[] data) {
            List<E[]> result = new ArrayList<>();
            for (int i = 0; i < data.length; i++) {
                E[] es = Arrays.copyOf(data, i + 1);
                comb(result, data, es, 0, 0);
            }
            return result;
        }

        private <E> void comb(List<E[]> result, E[] data, E[] cache, int deep, int cur) {
            if (deep >= cache.length) {
                E[] es = Arrays.copyOf(cache, cache.length);
                result.add(es);
            } else {
                for (int i = cur; i <= data.length - cache.length + deep; i++) {
                    cache[deep] = data[i];
                    comb(result, data, cache, deep + 1, i + 1);
                }
            }
        }

    }

    static class ListCombination implements Combination {
        @Override
        public <E> List<E[]> comb(E[] data) {
            List<E> es = new ArrayList<>(Arrays.asList(data));
            List<List<E>> sets = new ArrayList<>();

            for (int i = 0; i < data.length; i++) {
                List<List<E>> comb = comb(es, new ArrayList<>(), i + 1);
                sets.addAll(comb);
            }
            return sets.stream().map(s -> s.toArray(data)).collect(Collectors.toList());
        }

        private <E> List<List<E>> comb(List<E> remain, List<E> result, int deep) {
            if (deep < 1) {
                List<List<E>> sets = new ArrayList<>();
                for (int i = 0; i < remain.size(); i++) {
                    List<E> es = new ArrayList<>(result);
                    es.add(remain.get(i));
                    sets.add(es);
                }
                return sets;
            } else {
                List<List<E>> sets = new ArrayList<>();
                for (int i = 0; i < remain.size(); i++) {
                    ArrayList<E> remainNew = new ArrayList<>(remain);
                    E remove = remainNew.remove(i);
                    List<E> resultNew = new ArrayList<>(result);
                    resultNew.add(remove);
                    List<List<E>> comb = comb(remainNew, resultNew, deep--);
                    sets.addAll(comb);
                }
                return sets;
            }
        }

    }
}
