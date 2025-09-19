import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Git.initializeRepo("ProjectFolder");
        Utils.makeFile("ProjectFolder/ProjectFiles", "Some File Contents");
        Git.stageFile("ProjectFolder/ProjectFiles", "ProjectFolder");
    }
}
