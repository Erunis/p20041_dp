package com.osu.dp.string_matching;

public class DamerauLevenshtein {
    public static int computeDist(String source, String target) {
        int[][] distMatrix = new int[source.length() + 1][target.length() + 1];

        for (int i = 0; i <= source.length(); i++) {
            for (int j = 0; j <= target.length(); j++) {
                if(i == 0) {
                    distMatrix[i][j] = j;
                }

                else if (j == 0) {
                    distMatrix[i][j] = i;
                }

                else {
                    int subsCount = LevenshteinTools.substitutionCount(source.charAt(i-1), target.charAt(j-1));

                    distMatrix[i][j] = LevenshteinTools.numOfEdits(
                            distMatrix[i-1][j] + 1, //delete
                            distMatrix[i][j-1] + 1, //insert
                            distMatrix[i-1][j-1] + subsCount //substitute
                    );

                    if (i > 1 && j > 1 && source.charAt(i-1) == target.charAt(j-2) && source.charAt(i-2) == target.charAt(j-1)) {
                        distMatrix[i][j] = Math.min(distMatrix[i][j], distMatrix[i-2][j-2] + subsCount);
                    }
                }
            }
        }

        //LevenshteinTools.printMatrix(distMatrix);
        return distMatrix[source.length()][target.length()];
    }
}
