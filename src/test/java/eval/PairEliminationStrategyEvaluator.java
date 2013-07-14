package eval;

import factual.HangmanGame;
import factual.HangmanGameRunner;
import factual.strategy.PairEliminationStrategy;
import factual.support.Wordlist;
import org.junit.Test;

public class PairEliminationStrategyEvaluator {


    @Test
    public void singleWordGuessTest() {
        String word = "yolked";
        int chances = Math.max(5, word.length());
        System.out.println("Word = " + word + " ; chances = " + chances);

        HangmanGame game = new HangmanGame(word, chances);
        HangmanGameRunner runner = new HangmanGameRunner();

        runner.run(game, new PairEliminationStrategy(Wordlist.loadFromFile("data/" + word.length() + ".sp.txt")));
        System.out.println("(" + word.length() + ") " + game);
    }

    @Test
    public void unit() {

        String[] words = new String[]{
                "COMAKER",
                "MISTAKES",
                "CUMULATE",
                "ERUPTIVE",
                "FACTUAL",
                "MONADISM",
                "MUS",
                "NAGGING",
                "OSES",
                "REMEMBERED",
                "SPODUMENES",
                "STEREOISOMERS",
                "TOXICS",
                "TRICHROMATS",
                "TRIOSE",
                "UNIFORMED"};

        for (String word: words) {

            int chances = 5;

            HangmanGame game = new HangmanGame(word, chances);
            HangmanGameRunner runner = new HangmanGameRunner();

            runner.run(game, new PairEliminationStrategy(Wordlist.loadFromFile("data/" + word.length() + ".sp.txt")));

            System.out.println(game);
        }

    }

}
