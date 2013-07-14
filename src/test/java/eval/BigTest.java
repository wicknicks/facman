package eval;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import factual.HangmanGame;
import factual.HangmanGameRunner;
import factual.strategy.FreqEliminatingStrategy;
import factual.strategy.IllogicalReductionStrategy;
import factual.strategy.PairEliminationStrategy;
import factual.strategy.OptimisticConsonant;
import factual.support.Wordlist;
import org.apache.log4j.Logger;
import org.junit.Test;
import sys.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;


public class BigTest {

    static {
        Utils.initLogging();
    }

    Logger logger = Logger.getLogger(BigTest.class);

    @Test
    public void testLargeDataset() throws Exception {

        String filename = System.getProperty("file");
        if (filename == null) filename = "data/8.sp.txt";

        logger.info("filename = " + filename);

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String word;

        int won=0; int lost=0;
        while (true) {
            word = reader.readLine();
            if (word == null) break;
            if (word.length() < 1) continue;

            int chances = 6;

            HangmanGame game = new HangmanGame(word, chances);
            HangmanGameRunner runner = new HangmanGameRunner();

            runner.run(game, new FreqEliminatingStrategy(Wordlist.loadFromFile(filename)));

            //logger.info("Word = " + word + " ; chances = " + chances + "; " + game.currentScore() + "\t" + game.gameStatus());

            if (game.gameStatus() == HangmanGame.Status.GAME_WON)
                won++;
            else {
                //System.out.println("LOST");
                lost++;
            }
        }

        logger.info("won = " + won + " ; lost = " + lost);

    }


    @Test
    public void testLimitedParallel() throws Exception {
        String filename = System.getProperty("file");
        if (filename == null) filename = "data/6.sp.txt";

        logger.info("filename = " + filename);

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String word;
        ArrayList<String> wordlist = new ArrayList<String>();

        while (true) {
            word = reader.readLine();
            if (word == null) break;
            if (word.length() < 1) continue;
            wordlist.add(word);
        }

        logger.info("Total: " + wordlist.size());
        int cores = Math.max(2, Runtime.getRuntime().availableProcessors() - 2);

        final Random generator = new Random();
        final int thresh = (120 * 1000 / wordlist.size());
        wordlist = new ArrayList<String>(Collections2.filter(wordlist, new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return generator.nextInt(100) < thresh;
            }
        }));

        logger.info("Total: " + wordlist.size());

        Gamer[] gamers = new Gamer[cores];
        Thread[] runners = new Thread[cores];
        int diff = wordlist.size()/cores;
        int i;
        for (i=0; i<cores-1; i++) {
            //logger.info(diff * i + " -> " + ((i + 1) * diff));
            gamers[i] = new Gamer(wordlist, diff * i, ((i+1)*diff));
            runners[i] = new Thread(gamers[i]);
            runners[i].start();
        }

        gamers[i] = new Gamer(wordlist, diff * i, wordlist.size());
        runners[i] = new Thread(gamers[i]);
        runners[i].start();
        //logger.info(diff * i + " -> " + wordlist.size());

        for (Thread runner : runners) runner.join();

        int won=0; int lost=0;
        for (i=0; i<gamers.length; i++) {
            won += gamers[i].won();
            lost += gamers[i].lost();
        }

        logger.info("won = " + won + " ; lost = " + lost + " " + filename);
    }

    @Test
    public void testParallel() throws Exception {

        String filename = System.getProperty("file");
        if (filename == null) filename = "data/19.sp.txt";

        logger.info("filename = " + filename);

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String word;
        ArrayList<String> wordlist = new ArrayList<String>();

        while (true) {
            word = reader.readLine();
            if (word == null) break;
            if (word.length() < 1) continue;
            wordlist.add(word);
        }

        logger.info("Total: " + wordlist.size());
        int cores = Math.max(2, Runtime.getRuntime().availableProcessors() - 2);
        Gamer[] gamers = new Gamer[cores];
        Thread[] runners = new Thread[cores];
        int diff = wordlist.size()/cores;
        int i;
        for (i=0; i<cores-1; i++) {
            //logger.info(diff * i + " -> " + ((i + 1) * diff));
            gamers[i] = new Gamer(wordlist, diff * i, ((i+1)*diff));
            runners[i] = new Thread(gamers[i]);
            runners[i].start();
        }

        gamers[i] = new Gamer(wordlist, diff * i, wordlist.size());
        runners[i] = new Thread(gamers[i]);
        runners[i].start();
        //logger.info(diff * i + " -> " + wordlist.size());

        for (Thread runner : runners) runner.join();

        int won=0; int lost=0;
        for (i=0; i<gamers.length; i++) {
            won += gamers[i].won();
            lost += gamers[i].lost();
        }

        logger.info("won = " + won + " ; lost = " + lost
                + " (" + ((100.0*won)/wordlist.size()) + ") ");
    }

    public static class Gamer implements Runnable {

        private final int start, end;
        private final List<String> words;
        private final int chances = 5;
        private int won=0, lost = 0;

        public Gamer(List<String> words, int start, int end) {
            this.words = words;
            this.start = start;
            this.end = end;
        }

        public int won() {
            return won;
        }

        public int lost() {
            return lost;
        }

        @Override
        public void run() {
            //System.out.println("Starting: " + start + " " + end);
            boolean t;
            for (int i = start; i < end; i++) {
                t = false;
                String word = words.get(i);

                HangmanGame game = new HangmanGame(word, chances);
                HangmanGameRunner runner = new HangmanGameRunner();

                //runner.run(game, new FreqEliminatingStrategy(Wordlist.loadFromFile("data/" + word.length() + ".sp.txt")));
                //runner.run(game, new PairEliminationStrategy(Wordlist.loadFromFile("data/" + word.length() + ".sp.txt")));
                //runner.run(game, new OptimisticConsonant(Wordlist.loadFromFile("data/" + word.length() + ".sp.txt")));
                runner.run(game, new IllogicalReductionStrategy(Wordlist.loadFromFile("data/" + word.length() + ".sp.txt")));
                if (game.gameStatus() == HangmanGame.Status.GAME_WON) t = true;

                /*
                game = new HangmanGame(word, chances);
                runner = new HangmanGameRunner();
                runner.run(game, new PairEliminationStrategy(Wordlist.loadFromFile("data/" + word.length() + ".sp.txt")));
                if (game.gameStatus() == HangmanGame.Status.GAME_WON) t = true;


                game = new HangmanGame(word, chances);
                runner = new HangmanGameRunner();
                runner.run(game, new OptimisticConsonant(Wordlist.loadFromFile("data/" + word.length() + ".sp.txt")));
                if (game.gameStatus() == HangmanGame.Status.GAME_WON) t = true;

                game = new HangmanGame(word, chances);
                runner = new HangmanGameRunner();
                runner.run(game, new IllogicalReductionStrategy(Wordlist.loadFromFile("data/" + word.length() + ".sp.txt")));
                if (game.gameStatus() == HangmanGame.Status.GAME_WON) t = true;

                */
                if (t) won++; else lost++;
                //if (game.gameStatus() == HangmanGame.Status.GAME_WON) won++;
                //else lost++;
            }
        }
    }
}
