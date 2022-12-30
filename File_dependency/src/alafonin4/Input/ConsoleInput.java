package alafonin4.Input;

import java.io.File;
import java.util.Scanner;

public class ConsoleInput implements Input {
    private static final Scanner scanner = new Scanner(System.in);
    ConsoleInput() {}
    public static String getPathToReferenceDirectory() {
        System.out.print("Enter path to folder: ");
        String path = scanner.nextLine();
        File f = new File(path);

        if (f.exists()) {

            if (f.isDirectory()) {
                return path;
            } else {
                System.out.println("The line you entered is the path to the file, not the folder");
                return getPathToReferenceDirectory();
            }

        } else {
            System.out.println("The line you entered is the path to a non-existent folder or file.");
            return getPathToReferenceDirectory();
        }
    }
    public static boolean shouldRepeat() {
        System.out.print("Enter y, if you want to continue: ");
        String answer = scanner.nextLine();

        if (answer.equals("y")) {
            return true;
        } else {
            return false;
        }
    }
}
