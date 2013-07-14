package factual.strategy;

import factual.Guess;
import factual.GuessLetter;
import factual.GuessingStrategy;
import factual.HangmanGame;

/**
 * Use the letter ordering "ESIARNTOLCDUPMGHBYFVKWZXQJ" [1]
 * and guess it character for character.
 * <p></p>
 * <p>
 * References:
 * [1] http://www.datagenetics.com/blog/april12012/
 * </p>
*/
public class LetterOrderingStrategy implements GuessingStrategy {

    final char[] order = "ESIARNTOLCDUPMGHBYFVKWZXQJ".toCharArray();
    int ix = 0;

    @Override
    public Guess nextGuess(HangmanGame game) {
        Guess g = new GuessLetter(order[ix]);
        ix++;
        return g;
    }

}
