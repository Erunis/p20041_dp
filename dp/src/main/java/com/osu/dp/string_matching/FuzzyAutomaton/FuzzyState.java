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

    public double similarityFunc(String pattern, String source, int logic) {
        mapsInit();

        if (pattern.length() == source.length()) {
            for (int i = 0; i < pattern.length(); i++) {
                init(pattern, i);
                countSimilarity(pattern, source, logic, i);
            }

            countTKonorm(logic);
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

    private double getSimilarity(double[] array) {
        double similarity = array[0];

        for (int i = 1; i < array.length; i++) {
            similarity = (similarity + array[i]) - (similarity * array[i]);
        }

        return similarity;
    }

    private void countSimilarity(String pattern, String source, int logic, int i) {
        Integer srcId = charMap.get(Character.toString(source.charAt(i)));
        System.out.println("initial=" + Arrays.toString(initial));

        for (int j = 0; j < transitionMatrix.length; j++) {
            for (int k = 0; k < transitionMatrix[0].length; k++) {
                if (j < pattern.length() && k == j + 1) {
                    Integer ptnId = charMap.get(Character.toString(pattern.charAt(j)));
                    stringSimValue = similarityMatrix[ptnId][srcId];
                } else {
                    stringSimValue = 0;
                }
                System.out.println("sim=" + stringSimValue);

                double[] temp = initial;

                switch (logic) {
                    case 1 -> transitionMatrix[j][k] = FuzzyLogic.GodelTNorm(temp[j], stringSimValue);
                    case 2 -> transitionMatrix[j][k] = FuzzyLogic.LukasiewiczTNorm(temp[j], stringSimValue);
                    case 3 -> transitionMatrix[j][k] = FuzzyLogic.PavelkaTNorm(temp[j], stringSimValue);
                    default -> System.out.println("Typed logic does not exist.");
                }

                System.out.println("init=" + Arrays.toString(initial));
            }
        }

        switch (logic) {
            case 1 -> temp = FuzzyLogic.GodelTKonorm(transitionMatrix);
            case 2 -> temp = FuzzyLogic.LukasiewiczTKonorm(transitionMatrix);
            case 3 -> temp = FuzzyLogic.PavelkaTKonorm(transitionMatrix);
            default -> System.out.println("Typed logic does not exist.");
        }
    }

    private void countTKonorm(int logic) {
        switch (logic) {
            case 1 -> similarity = Arrays.stream(initial).max().getAsDouble();
            case 2 -> similarity = Math.min(1, Arrays.stream(initial).sum());
            case 3 -> similarity = getSimilarity(initial);
            default -> System.out.println("Typed logic does not exist.");
        }
    }
}
