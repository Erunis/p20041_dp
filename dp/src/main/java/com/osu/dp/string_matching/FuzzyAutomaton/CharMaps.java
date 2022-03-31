package com.osu.dp.string_matching.FuzzyAutomaton;

import java.util.Map;

public class CharMaps {
    public static double[][] setSimilarityMatrix() {
        return new double[][]{
                {1,   0.9,   0,  0.2,  0.1},
                {0.9,  1,    0,  0.2,  0.1},
                {0,    0,    1,  0.2,  0.1},
                {0.2, 0.2,  0.2,  1,   0.8},
                {0.1, 0.1,  0.1,  0.8,  1}};
    }

    public static void setCharMap(Map<String, Integer> charMap) {
        charMap.put("a", 0);
        charMap.put("á", 1);
        charMap.put("b", 2);
        charMap.put("c", 3);
        charMap.put("č", 4);
        charMap.put("d", 5);
        charMap.put("ď", 6);
        charMap.put("e", 7);
        charMap.put("é", 8);
        charMap.put("ě", 9);
        charMap.put("f", 10);
        charMap.put("g", 11);
        charMap.put("h", 12);
        charMap.put("i", 13);
        charMap.put("í", 14);
        charMap.put("j", 15);
        charMap.put("k", 16);
        charMap.put("l", 17);
        charMap.put("m", 18);
        charMap.put("n", 19);
        charMap.put("ň", 20);
        charMap.put("o", 21);
        charMap.put("ó", 22);
        charMap.put("p", 23);
        charMap.put("q", 24);
        charMap.put("r", 25);
        charMap.put("ř", 26);
        charMap.put("s", 27);
        charMap.put("š", 28);
        charMap.put("t", 29);
        charMap.put("ť", 30);
        charMap.put("u", 31);
        charMap.put("ú", 32);
        charMap.put("ů", 33);
        charMap.put("v", 34);
        charMap.put("w", 35);
        charMap.put("x", 36);
        charMap.put("y", 37);
        charMap.put("ý", 38);
        charMap.put("z", 39);
        charMap.put("ž", 40);
    }

    public int getIndex() {

        return 0;
    }
}
