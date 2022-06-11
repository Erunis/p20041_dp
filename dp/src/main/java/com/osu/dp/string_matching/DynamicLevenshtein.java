package com.osu.dp.string_matching;

public class DynamicLevenshtein {
    /** Method for calculating dynamic Levenshtein distance
     * @param source ... input source string
     * @param target ... pattern string */
    public static int computeDist(String source, String target) {
        int[][] distMatrix = new int[source.length() + 1][target.length() + 1];

        for (int i = 0; i <= source.length(); i++) {
            for(int j = 0; j <= target.length(); j++) {

                /** It's required to input all of the pattern letters to get the pattern string if the source string is empty. */
                if (i == 0) {
                    distMatrix[i][j] = j;
                }

                /** It's required to delete all of the source letters if the pattern string is empty. */
                else if (j == 0) {
                    distMatrix[i][j] = i;
                }

                /** Calculation of the minimum value made out of all the edit operations insert, delete and substitute */
                else {
                    distMatrix[i][j] = LevenshteinTools.numOfEdits(
                            distMatrix[i-1][j] + 1, //delete
                            distMatrix[i][j-1] + 1, //insert
                            distMatrix[i-1][j-1] + LevenshteinTools.substitutionCount(source.charAt(i-1), target.charAt(j-1)) //substitute
                    );
                }
            }
        }

        /** Optional call for printing the final matrix of result values. */
        // LevenshteinTools.printMatrix(distMatrix);
        return distMatrix[source.length()][target.length()];
    }
}
