package factual.strategy;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import factual.*;
import factual.support.*;

import java.util.ArrayList;
import java.util.List;

public class OptimisticConsonant implements GuessingStrategy {

    private Wordlist words = null;
    private List<Character> guesses = new ArrayList<Character>();

    private PairIndex pairIndex = new PairIndex();
    private LetterPositionIndexer positionIndexer = new LetterPositionIndexer();
    private CharFrequencyCounter frequencyCounter = new CharFrequencyCounter();

    public OptimisticConsonant(Wordlist words) {
        this.words = words;
        positionIndexer.buildIndex(this.words);
    }

    private void buildAllIndexes() {
        ///System.out.println("Size of Wordlist = " + words.size() + " " + game);
        pairIndex.index(words);
        positionIndexer.buildIndex(words);
        frequencyCounter.count(this.words);
    }

    public Wordlist words() {
        return this.words;
    }

    @Override
    public Guess nextGuess(HangmanGame game) {
        if (guesses.size() > 0 && game.getCorrectlyGuessedLetters().contains(guesses.get(guesses.size() - 1)))
            spotElimination(game, guesses.get(guesses.size() - 1));

        if (guesses.size() > 0 && game.getIncorrectlyGuessedLetters().contains(guesses.get(guesses.size() - 1)))
            wordElimination(guesses.get(guesses.size() - 1));

        //if (words.size() < 10) System.out.println(game.numWrongGuessesRemaining() + " " + words);
        if (words.size() == 0) throw new RuntimeException("out of words....!");

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

        Character c = null;

        buildAllIndexes();
        System.out.println(game + " " + game.numWrongGuessesRemaining() + " ;; Size of Wordlist = " + words.size());

        int k=0;
        while (true) {

            //System.out.println("freq consonant");
            c = frequencyCounter.getMostFrequentConsonant(k);

            if (c == null) break;
            if ( !guesses.contains(c) ) break;
            c = null;
            k++;
            if (k >= 1) break;
        }

        if (c != null) {
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
