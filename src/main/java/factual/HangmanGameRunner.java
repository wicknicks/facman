package factual;

/**
 * Run the hangman game using the pseudocode given by Ron.
 *
 * <pre> {@code

 // runs your strategy for the given game, then returns the score:
 public int run(HangmanGame game, GuessingStrategy strategy) {
     while (game has not been won or lost) {
        ask the strategy for the next guess
        apply the next guess to the game
     }
     return game.score();
   }
 }
  </pre>
 *
 */

public class HangmanGameRunner {

    public int run(HangmanGame game, GuessingStrategy strategy) {

        while (game.gameStatus() == HangmanGame.Status.KEEP_GUESSING) {
            Guess guess = strategy.nextGuess(game);
            guess.makeGuess(game);

            //System.out.println("Guessing " + guess);
            //System.out.println(game);
        }

        //System.out.println(game);
        return game.currentScore();

    }

}
