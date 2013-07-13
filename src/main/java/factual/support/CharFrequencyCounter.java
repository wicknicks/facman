package factual.support;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CharFrequencyCounter {

    Counter[] counts = new Counter[Word.ALPHABET.length()];

    public void count(Wordlist words) {

        for (int i=0; i<counts.length; i++) {
            counts[i] = new Counter();
            counts[i].character = (char) ('A' + i);
            counts[i].percentage = 0;
        }

        for (Word w: words) {
            if ( !w.indexable ) continue;
            for (char ch: w.word.toCharArray()) {
                counts[ch - 'A'].percentage++;
            }
        }

//        for (int i=0; i<counts.length; i++)
//            counts[i].percentage = (counts[i].percentage * 100)/words.size();

        Arrays.sort(counts, new Comparator<Counter>() {
            @Override
            public int compare(Counter o1, Counter o2) {
                return (int) (o2.percentage - o1.percentage);
            }
        });
    }

    public void printCounts() {
        for (Counter c: counts) System.out.print(c.character);
        System.out.println();
    }

    //get kth most frequent character
    public char getMostFrequenceLetter(int k) {
        return counts[k].character;
    }

    public char getMidGuess() {
        int max = counts.length;
        for (int i=0; i<counts.length; i++)
            if (counts[i].percentage == 0) {
                max = i; break;
            }

        return counts[ max/2 ].character;
    }

    public static class Counter {
        char character;
        double percentage;

        public String toString() {
            return String.format("%c %.2f", character, percentage);
        }
    }

}
