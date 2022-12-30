package alafonin4.Output;

import alafonin4.Services.Operations;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileOutput implements OutputInFile {
    public static void writeAllTextInFiles(File[] files, String path) throws IOException {
        List<String> allLinesFromFiles = new ArrayList<String>(0);
        for (File file : files) {
            List<String> linesFromFile = Operations.readAllLInesFromFile(file.getAbsolutePath());
            allLinesFromFiles.addAll(linesFromFile);
        }
        path += "\\Merged_files.txt";
        File newFile = new File(path);
        try
        {
            boolean created = newFile.createNewFile();
            if (created) {
                writeInFile(path, allLinesFromFiles);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    private static void writeInFile(String path, List<String> allLinesFromFiles) {
        try (FileWriter writer = new FileWriter(path, false))
        {
            for (String line:
                    allLinesFromFiles) {
                writer.write(line);
            }
            writer.flush();
        }
        catch(IOException ex){
            System.out.println("Error when writing to a file.");
        }
    }
}
