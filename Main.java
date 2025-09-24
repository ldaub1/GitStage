import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Git.initializeRepo("ProjectFolder");
        Utils.makeFile("ProjectFolder/CoolFile", "RANDOM DATA");
        Git.refresh("ProjectFolder");
    }
}
