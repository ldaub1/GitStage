import java.io.*;


public class Main {
    public static void main(String[] args) throws IOException {

        clearFiles("ProjectFolder");

        wrapperTest();

        // clearFiles("ProjectFolder");
        // // initializes repo
        // Git.initializeRepo("ProjectFolder");
        // // makes test files
        // Utils.makeFile("ProjectFolder/CoolFile", "RANDOM DATA");
        // Utils.makeFile("ProjectFolder/otherCoolFile", "ASDFFFF");
        // Utils.makeFile("ProjectFolder/otherCoolFile2", "other cool file 2");
        // Utils.makeFile("ProjectFolder/CoolFile2", "RANDOM DATA PART TWOOO");
        // // blob and index the files
        // Git.refresh("ProjectFolder");
        // Git.buildCommit("author", "wow message here");
        // Git.buildCommit("author2", "wow 2nd message here");

        // // verify they match
        // System.out.println("INDEX FILE CONTENTS: \n" + Utils.readFile("ProjectFolder/git/index"));
        // System.out.println("HASH OF COOLFILE: " + Utils.SHA1(Utils.readFile("ProjectFolder/CoolFile")));
        // System.out.println("HASH OF OTHERCOOLFILE: " + Utils.SHA1(Utils.readFile("ProjectFolder/otherCoolFile")));

        // deleting the files for those weird-ahh stretch goals. delete this if you want
        // to see the actual stuff it creates
        // clearFiles("ProjectFolder");


        // Git.recursiveBuildTree();

    }

    public static void clearFiles(String repo) throws IOException {
        Utils.deleteDirectory(repo);
    }

    public static void wrapperTest() {
        GitWrapper test = new GitWrapper();
        test.init();
    }

}
