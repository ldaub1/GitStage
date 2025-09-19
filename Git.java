import java.io.*;

public class Git {
    ///
    /// INITIALIZES REPOSITORY INCLUDING GIT FOLDER
    /// WITH OBJECTS FOLDER AND INDEX FILE
    ///
    public static void initializeRepo(String repoPath) throws IOException {
        // initialize main folder, if it doesn't exist already
        if (repoPath.equals(""))
            System.out.println("Can't Name Repository An Empty String");
        Utils.makeDir(repoPath);

        // initialize git folder inside of main folder, unless one already exists
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
