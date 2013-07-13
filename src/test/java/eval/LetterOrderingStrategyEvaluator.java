package eval;

import factual.HangmanGame;
import factual.HangmanGameRunner;
import factual.strategy.LetterOrderingStrategy;
import org.junit.Test;

public class LetterOrderingStrategyEvaluator {

    @Test
    public void unit() {

        String[] words = new String[]{"aardvark", "aahing", "aa", "electroencephalographically", "ethylenediaminetetraacetates",
                "immunoelectrophoretically", "dichlorodifluoromethanes", "carboxymethylcellulose",
                "acetylcholinesterases", "acetylcholinesterase", "abdicable",
                "abdicated", "abdicates", "abdicator", "abdominal", "abductees"};

        for (String word: words) {
            HangmanGame game = new HangmanGame(word, Math.max(5, word.length()));
            HangmanGameRunner runner = new HangmanGameRunner();
            runner.run(game, new LetterOrderingStrategy());
            System.out.println("(" + word.length() + ") " + game + "; word length ");
        }

    }

}
