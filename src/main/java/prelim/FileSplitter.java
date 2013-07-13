package prelim;

import java.io.*;

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
