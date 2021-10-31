package com.osu.dp.string_matching;

import java.util.Arrays;

public class LevenshteinTools {
    /** Výpočet pro podmínku (ai != bj) u substituce, zjišťuje, jestli se znaky zdrojového a cílového řetězce rovnají. */
    public static int substitutionCount(char sourceChar, char targetChar) {
        return sourceChar == targetChar ? 0 : 1;
    }

    /** Metoda pro výpočet počtu úprav potřebných k získání cílového řetězce ze zdrojového. */
    public static int numOfEdits(int... count) {
        return Arrays.stream(count).min().orElse(Integer.MAX_VALUE);
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
