package sandbox;

import factual.GuessLetter;
import factual.GuessWord;
import factual.HangmanGame;
import factual.HangmanGameRunner;
import factual.strategy.OracleStrategy;
import org.apache.log4j.Logger;
import org.junit.Test;
import sys.Utils;

public class GameRunner {

    Logger logger = Logger.getLogger(GameRunner.class);

    static {
        Utils.initLogging();
    }

    @Test
    public void testOracleStrategy() {
        String word = "strategy";
        HangmanGame game = new HangmanGame(word, 4);

        HangmanGameRunner runner = new HangmanGameRunner();
        int sc = runner.run(game, new OracleStrategy(word));

        logger.info(game);
    }

    @Test
    public void runSampleGame() {
        HangmanGame game = new HangmanGame("factual", 4); // secret word is factual, 4 wrong guesses are allowed
        System.out.println(game);
        new GuessLetter('a').makeGuess(game);
        System.out.println(game);
        new GuessWord("natural").makeGuess(game);
        System.out.println(game);
        new GuessLetter('x').makeGuess(game);
        System.out.println(game);
        new GuessLetter('u').makeGuess(game);
        System.out.println(game);
        new GuessLetter('l').makeGuess(game);
        System.out.println(game);
        new GuessWord("factual").makeGuess(game);
        System.out.println(game);
    }

    @Test
    public void looseSampleGame() {
        HangmanGame game = new HangmanGame("factual", 4); // secret word is factual, 4 wrong guesses are allowed
        System.out.println(game);

        try {

            String word = "rmnopq";
            for (int i=0; i<word.length(); i++) {
                new GuessLetter(word.charAt(i)).makeGuess(game);
                System.out.println(game);
            }

        } catch (IllegalStateException ex) {
            logger.error("IllegalStateException " + ex.getMessage());
        }

    }

}
