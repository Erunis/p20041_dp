package com.osu.dp.string_matching.FuzzyAutomaton;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/** Class containing methods for calculating the final similarity value of two strings */
public class FuzzyState {
    private double stringSimValue; // Similarity value of two examined letters
    private double similarity; // Final similarity value of two strings
    private double[][] similarityMatrix; // Variable for storing the matrix of letter similarities
    private Map<String, Integer> charMap; // Variable for storing the map of characters and their indexes
    private double[][] transitionMatrix; // Computation matrix
    private double[] initial; // Array of state values
    private double[] temp; // Temporary array

    /** Method for initializing the similarity matrix. */
    private void mapsInit() {
        similarityMatrix = CharMaps.setSimilarityMatrix();

        charMap = new HashMap<>();
        CharMaps.setCharMap(charMap);
    }

    /** Method for initialization of the automata, its states and computation matrix.
     * @param str ... source or pattern string from which the length of initialization array is determined
     * @param i ... index of the letter that is being processed */
    private void init(String str, int i) {
        int size = str.length() + 1;
        transitionMatrix = new double[size][size];

        if (i == 0) {
            initial = getInitValue(size);
        }

        else {
            initial = temp;
        }
    }

    /** Main method for calling the calculation of the similarity of two strings.
     * @param source ... input source string
     * @param pattern ... pattern string
     * @param logic ... number indicating the type of logic that should be used for the computation */
    public double similarityFunc(String source, String pattern, int logic) {
        mapsInit();

        return getSimilarity(source, logic, pattern);
    }

    /** Method for calculation of the similarity of two strings. */
    private double getSimilarity(String source, int logic, String pattern) {
        /** If pattern and source strings are the same length. */
        if (pattern.length() == source.length()) {
            for (int i = 0; i < pattern.length(); i++) {
                init(pattern, i);
                countSimilarity(pattern, source, logic, i);
            }

            getResult(source, pattern, logic);
        }

        /** If pattern string is longer than source string. */
        else if (pattern.length() > source.length()) {
            for (int i = 0; i < source.length(); i++) {
                init(source, i);
                countSimilarity(pattern, source, logic, i);
            }

            getResult(source, pattern, logic);
        }

        /** If source string is longer than pattern string, it can be only max one letter longer. */
        else if (source.length() == pattern.length() + 1) {
            pattern = pattern + "#";
            for (int i = 0; i < source.length(); i++) {
                init(pattern, i);
                countSimilarity(pattern, source, logic, i);
            }

            getResult(source, pattern, logic);
        }

        /** If source string is two letters and more longer than pattern string. */
        else {
            System.out.println("Source word is too long.");
            similarity = 0;
        }

        return similarity;
    }

    /** Computation of the similarity */
    public void countSimilarity(String pattern, String source, int logic, int i) {
        Integer srcId = charMap.get(Character.toString(source.charAt(i)));
        System.out.println("initial=" + Arrays.toString(initial));

        for (int j = 0; j < transitionMatrix.length; j++) {
            for (int k = 0; k < transitionMatrix[0].length; k++) {
                if (j < pattern.length() && k == j + 1) {
                    Integer ptnId = charMap.get(Character.toString(pattern.charAt(j)));
                    stringSimValue = similarityMatrix[ptnId][srcId];
                }

                else if (j == 0) {
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

                /** Optional call for printing the matrix of result values. */
                /* System.out.println("init=" + Arrays.toString(initial));
                for (int row = 0; row < transitionMatrix.length; row++) {
                    for (int col = 0; col < transitionMatrix[row].length; col++) {
                        System.out.print(transitionMatrix[row][col] + " ");
                    }
                    System.out.println();
                }
                System.out.println(".....................");*/
            }
        }

        switch (logic) {
            case 1 -> temp = FuzzyLogic.GodelTKonorm(transitionMatrix);
            case 2 -> temp = FuzzyLogic.LukasiewiczTKonorm(transitionMatrix);
            case 3 -> temp = FuzzyLogic.PavelkaTKonorm(transitionMatrix);
            default -> System.out.println("Typed logic does not exist.");
        }
        initial = temp;
    }

    /** Final t-conorm for getting the results from {@code transitionMatrix} */
    public void getResult(String source, String pattern, int logic) {
        double[] ret = new double[]{};
        if (pattern.length() > 3 && source.length() > 3) {
            if (source.length() <= pattern.length()) {
                ret = new double[2];
                ret[0] = initial[initial.length-2];
                ret[1] = initial[initial.length-1];
            }

            else if (source.length() == pattern.length()+1) {
                ret = new double[3];
                ret[0] = initial[initial.length-3];
                ret[1] = initial[initial.length-2];
                ret[2] = initial[initial.length-1];
                System.out.println(Arrays.toString(ret));
            }
        }

        else {
            ret = initial;
        }

        switch (logic) {
            case 1 -> similarity = Arrays.stream(ret).max().getAsDouble();
            case 2 -> similarity = Math.min(1, Arrays.stream(ret).sum());
            case 3 -> similarity = getPavelkaResult(ret);
            default -> System.out.println("Typed logic does not exist.");
        }
    }

    /** Method for getting the final similarity value via Pavelka logic. */
    private double getPavelkaResult(double[] array) {
        double similarity = array[0];

        for (int i = 1; i < array.length; i++) {
            similarity = (similarity + array[i]) - (similarity * array[i]);
        }

        return similarity;
    }

    /** Method for initializing states of the automaton. */
    private double[] getInitValue (int size) {
        double[] initialVal = new double[size];
        initialVal[0] = 1;
        initialVal[1] = 0.8;
        initialVal[2] = 0.4;

        for (int i = 3; i < size; i++) {
            initialVal[i] = 0;
        }

        return initialVal;
    }
}
