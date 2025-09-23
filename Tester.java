import java.io.*;

public class Tester {
    public static void main(String[] args) throws IOException {
        // createTestRepos();
        // removeTestRepos();
        // System.out.println(Utils.SHA1("asdfasdf"));

        // init repo
        Git.initializeRepo("ProjectFolder");
        // make file in project folder
        Utils.makeFile("ProjectFolder/ProjectFile", "contents");
        // blob the file in the projects folder
        Git.makeBlob("ProjectFolder/ProjectFile", "ProjectFolder");
        // make sure it exists and we can read it (its compressed)
        checkForObject("ProjectFolder", "ProjectFolder/ProjectFile");
    }

    public static void checkForObject(String projectName, String filePath) throws IOException {
        System.out.println(
                Utils.readFile(
                        projectName + "/git/objects/" + Utils.SHA1(Utils.readFile(filePath))));
    }

    public static void createTestRepos() throws IOException {
        for (int i = 0; i < 1000; i++) {
            Git.initializeRepo("TEST REPO " + i);
        }
    }

    public static void removeTestRepos() throws IOException {
        for (int i = 0; i < 1000; i++) {
            Git.deleteRepo("TEST REPO " + i);
        }
    }
}
