package common;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Files {
    /**
     * Método para leer datos de un archivo
     * 
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public static List<String> loadFile(String filePath) throws FileNotFoundException {
        if (filePath == null || filePath.length() < 1) {
            throw new IllegalArgumentException("Filename may not be null or empty");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("No file in " + file.getAbsolutePath());
        }
        List<String> data = new ArrayList<String>();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            while (in.ready()) {
                String line = in.readLine();
                if (line.length() > 0) {
                    data.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        return data;
    }
}