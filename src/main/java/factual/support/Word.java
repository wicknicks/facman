package factual.support;

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
