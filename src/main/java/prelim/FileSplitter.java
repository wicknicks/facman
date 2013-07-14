package prelim;

import java.io.*;

/**
 * Split the given words.txt file into 1.sp.txt, 2.sp.txt. 3.sp.txt .... 28.sp.txt
 * Each m.sp.txt contains words which are m characters long.
 */

public class FileSplitter {

    public void split(String file, FileAllocCriterion criterion, BufferedWriter[] writers) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String tmp;
        while (true) {
            tmp = reader.readLine();
            if (tmp == null) break;
            if (tmp.length() < 1) continue;
            writers[ criterion.allocate(tmp) ].write(tmp);
            writers[ criterion.allocate(tmp) ].write("\n");
        }

        for (BufferedWriter writer : writers) writer.flush();
        reader.close();
    }

    public static class FileAllocCriterion {
        public int allocate(String line) {
            return line.length();
        }
    }

}
