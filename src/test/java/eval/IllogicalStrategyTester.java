package eval;

import factual.HangmanGame;
import factual.HangmanGameRunner;
import factual.strategy.IllogicalReductionStrategy;
import factual.support.Wordlist;
import org.junit.Test;

public class IllogicalStrategyTester {
    @Test
    public void singleWordGuessTest() {
        String word = "yolked";
        int chances = 6;
        System.out.println("Word = " + word + " ; chances = " + chances);

        HangmanGame game = new HangmanGame(word, chances);
        HangmanGameRunner runner = new HangmanGameRunner();

        runner.run(game, new IllogicalReductionStrategy(Wordlist.loadFromFile("data/" + word.length() + ".sp.txt")));
        System.out.println("(" + word.length() + ") " + game);
    }


    @Test
    public void unit() {

        String[] words = new String[]{"poachy",
                "podium",
                "pogeys",
                "pokily",
                "cicale",
                "citing",
                "pouffs",
                "clammy",
                "prevue",
                "clinks",
                "prises",
                "purism",
                "pyrrol",
                "python",
                "cloyed",
                "quaffs",
                "clunks",
                "quokka",
                "coifed"
        };

        for (String word: words) {
            HangmanGame game = new HangmanGame(word, Math.max(5, word.length()));
            HangmanGameRunner runner = new HangmanGameRunner();
            runner.run(game, new IllogicalReductionStrategy(Wordlist.loadFromFile("data/" + word.length() + ".sp.txt")));
            System.out.println(word + " " + game);
        }

    }
}
