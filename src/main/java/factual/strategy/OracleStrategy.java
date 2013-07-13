package factual.strategy;

import factual.*;

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
