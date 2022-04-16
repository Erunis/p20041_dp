package com.osu.dp.string_matching.FuzzyAutomaton;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FuzzyState {
    private double stringSimValue;
    private double similarity;
    private double[][] similarityMatrix;
    private Map<String, Integer> charMap;
    private double[][] transitionMatrix;
    private double[] initial;
    private double[] temp;

    private void mapsInit() {
        similarityMatrix = CharMaps.setSimilarityMatrix();

        charMap = new HashMap<>();
        CharMaps.setCharMap(charMap);
    }

    private void init(String pattern, int i) {
        int size = pattern.length() + 1;
        transitionMatrix = new double[size][size];

        if (i == 0) {
            initial = getInitialValue(transitionMatrix.length);
        }

        else {
            initial = temp;
        }

    }

    public double similarityFunc(String pattern, String source) {
        mapsInit();

        if (pattern.length() == source.length()) {
            for (int i = 0; i < pattern.length(); i++) {
                init(pattern, i);
                Integer srcId = charMap.get(Character.toString(source.charAt(i)));
                System.out.println("initial="+Arrays.toString(initial));

                for (int j = 0; j < transitionMatrix.length; j++) {
                    for (int k = 0; k < transitionMatrix[0].length; k++) {
                        if (j < pattern.length() && k == j+1) {
                            Integer ptnId = charMap.get(Character.toString(pattern.charAt(j)));
                            stringSimValue = similarityMatrix[ptnId][srcId];
                        }

                        else {
                            stringSimValue = 0;
                        }
                        System.out.println("sim="+stringSimValue);

                        double[] temp = initial;
                        transitionMatrix[j][k] = FuzzyLogic.GodelTNorm(temp[j], stringSimValue);
                        System.out.println("init="+Arrays.toString(initial));

                        /* VÝPIS MATICE PŘECHODŮ
                        for (int row = 0; row < transitionMatrix.length; row++) {
                            for (int col = 0; col < transitionMatrix[row].length; col++) {
                                System.out.print(transitionMatrix[row][col] + " ");
                            }
                            System.out.println();
                        }
                        System.out.println("..................");*/
                    }
                }

                temp = FuzzyLogic.GodelTKonorm(transitionMatrix);
            }

            // jiné pro každou logiku!
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
