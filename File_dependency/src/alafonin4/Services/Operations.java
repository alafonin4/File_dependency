package alafonin4.Services;

import alafonin4.FilesInformation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Operations {
    public static File[] getAllFiles(File[] files, List<File> f) {
        for (File file : files) {
            if (file.isDirectory()) {
                getAllFiles(file.listFiles(), f);
            } else {
                f.add(file);
            }
        }
        File[] files1 = new File[f.size()];
        f.toArray(files1);
        return files1;
    }
    public static File[] getSortedFiles(File[] files, String path) throws IOException {
        List<File> sortedFiles = new ArrayList<File>(0);
        List<FilesInformation> filesWithoutDep = getAllFilesDependence(files, false);
        List<FilesInformation> filesWithDep = getAllFilesDependence(files, true);
        List<FilesInformation> filesW = addAllDependence(filesWithDep, filesWithoutDep, path);

        for (FilesInformation f : filesW) {
            sortedFiles.add(f.getFile());
        }

        File[] files1 = new File[sortedFiles.size()];
        sortedFiles.toArray(files1);

        return files1;
    }
    public static List<String> readAllLInesFromFile(String path) throws IOException {
        return Files.readAllLines(Paths.get(path));
    }
    private static List<FilesInformation> getAllFilesDependence(File[] files, boolean dependence) throws IOException {
        List<FilesInformation> filesIndirs = new ArrayList<FilesInformation>(0);
        for (var file : files) {
            List<String> content = readAllLInesFromFile(file.getAbsolutePath());
            boolean fl = false;
            for (String str : content) {
                Pattern pattern = Pattern.compile("require '.+?'");
                Matcher matcher = pattern.matcher(str);
                while (matcher.find()) {
                    fl = true;
                }
            }
            if (fl == dependence) {
                filesIndirs.add(new FilesInformation(file, content));
            }
        }
        return filesIndirs;
    }
    private static List<FilesInformation> addAllDependence(List<FilesInformation> filesIndirs,
                                                     List<FilesInformation> filesWithoutDep,
                                                     String path) {
        if (filesWithoutDep.size() == 0) {
            throw new IllegalArgumentException("Impossible to sort array of files, " +
                    "because files depends each other");
        }
        for (FilesInformation file : filesIndirs) {
            var content = file.getContent();
            for (String str : content) {
                Pattern pattern = Pattern.compile("require '.+?'");
                Matcher matcher = pattern.matcher(str);
                while (matcher.find()) {
                    String partOfPath = str.substring(matcher.start() + 9, matcher.end() - 1);
                    if ((partOfPath.contains("\\"))
                            && (File.pathSeparatorChar == '/')) {
                        partOfPath = partOfPath.replace('/', '\\');
                    }
                    if ((partOfPath.contains("/"))
                            && (File.pathSeparatorChar == '\\')) {
                        partOfPath = partOfPath.replace('/', '\\');
                    }
                    String allPath = path + partOfPath;
                    file.add(allPath);
                }
            }
        }
        List<FilesInformation> filesIndirs1 = new ArrayList<FilesInformation>(0);
        filesIndirs1.addAll(filesWithoutDep);
        int count1 = 0;
        for (FilesInformation file : filesIndirs) {
            for (String s : file.getFilesWhichDepends()) {
                int count = 0;
                for (FilesInformation f : filesIndirs1) {
                    if (f.getFile().getAbsolutePath().equals(s)) {
                        count++;
                    }
                }

                if (count == file.getFilesWhichDepends().size()) {
                    boolean fl = false;
                    for (FilesInformation f:
                         filesIndirs1) {
                        if (f.getFilesWhichDepends().contains(s)) {
                            fl = true;
                        }
                    }
                    if (!fl) {
                        filesIndirs1.add(file);
                    } else {
                        throw new IllegalArgumentException("Impossible to sort array of files, " +
                                "because files depends each other");
                    }
                }
            }
            count1++;
            if (count1 == filesIndirs.size()) {
                filesIndirs1.add(file);
            }
        }
        return filesIndirs1;
    }
}
