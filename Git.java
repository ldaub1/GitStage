import java.io.*;

public class Git {
    ///
    /// INITIALIZES REPOSITORY INCLUDING GIT FOLDER
    /// WITH OBJECTS FOLDER AND INDEX FILE
    ///
    public static void initializeRepo(String repoPath) throws IOException {
        if (repoPath.equals(""))
            System.out.println("Can't Name Repository An Empty String");
        Utils.makeDir(repoPath);

        if (!Utils.makeDir(repoPath + "/git")) {
            System.out.println("Git Repository Already Exists");
            return;
        }
        Utils.makeDir(repoPath + "/git/objects");
        Utils.makeFile(repoPath + "/git/index");
        Utils.makeFile(repoPath + "/git/HEAD");
        System.out.println("Git Repository Created");
    }

    ///
    /// DELETES REPOSITORY AT GIVEN PATH
    /// RETURNS FALSE IF REPOSITORY NOT FOUND
    ///
    public static void deleteRepo(String repoPath) throws IOException {
        if (!Utils.deleteDirectory(repoPath)) {
            System.out.println("Repository Does Not Exist");
            return;
        }
        System.out.println("Repository Deleted Successfully");

    }
}
