package support;

import factual.support.CharFrequencyCounter;
import factual.support.LetterPositionIndexer;
import factual.support.Wordlist;
import org.junit.Test;

import java.io.IOException;

public class LetterPositionIndexerTests {

    @Test
    public void indexTest() throws IOException {
        String filename = "/home/arjun/Sandbox/factual-hangman/data/11.sp.txt";
        LetterPositionIndexer indexer = new LetterPositionIndexer();

        Wordlist wordlist = Wordlist.loadFromFile(filename);
        (new CharFrequencyCounter()).count(wordlist);
        indexer.buildIndex(wordlist);
    }

}
