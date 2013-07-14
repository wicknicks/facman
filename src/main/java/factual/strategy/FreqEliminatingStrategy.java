package factual.strategy;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import factual.*;
import factual.support.CharFrequencyCounter;
import factual.support.LetterPositionIndexer;
import factual.support.Word;
import factual.support.Wordlist;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FreqEliminatingStrategy implements GuessingStrategy {

    private Wordlist words = null;
    private List<Character> guesses = new ArrayList<Character>();

    private LetterPositionIndexer positionIndexer = new LetterPositionIndexer();
    private CharFrequencyCounter frequencyCounter = new CharFrequencyCounter();

    public FreqEliminatingStrategy(Wordlist words) {
        this.words = words;
        positionIndexer.buildIndex(this.words);
    }

    @Override
    public Guess nextGuess(HangmanGame game) {

        if (game.getCorrectlyGuessedLetters().size() > 0) {
            Set<Integer> bestGuessesSoFar = intersect(game);
            //System.out.println("# of best guesses = " + bestGuessesSoFar.size());
            if (bestGuessesSoFar.size() == 1) return new GuessWord(words.get(bestGuessesSoFar.iterator().next()).word);

            //reduce word list
            if (bestGuessesSoFar.size() < words.size()) {
                Wordlist reducedwordlist = new Wordlist();
                for (int ix: bestGuessesSoFar) {
                    reducedwordlist.add(words.get(ix));
                }
                words = reducedwordlist;
                positionIndexer.buildIndex(this.words);
//                System.out.println("Reducing Wordlist ..........");
            }

        }

        //eliminate all the words which don't contain correct guesses at their respective spots
        if (guesses.size() > 0 && game.getCorrectlyGuessedLetters().contains(guesses.get(guesses.size() - 1)))
            spotElimination(game, guesses.get(guesses.size() - 1));

        if (guesses.size() > 0 && game.getIncorrectlyGuessedLetters().contains(guesses.get(guesses.size() - 1)))
            wordElimination(guesses.get(guesses.size() - 1));

        System.out.println("Size of wordlist = " + this.words.size());
        frequencyCounter.count(this.words);

        int k=0;
        char c;
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
//        System.out.println("Spot eliminating " + character);
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
            if (w.word.indexOf(c) >= 0) w.indexable = false;
        }

    }

}
