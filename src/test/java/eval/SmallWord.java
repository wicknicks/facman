package eval;

import factual.HangmanGame;
import factual.HangmanGameRunner;
import factual.strategy.FreqEliminatingStrategy;
import factual.strategy.OptimisticConsonant;
import factual.strategy.PairEliminationStrategy;
import factual.support.Wordlist;
import org.junit.Test;

public class SmallWord {

    @Test
    public void singleWordGuessTest() {
        String word = "coin";
        int chances = 6;
        System.out.println("Word = " + word + " ; chances = " + chances);

        System.out.println("----- Pair Elimination Strategy ----- ");
        HangmanGame game = new HangmanGame(word, chances);
        HangmanGameRunner runner = new HangmanGameRunner();
        runner.run(game, new PairEliminationStrategy(Wordlist.loadFromFile("data/" + word.length() + ".sp.txt")));
        System.out.println(game);

        System.out.println("----- Freq Elimination Strategy ----- ");
        game = new HangmanGame(word, chances);
        runner = new HangmanGameRunner();
        runner.run(game, new FreqEliminatingStrategy(Wordlist.loadFromFile("data/" + word.length() + ".sp.txt")));
        System.out.println(game);

        System.out.println("----- Optimistic Consonant Strategy ----- ");
        game = new HangmanGame(word, chances);
        runner = new HangmanGameRunner();
        runner.run(game, new OptimisticConsonant(Wordlist.loadFromFile("data/" + word.length() + ".sp.txt")));
        System.out.println(game);
    }

}
