package factual.strategy;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import factual.*;
import factual.support.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IllogicalReductionStrategy implements GuessingStrategy {

    private Wordlist words = null;
    private List<Character> guesses = new ArrayList<Character>();

    private Random generator = new Random();

    private PairIndex pairIndex = new PairIndex();
    private LetterPositionIndexer positionIndexer = new LetterPositionIndexer();
    private CharFrequencyCounter frequencyCounter = new CharFrequencyCounter();

    public IllogicalReductionStrategy(Wordlist words) {
        this.words = words;
        positionIndexer.buildIndex(this.words);
    }

    private void buildAllIndexes() {
        //System.out.println("Size of Wordlist = " + words.size());
        pairIndex.index(words);
        positionIndexer.buildIndex(words);
        frequencyCounter.count(this.words);
    }

    @Override
    public Guess nextGuess(HangmanGame game) {
        if (guesses.size() > 0 && game.getCorrectlyGuessedLetters().contains(guesses.get(guesses.size() - 1)))
            spotElimination(game, guesses.get(guesses.size() - 1));

        if (guesses.size() > 0 && game.getIncorrectlyGuessedLetters().contains(guesses.get(guesses.size() - 1)))
            wordElimination(guesses.get(guesses.size() - 1));

        if (words.size() == 0) {
            //throw new RuntimeException("out of words....!"); //
            int ix = 0;
            while (true) {
                char c = Word.ORDERING.charAt(ix);
                if ( !guesses.contains(c) ) {
                    //System.out.println(c);
                    guesses.add(c);
                    return new GuessLetter(c);
                }
                ix++;
            }
        }
        //if (words.size() < 30) System.out.println(game.numWrongGuessesRemaining() + " " + words);

        int _chances = 1; //game.numWrongGuessesRemaining();
        if (words.size() <= _chances) {
            GuessWord gw = new GuessWord(words.get(0).word);
            words.remove(0);
            return gw;
        }

        Wordlist tmp = new Wordlist();
        tmp.addAll(Collections2.filter(words, new Predicate<Word>() {
            @Override
            public boolean apply(Word word) {
                return word.indexable;
            }
        }));
        words = tmp;

        illogicalReduction(game);

        Character c = null;

        buildAllIndexes();
        int k=0;
        while (true) {
            c = frequencyCounter.getMostFrequentConsonant(k);
            if (c == null) break;
            if ( !guesses.contains(c) ) break;
            c = null;
            k++;
            if (k >= 1) break;
        }

        if (c != null) {
            //System.out.println("Consonant = " + c);
            guesses.add(c);
            return new GuessLetter(c);
        }

        k=0;
        while (true) {
            c = frequencyCounter.getMostFrequenceLetter(k);
            if ( !guesses.contains(c) ) break;
            k++;
        }

        guesses.add(c);
        return new GuessLetter(c);
    }

    private void illogicalReduction(HangmanGame game) {
        if (game.numWrongGuessesRemaining() <= 1 && words.size() >= 10) {
            Wordlist tmp = new Wordlist();
            tmp.addAll(Collections2.filter(words, new Predicate<Word>() {
                @Override
                public boolean apply(Word word) {
                    return generator.nextInt(100) > 30;
                }
            }));
            words = tmp;
            //System.out.println("After desperate reduction, size = " + words.size());
        }

    }

    private void spotElimination(HangmanGame game, char character) {
        int i=0;
        for (char statusChar: game.getGuessedSoFar().toCharArray()) {
            if (statusChar == character) {
                spotElimination(character, i);
            }
            i++;
        }
    }

    private void spotElimination(char character, int position) {
        for (Word w: words) {
            if ( !w.indexable ) continue;
            if (w.word.charAt(position) != character)
                w.indexable = false;
        }
    }


    private void wordElimination(char c) {

        for (Word w: words) {
            if ( !w.indexable ) continue;
            if (w.word.indexOf(c) >= 0)
                w.indexable = false;
        }

    }

}
