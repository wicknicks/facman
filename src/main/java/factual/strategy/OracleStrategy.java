package factual.strategy;

import factual.*;

/*
A simple strategy which guesses the word given to it in the constructor.
 */

public class OracleStrategy implements GuessingStrategy {

    char[] letters = null;
    int ix = 0;

    public OracleStrategy(String word) {
        letters = ("xz" + word).toUpperCase().toCharArray();
        ix = 0;
    }

    @Override
    public Guess nextGuess(HangmanGame game) {
        return new GuessWord(String.valueOf(letters, 2, letters.length-2));

//        Guess g = new GuessLetter(letters[ix]);
//        ix++;
//        return g;
    }
}
