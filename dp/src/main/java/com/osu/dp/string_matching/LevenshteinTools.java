package com.osu.dp.string_matching;

import java.util.Arrays;

/** Auxiliary class for methods of Levenshtein distance calculation. */
public class LevenshteinTools {
    /** Calculation for the condition (ai != bj) for the substitution operation determining if the currently examined
     * letter in source string is the same as the one examined in the pattern string. */
    public static int substitutionCount(char sourceChar, char targetChar) {
        return sourceChar == targetChar ? 0 : 1;
    }

    /** Method for calculation of the minimum edit distance. */
    public static int numOfEdits(int... count) {
        return Arrays.stream(count).min().orElse(Integer.MAX_VALUE);
    }

    /** Auxiliaty method for printing the matrix for dynamic Levenshtein. */
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printMatrix(String[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    /** Calculation of the final similiarity value for the methods of Levenshtein distance. */
    public static double countSimilarity(String source, String target, int distance) {
        double error = ((double) distance / Math.max(source.length(), target.length())) * 100;
        double similarity = 100 - error;

        return similarity;
    }
}
