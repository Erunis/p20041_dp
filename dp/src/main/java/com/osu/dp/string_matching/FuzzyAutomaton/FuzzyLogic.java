package com.osu.dp.string_matching.FuzzyAutomaton;

public class FuzzyLogic {
    public static double GodelTNorm(double previousValue, double transitionValue) {
        return Math.min(previousValue, transitionValue);
    }

    public static double GodelTKonorm(int x, int y) {
        return Math.max(x,y);
    }

    /*public double LukasiewiczTNorm() {
        max(0, x+y-1)
    }

    public double LukasiewiczTKonorm() {
        min(1, x+y)
    }*/
}
