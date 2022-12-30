package alafonin4;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilesInformation {
    private File file;
    private List<String> content = new ArrayList<String>(0);
    private Set<String> filesWhichDepends = new HashSet<String>(0);
    public FilesInformation(File file, List<String> content) {
        this.file = file;
        this.content.addAll(content);
    }
    public void add(String path) {
        File f = new File(path);
        if ((f.exists()) && (!f.isDirectory())) {
            filesWhichDepends.add(path);
        }
    }

    public File getFile() {
        return file;
    }

    public Set<String> getFilesWhichDepends() {
        return filesWhichDepends;
    }

    public List<String> getContent() {
        return content;
    }
}