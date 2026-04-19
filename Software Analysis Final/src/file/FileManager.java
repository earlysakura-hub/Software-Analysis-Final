package file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public List<String[]> readCsv(String filePath) {
        List<String[]> rows = new ArrayList<String[]>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                rows.add(line.split(","));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows;
    }

    public void writeCsv(String filePath, List<String> lines) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void appendLine(String filePath, String line) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(line);
            bw.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}