package factual;

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
