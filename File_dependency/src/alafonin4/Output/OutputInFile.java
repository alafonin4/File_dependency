package alafonin4.Output;

import java.io.File;
import java.io.IOException;

public interface OutputInFile extends Output {
    public static void writeAllTextInFiles(File[] files, String path) throws IOException{};
}
