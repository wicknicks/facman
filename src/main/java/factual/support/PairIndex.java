package factual.support;

import java.util.*;

public class PairIndex {

    private HashMap<String, Set<Integer>> index = new HashMap<String, Set<Integer>>();
    private Popularity[] counts;

    private void initIndex() {
        index.clear();
        for (char c1: Word.VOWELS.toCharArray()) {
            for (char c2: Word.VOWELS.toCharArray()) {
                index.put(String.valueOf(c1) + String.valueOf(c2), new HashSet<Integer>());
            }
        }
        counts = new Popularity[index.keySet().size()];
    }

    public void index(Wordlist words) {
        initIndex();
        ArrayList<Character> vowelsFound = new ArrayList<Character>();
        int positionIndex = 0;
        for (Word word: words) {
            positionIndex++;
            if ( !word.indexable ) continue;
            vowelsFound.clear();
            for (int i=0; i<word.word.length(); i++)
                if (Word.VOWELS.indexOf(word.word.charAt(i)) > -1)
                    vowelsFound.add(word.word.charAt(i));
            if (vowelsFound.size() <= 1) continue;
            for (int i=0; i<vowelsFound.size(); i++) {
                for (int j=0; j<vowelsFound.size(); j++) {
                    if (i != j)
                        index.get(buildKey(vowelsFound.get(i), vowelsFound.get(j))).add(positionIndex);
                }
            }
        }

        int i = 0;
        for (String key: index.keySet()) counts[i++] = new Popularity(key, index.get(key).size());

        Arrays.sort(counts, new Comparator<Popularity>() {
            @Override
            public int compare(Popularity o1, Popularity o2) {
                return o2.count - o1.count;
            }
        });
    }

    public Set<Integer> lookup(String key) {
        return index.get(key);
    }

    public String findMostPopularKey(int k) {
        if (k >= counts.length || lookup(counts[k].key).size() == 0)
            return null;
        return counts[k].key;
    }

    private class Popularity {
        String key;
        int count;

        public Popularity(String key, int count) {
            this.key = key;
            this.count = count;
        }
    }

    public void printCounts() {
        for (String key: index.keySet())
            System.out.println(key + " " + lookup(key).size());
    }

    private String buildKey(char vowel1, char vowel2) {
        char[] cc = new char[]{vowel1, vowel2};
//        System.out.println(cc);
        return String.valueOf(cc);
    }

}
