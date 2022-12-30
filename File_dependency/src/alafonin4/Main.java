package alafonin4;

import alafonin4.Input.ConsoleInput;
import alafonin4.Output.FileOutput;
import alafonin4.Services.Operations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String... args) throws IOException {
        System.out.println("Welcome to the file dependency handler.");
        while (true) {
            String path = ConsoleInput.getPathToReferenceDirectory();

            File dir = new File(path);
            List<File> f = new ArrayList<File>(0);
            File[] files = Operations.getAllFiles(dir.listFiles(), f);
            if (files.length == 0) {
                System.out.println("The folder you entered isn't contains any files.");
                continue;
            }
            boolean isGranted = false;
            try {
                files = Operations.getSortedFiles(files, path);
                FileOutput.writeAllTextInFiles(files, path);
                isGranted = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            if (isGranted) {
                System.out.println("The final file that takes into account the dependencies is formed.");
            }
            if (!ConsoleInput.shouldRepeat()) {
                break;
            }
        }
        System.out.println("Thank you for using the services of the program!!");
    }
}