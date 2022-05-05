package com.osu.dp.string_matching;

public class DynamicLevenshtein {
    /** Dynamický výpočet Levenshteinovy vzdálenosti pomocí matic */
    public static int computeDist(String source, String target) {
        int[][] distMatrix = new int[source.length() + 1][target.length() + 1];

        for (int i = 0; i <= source.length(); i++) {
            for(int j = 0; j <= target.length(); j++) {

                /* Pokud je zdrojový řetězec prázdný, je třeba na získání cílového řetězce vložit všechny znaky. */
                if (i == 0) {
                    distMatrix[i][j] = j;
                }

                /* Pokud je cílový řetězec prázdný, je třeba na jeho získání ze zdrojového řetězce smazat všechny znaky. */
                else if (j == 0) {
                    distMatrix[i][j] = i;
                }

                /* Výpočet minima z operací delete, insert a substitute */
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
