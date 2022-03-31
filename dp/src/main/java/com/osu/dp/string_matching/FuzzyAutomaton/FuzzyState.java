package com.osu.dp.string_matching.FuzzyAutomaton;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FuzzyState {
    private double stringSimValue;
    private double similarity;
    private double[][] similarityMatrix;
    private Map<String, Integer> charMap;
    private double[][] transitionMatrix;
    private double[] initial;

    private void init(String pattern) {
        similarityMatrix = CharMaps.setSimilarityMatrix();

        charMap = new HashMap<>();
        CharMaps.setCharMap(charMap);

        int size = pattern.length() + 1;
        transitionMatrix = new double[size][size];

        initial = getInitialValue(transitionMatrix.length);
    }

    public double similarityFunc(String pattern, String source) {
        init(pattern);
        System.out.println(charMap.toString());

        if (pattern.length() == source.length()) {
            for (int i = 0; i < pattern.length(); i++) {
                Integer srcId = charMap.get(Character.toString(source.charAt(i)));
                System.out.println("initial="+Arrays.toString(initial));

                for (int j = 0; j < transitionMatrix.length; j++) {
                    for (int k = 0; k < transitionMatrix[j].length; k++) {
                        if (j <= pattern.length() && k == j+1) {
                            Integer ptnId = charMap.get(Character.toString(pattern.charAt(j)));
                            stringSimValue = similarityMatrix[ptnId][srcId];
                        }

                        else {
                            stringSimValue = 0;
                        }

                        transitionMatrix[j][k] = FuzzyLogic.GodelTNorm(initial[j], stringSimValue);

                        for (int row = 0; row < transitionMatrix.length; row++) {
                            for (int col = 0; col < transitionMatrix[row].length; col++) {
                                System.out.print(transitionMatrix[row][col] + " ");
                            }
                            System.out.println();
                        }
                        System.out.println("..................");
                    }
                }

                initial = FuzzyLogic.GodelTKonorm(transitionMatrix);
            }

            similarity = Arrays.stream(initial).max().getAsDouble();
        }

        else {
            System.out.println("Error strings not same length.");
        }

        return similarity;
    }

    private double[] getInitialValue(int size) {
        double[] initialVal = new double[size];
        initialVal[0] = 1;

        for (int i = 1; i < size; i++) {
            initialVal[i] = 0;
        }

        return initialVal;
    }
}
