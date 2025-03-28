package com.tgms.validationtolls.utils;

import java.util.ArrayList;
import java.util.List;

public class PaginationUtils {

    public static List<Object> getRangeWithDots(int current, int last) {
        int delta = 2;
        int left = current - delta;
        int right = current + delta + 1;
        List<Integer> range = new ArrayList<>();
        List<Object> rangeWithDots = new ArrayList<>();
        Integer prev = null;

        // Generate the range of numbers to include
        for (int i = 1; i <= last; i++) {
            if (i == 1 || i == last || (i >= left && i < right)) {
                range.add(i);
            }
        }

        // Add dots and numbers to the final list
        for (int i : range) {
            if (prev != null) {
                if (i - prev == 2) {
                    rangeWithDots.add(prev + 1);
                } else if (i - prev != 1) {
                    rangeWithDots.add("...");
                }
            }
            rangeWithDots.add(i);
            prev = i;
        }

        return rangeWithDots;
    }


}
