package com.osu.dp.string_matching;

import java.util.Arrays;

public class DynamicLevenshtein {
    /** Dynamický výpočet Levenshteinovy vzdálenosti pomocí matic */
    public static int computeDist(String source, String target) {
        int[][] distMatrix = new int[source.length() + 1][target.length() + 1];

        for (int i = 0; i <= source.length(); i++) {
            for(int j = 0; j <= target.length(); j++) {
                if (i == 0) {
                    distMatrix[i][j] = j;
                }

                else if (j == 0) {
                    distMatrix[i][j] = i;
                }

                else {
                    distMatrix[i][j] = LevenshteinTools.numOfEdits(
                            distMatrix[i-1][j] + 1, //delete
                            distMatrix[i][j-1] + 1, //insert
                            distMatrix[i-1][j-1] + LevenshteinTools.substitutionCount(source.charAt(i-1), target.charAt(j-1)) //substitute
                    );
                }
            }
        }
        LevenshteinTools.printMatrix(distMatrix);
        return distMatrix[source.length()][target.length()];
    }


}
