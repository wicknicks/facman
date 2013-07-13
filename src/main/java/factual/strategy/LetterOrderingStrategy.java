package factual.strategy;

import factual.Guess;
import factual.GuessLetter;
import factual.GuessingStrategy;
import factual.HangmanGame;

//letter ordering taken from http://www.datagenetics.com/blog/april12012/

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
