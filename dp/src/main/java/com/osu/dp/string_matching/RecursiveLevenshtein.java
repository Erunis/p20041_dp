package com.osu.dp.string_matching;

public class RecursiveLevenshtein {
    /** Rekurzivní výpočet Levenshteinovy vzdálenosti */
    public static int computeDist(String source, String target) {
        /* Pokud je zdrojový řetězec prázdný, je třeba na získání cílového řetězce vložit všechny znaky. */
        if (source.isEmpty()) {
            return target.length();
        }

        /* Pokud je cílový řetězec prázdný, je třeba na jeho získání ze zdrojového řetězce smazat všechny znaky. */
        if (target.isEmpty()) {
            return source.length();
        }

        /* lev = lev(i-1, j)+1 */
        int delete = computeDist(source.substring(1), target) + 1;

        /* lev = lev(i, j-1)+1 */
        int insert = computeDist(source, target.substring(1)) + 1;

        /* lev = lev(i-1, j-1)+1(ai != bj) */
        int substitute = computeDist(source.substring(1), target.substring(1)) +
                LevenshteinTools.substitutionCount(source.charAt(0), target.charAt(0));

        return LevenshteinTools.numOfEdits(delete, insert, substitute);
    }
}
