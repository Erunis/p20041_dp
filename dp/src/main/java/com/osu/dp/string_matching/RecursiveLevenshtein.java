package com.osu.dp.string_matching;

public class RecursiveLevenshtein {
    /** Method for calculating recursive Levenshtein distance
     * @param source ... input source string
     * @param target ... pattern string */
    public static int computeDist(String source, String target) {
        /** It's required to input all of the pattern letters to get the pattern string if the source string is empty. */
        if (source.isEmpty()) {
            return target.length();
        }

        /** It's required to delete all of the source letters if the pattern string is empty. */
        if (target.isEmpty()) {
            return source.length();
        }

        /** From Levenshtein distance formula: lev = lev(i-1, j)+1*/
        int delete = computeDist(source.substring(1), target) + 1;

        /** From Levenshtein distance formula: lev = lev(i, j-1)+1 */
        int insert = computeDist(source, target.substring(1)) + 1;

        /** From Levenshtein distance formula: lev = lev(i-1, j-1)+1(ai != bj) */
        int substitute = computeDist(source.substring(1), target.substring(1)) +
                LevenshteinTools.substitutionCount(source.charAt(0), target.charAt(0));

        return LevenshteinTools.numOfEdits(delete, insert, substitute);
    }
}
