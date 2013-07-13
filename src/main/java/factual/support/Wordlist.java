package factual.support;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Wordlist extends ArrayList<Word> {

    public static Wordlist loadFromFile(String filename) {

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

}
