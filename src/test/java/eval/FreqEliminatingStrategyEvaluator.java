package eval;

import factual.HangmanGame;
import factual.HangmanGameRunner;
import factual.strategy.FreqEliminatingStrategy;
import factual.support.Wordlist;
import org.junit.Test;

public class FreqEliminatingStrategyEvaluator {


    @Test
    public void singleWordGuessTest() {
        String word = "yolked";
        int chances = Math.max(5, word.length());
        System.out.println("Word = " + word + " ; chances = " + chances);

        HangmanGame game = new HangmanGame(word, chances);
        HangmanGameRunner runner = new HangmanGameRunner();

        runner.run(game, new FreqEliminatingStrategy(Wordlist.loadFromFile("data/" + word.length() + ".sp.txt")));
        System.out.println("(" + word.length() + ") " + game);
    }

    @Test
    public void unit() {

        String[] words = new String[]{"aardvark", "aahing", "aa", "electroencephalographically", "ethylenediaminetetraacetates",
                "immunoelectrophoretically", "dichlorodifluoromethanes", "carboxymethylcellulose",
                "acetylcholinesterases", "acetylcholinesterase", "abdicable",
                "abdicated", "abdicates", "abdicator", "abdominal", "abductees"};

        for (String word: words) {

            int chances = Math.max(5, word.length());

            HangmanGame game = new HangmanGame(word, chances);
            HangmanGameRunner runner = new HangmanGameRunner();

            runner.run(game, new FreqEliminatingStrategy(Wordlist.loadFromFile("data/" + word.length() + ".sp.txt")));

            System.out.println("Word = " + word + " ; chances = " + chances + "; " + game.currentScore());
        }

    }

}
