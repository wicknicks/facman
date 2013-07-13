package factual.strategy;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import factual.*;
import factual.support.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PairEliminationStrategy implements GuessingStrategy
{
    private Wordlist words;
    private List<Character> guesses = new ArrayList<Character>();

    private PairIndex pairIndex = new PairIndex();
    private LetterPositionIndexer positionIndexer = new LetterPositionIndexer();
    private CharFrequencyCounter frequencyCounter = new CharFrequencyCounter();

    public PairEliminationStrategy(Wordlist words) {
        this.words = words;
        buildAllIndexes();
    }

    private void buildAllIndexes() {
        pairIndex.index(words);
        positionIndexer.buildIndex(words);
    }

    @Override
    public Guess nextGuess(HangmanGame game) {
        if (guesses.size() > 0 && game.getCorrectlyGuessedLetters().contains(guesses.get(guesses.size() - 1)))
            spotElimination(game, guesses.get(guesses.size() - 1));

        if (guesses.size() > 0 && game.getIncorrectlyGuessedLetters().contains(guesses.get(guesses.size() - 1)))
            wordElimination(guesses.get(guesses.size() - 1));

        Wordlist tmp = new Wordlist();
        tmp.addAll(Collections2.filter(words, new Predicate<Word>() {
            @Override
            public boolean apply(Word word) {
                return word.indexable;
            }
        }));
        words = tmp;

        buildAllIndexes();

        int k=0;
        Character c = null;
        while (true) {
            String s = pairIndex.findMostPopularKey(k);
            if (s == null) break;
            c = s.charAt(0);
            if ( !guesses.contains(c) ) break;
            c = s.charAt(1);
            if ( !guesses.contains(c) ) break;
            k++; c=null;
        }

        //found a vowel
        if (c != null) {
            guesses.add(c);
            return new GuessLetter(c);
        }

        //did not find a vowel which can be thrown at this word
        if (game.getCorrectlyGuessedLetters().size() > 0) {
            Set<Integer> bestGuessesSoFar = intersect(game);
            if (bestGuessesSoFar.size() == 1) return new GuessWord(words.get(bestGuessesSoFar.iterator().next()).word);
        }

        frequencyCounter.count(this.words);

        k=0;
        while (true) {
            c = frequencyCounter.getMostFrequenceLetter(k);
            if ( !guesses.contains(c) ) break;
            k++;
        }

        guesses.add(c);
        return new GuessLetter(c);
    }

    private Set<Integer> intersect(HangmanGame game) {
        Set<Integer> bestGuessIndexes = new HashSet<Integer>();
        for (int i=0; i<words.size(); i++) bestGuessIndexes.add(i);

        int count = 0;
        char[] gamestate = game.getGuessedSoFar().toCharArray();
        for (char ch: gamestate) {
            if (ch != HangmanGame.MYSTERY_LETTER) count++;
        }

        if (count == 1) return bestGuessIndexes;

        for (int i=0; i<gamestate.length; i++) {
            if (gamestate[i] == HangmanGame.MYSTERY_LETTER) continue;
            Set<Integer> lookupIndexes = positionIndexer.lookup(gamestate[i], i);
            ImmutableSet<Integer> common = Sets.intersection(bestGuessIndexes, lookupIndexes).immutableCopy();

            bestGuessIndexes.clear();
            bestGuessIndexes.addAll(common);
        }

        return bestGuessIndexes;
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
