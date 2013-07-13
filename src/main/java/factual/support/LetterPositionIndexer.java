package factual.support;

import java.util.*;

public class LetterPositionIndexer {

    HashMap<String, IndexItem> index = new HashMap<String, IndexItem>();

    public void buildIndex(Wordlist words) {
        int i=0;
        for (Word word: words) {
            if ( word.indexable ) index(word.word, i);
            i++;
        }

        //System.out.println("Items in Index " + index.keySet().size());

    }

    private void index(String word, int position) {
        int i=0;
        for (char ch: word.toCharArray()) {
            String s = buildKey(ch, i);
            if ( !index.containsKey(s) ) index.put(s, new IndexItem(ch, i));
            index.get(s).wordlistIndexes.add(position);
            i++;
        }
    }

    private String buildKey(char ch, int i) {
        return String.valueOf(ch) + i;
    }

    public Set<Integer> lookup(char character, int position) {
        String key = buildKey(character, position);
        return index.get(key).wordlistIndexes;
    }

    public static class IndexItem {
        char character;
        int position;
        Set<Integer> wordlistIndexes = new HashSet<Integer>();

        public IndexItem(char c, int pos) {
            this.character = c;
            this.position = pos;
        }

    }

}
