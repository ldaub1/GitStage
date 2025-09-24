import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // initializes repo
        Git.initializeRepo("ProjectFolder");
        // makes test files
        Utils.makeFile("ProjectFolder/CoolFile", "RANDOM DATA");
        Utils.makeFile("ProjectFolder/otherCoolFile", "ASDFFFF");
        // blob and index the files
        Git.refresh("ProjectFolder");

        // verify they match
        System.out.println("INDEX FILE CONTENTS: \n" + Utils.readFile("ProjectFolder/git/index"));
        System.out.println("HASH OF COOLFILE: " + Utils.SHA1(Utils.readFile("ProjectFolder/CoolFile")));
        System.out.println("HASH OF OTHERCOOLFILE: " + Utils.SHA1(Utils.readFile("ProjectFolder/otherCoolFile")));

        // deleting the files for those weird-ahh stretch goals. delete this if you want
        // to see the actual stuff it creates
        clearFiles("ProjectFolder");
    }

    public static void clearFiles(String repo) throws IOException {
        Utils.deleteDirectory(repo);
    }
}
