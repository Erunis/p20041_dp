package com.osu.dp.string_matching.FuzzyAutomaton;

import java.util.HashMap;
import java.util.Map;

public class FuzzyState {
    private double stringSimValue;
    private double similarity;
    private double[][] similarityMatrix;
    private Map<String, Integer> charMap;

    private void init() {
        similarityMatrix = new double[5][5];
        charMap = new HashMap<>();
        CharMaps.setSimilarityMatrix(similarityMatrix);
        CharMaps.setCharMap(charMap);
    }

    public double similarityFunc(String pattern, String source) {
        if (pattern.length() == source.length()) {
            for (int i = 0; i < pattern.length(); i++) {
                int ptnId = charMap.get(pattern.charAt(i));
                int srcId = charMap.get(source.charAt(i));
                stringSimValue = similarityMatrix[ptnId][srcId];

                if (i == 0) {
                    similarity = FuzzyLogic.GodelTNorm(1, stringSimValue);
                }

                else {
                    similarity = FuzzyLogic.GodelTNorm(similarity, stringSimValue);
                }
            }
        }

        else {
            System.out.println("Error strings not same length.");
        }
        //kontrola, že jsou stringy stejně dlouhé, if not = error
        //cyklus for pro průchod pattern a source stringů
        //na i-té pozici vezme symbol pattern/source, najde jejich sourceIndex a targetIndex v HashMap
        //similarityValue = Matrix[sourceIndex][targetIndex]
        //se získanou similarityValue výpočet pomocí t-norem a t-konorem

        return similarity;
    }
}
