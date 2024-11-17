package com.yushu.exercise07;

class SquareRoot {
    SquareRoot() {
    }

    public static double sqrt(double num) {
        double nextGuess = 1.0;

        double lastGuess;
        do {
            lastGuess = nextGuess;
            nextGuess = (nextGuess + num / nextGuess) * 0.5;
        } while(Math.abs(nextGuess - lastGuess) >= 1.0E-5);

        return nextGuess;
    }
}