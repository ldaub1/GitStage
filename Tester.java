import java.io.*;

public class Tester {
    public static void main(String[] args) throws IOException {
        // createTestRepos();
        // removeTestRepos();
        Git.initializeRepo("");
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
