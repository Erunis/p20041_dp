package com.osu.dp.string_matching.FuzzyAutomaton;

public class FuzzyLogic {
    public static double GodelTNorm(double previousValue, double transitionValue) {
        return Math.min(previousValue, transitionValue);
    }

    public static double[] GodelTKonorm(double[][] transitionMatrix) {
        double[] temp = transitionMatrix[0];
        for (int j = 1; j < transitionMatrix.length; j++) {
            for (int k = 0; k < transitionMatrix[0].length; k++) {
                if (transitionMatrix[j][k] > temp[k]) {
                    temp[k] = transitionMatrix[j][k];
                }
            }
        }
        return temp;
    }

    /*public static double GodelTKonorm(int x, int y) {
        return Math.max(x,y);
    }*/

    /*public double LukasiewiczTNorm() {
        max(0, x+y-1)
    }

    public double LukasiewiczTKonorm() {
        min(1, x+y)
    }*/
}
