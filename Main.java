import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Git.initializeRepo("ProjectFolder");
        Utils.makeFile("ProjectFolder/ProjectFiles.txt", "Some File Contents");
        Git.stageFile("ProjectFolder/ProjectFiles.txt", "ProjectFolder");
        Git.indexFile("ProjectFolder/ProjectFiles.txt", "ProjectFolder");
    }
}
