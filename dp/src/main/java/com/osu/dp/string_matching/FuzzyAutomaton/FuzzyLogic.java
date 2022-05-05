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

    public static double LukasiewiczTNorm(double previousValue, double transitionValue) {
        return Math.max(0, previousValue + transitionValue - 1);
    }

    public static double[] LukasiewiczTKonorm(double[][] transitionMatrix) {
        double[] temp = new double[transitionMatrix.length];
        double columnSum;

        for(int i = 0; i < transitionMatrix[0].length; i++){
            columnSum = 0;
            for(int j = 0; j < transitionMatrix.length; j++){
                columnSum += transitionMatrix[j][i];
            }
            temp[i] = Math.min(1, columnSum);
        }

        return temp;
    }

    public static double PavelkaTNorm(double previousValue, double transitionValue) {
        return (previousValue * transitionValue);
    }

    public static double[] PavelkaTKonorm(double[][] transitionMatrix) {
        double[] temp = new double[transitionMatrix.length];
        double columnSum;

        for(int i = 0; i < transitionMatrix[0].length; i++){
            columnSum = transitionMatrix[0][i];
            for(int j = 1; j < transitionMatrix.length; j++){
                columnSum = (columnSum + transitionMatrix[j][i]) - (columnSum * transitionMatrix[j][i]);
            }
            temp[i] = columnSum;
        }

        return temp;
    }
}
