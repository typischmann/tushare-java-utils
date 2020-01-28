package com.analysis.algorithm;

public class KellyCalculator {

    public static double kelly(double p, double rw){
        return (p*rw-(1-p))/rw;
    }

    public static double kelly(double p, double rw, double rl){
        return (p/rl)-((1-p)/rw);
    }
}
