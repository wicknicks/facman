package factual.support;

/**
 * Word class is where we store each possible word.
 * The indexable parameter determines if the word is
 * still eligible to be guessed.
 */

public class Word {

    public static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String ORDERING = "ESIARNTOLCDUPMGHBYFVKWZXQJ";
    public static String VOWELS = "AEIOU";

    public String word;
    public boolean indexable = true;

    public Word (String word) {
        this.word = word;
    }

}
