package eval;

import factual.HangmanGame;
import factual.HangmanGameRunner;
import factual.strategy.FreqEliminatingStrategy;
import factual.strategy.LetterOrderingStrategy;
import factual.strategy.OptimisticConsonant;
import factual.support.Wordlist;
import org.junit.Test;

public class OptimisticConsonantEvalutor {

    @Test
    public void singleWordGuessTest() {
        String word = "pyemia";
        int chances = 6;
        System.out.println("Word = " + word + " ; chances = " + chances);

        HangmanGame game = new HangmanGame(word, chances);
        HangmanGameRunner runner = new HangmanGameRunner();

        OptimisticConsonant oc = new OptimisticConsonant(Wordlist.loadFromFile("data/" + word.length() + ".sp.txt"));
        runner.run(game, oc);
        System.out.println("(" + word.length() + ") " + game + " " + oc.words());
    }


    @Test
    public void unit() {

        String[] words = new String[]{"aardvark", "aahing", "aa", "electroencephalographically", "ethylenediaminetetraacetates",
                "immunoelectrophoretically", "dichlorodifluoromethanes", "carboxymethylcellulose",
                "acetylcholinesterases", "acetylcholinesterase", "abdicable",
                "abdicated", "abdicates", "abdicator", "abdominal", "abductees"};

        for (String word: words) {
            HangmanGame game = new HangmanGame(word, Math.max(5, word.length()));
            HangmanGameRunner runner = new HangmanGameRunner();
            runner.run(game, new OptimisticConsonant(Wordlist.loadFromFile("data/" + word.length() + ".sp.txt")));
            System.out.println("(" + word.length() + ") " + game + "; word length ");
        }

    }
}
