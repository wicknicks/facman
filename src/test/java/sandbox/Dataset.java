package sandbox;

import factual.support.Wordlist;
import org.apache.log4j.Logger;
import org.junit.Test;
import prelim.FileSplitter;
import sys.Utils;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class Dataset {

    Logger logger = Logger.getLogger(Dataset.class);

    static {
        Utils.initLogging();
    }

    @Test
    public void shardFile() throws Exception {
        BufferedWriter[] writers = new BufferedWriter[29];
        for (int i=0; i<writers.length; i++)
            writers[i] = new BufferedWriter(new FileWriter("data/" + i + ".sp.txt"));
        (new FileSplitter()).split("data/words.txt", new FileSplitter.FileAllocCriterion(), writers);
        for (BufferedWriter writer : writers) writer.close();
    }

    @Test
    public void loadbadfile() {
        Wordlist.loadFromFile("something");
    }

    @Test
    public void buffReaderTest() throws Exception {
        long s = System.currentTimeMillis();
        BufferedReader reader = new BufferedReader(new FileReader("data/words.txt"));
        String tmp;

        int count = 0;
        int min = 100, max = -1;
        String ns="", xs="";
        while (true) {
            tmp = reader.readLine();
            if (tmp == null) break;
            if (tmp.length() < 1) continue;
            count++;
            if (tmp.length() < min) {
                min = tmp.length();
                ns = tmp;
            }
            if (tmp.length() > max) {
                max = tmp.length();
                xs = tmp;
            }
        }

        logger.info("Count = " + count);
        logger.info("Min = " + min + " " + ns);   // min = 2,  aa
        logger.info("Max = " + max + " " + xs);   // max = 28, ethylenediaminetetraacetates
        reader.close();

        long e = System.currentTimeMillis();
        logger.info(" Time Taken = " + (e-s));
    }

    @Test
    public void readDataset() throws Exception {

        long s = System.currentTimeMillis();

        String filename = "data/words.txt";
        FileChannel inChannel = new RandomAccessFile(filename, "r").getChannel();
        MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());

        // access the buffer as you wish.
        logger.info(buffer.remaining());

        Charset charset = Charset.forName("ASCII");
        CharBuffer buff = charset.decode(buffer);

        int count=0;
        for (int i=0; i<buff.length(); i++) {
            if (buff.get(i) == '\n') count++;
        }

        logger.info("Count = " + count);

        inChannel.close();

        long e = System.currentTimeMillis();
        logger.info(" Time Taken = " + (e-s));

    }

}
