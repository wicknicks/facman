package factual.support;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Contains a list of words, and can be loaded from a particular file using the loadFromFile method()
 */

public class Wordlist extends ArrayList<Word> {

    public static Wordlist loadFromFile(String filename) {

        File file = new File(filename);
        if ( !file.exists() ) {
            System.out.println(filename + " does not exist. Did you run:");
            System.out.println("the Dataset.shardFile() test?");
            System.exit(1);
        }

        Wordlist words = new Wordlist();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String tmp;

            while (true) {
                tmp = reader.readLine();
                if (tmp == null) break;
                if (tmp.length() < 1) continue;
                words.add(new Word(tmp.toUpperCase()));      //ensures that we all talk in uppercase
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return words;

    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Word w: this) {
            s.append(w.word);
            s.append(' '); //nasty
        }
        return s.substring(0);
    }

}
