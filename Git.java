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

    ///
    /// MAKE A BLOB OF THE FILE AT FILEPATH
    /// INSIDE OF THE GIT/OBJECTS FOLDER WHICH IS FOUND IN THE REPO
    /// THE NAME OF THE BLOB IS THE SHA1 OF THE CONTENTS
    /// THE CONTENTS OF THE BLOB ARE THE COMPRESSED ORIGINAL CONTENTS
    ///
    public static void makeBlob(String filePath, String repoPath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Can't Blob A Nonexistent File!");
            return;
        }
        String fileContents = Utils.readFile(filePath);
        Utils.makeFile(repoPath + "/git/objects/" + Utils.SHA1(fileContents), Utils.compress(fileContents));
    }

    public static void stageFile(String filePath, String repoPath) throws IOException {
        makeBlob(filePath, repoPath);
    }

    ///
    /// ADD THE FILE AT THE FILE PATH TO THE INDEX FOLDER
    ///
    public static void indexFile(String filePath, String repoPath) throws IOException {
        // write the hash of the file + space + name of file into the index file
        Utils.write(repoPath + "/git/index", Utils.SHA1(Utils.readFile(filePath)) + " " + new File(filePath).getName());
    }
}
