import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import javax.print.attribute.HashAttributeSet;

// import org.jcp.xml.dsig.internal.dom.Utils;

public class Git {
    private static boolean compression = false;
    private static ArrayList<String> workingListAL = new ArrayList<String>();

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
        Utils.makeFile(repoPath, "git/objects/" + Utils.SHA1(""));
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
        if (compression) {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Can't Blob A Nonexistent File!");
                return;
            }
            String fileContents = Utils.readFile(filePath);
            Utils.makeFile(repoPath + "/git/objects/" + Utils.SHA1(Utils.compress(fileContents).toString()),
                    Utils.compress(fileContents).toString());
        } else {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Can't Blob A Nonexistent File!");
                return;
            }
            String fileContents = Utils.readFile(filePath);
            Utils.makeFile(repoPath + "/git/objects/" + Utils.SHA1(fileContents),
                    fileContents);
        }
    }

    ///
    /// ADD THE FILE AT THE FILE PATH TO THE INDEX FOLDER
    ///
    public static void indexFile(String filePath, String repoPath) throws IOException {
        File indexFile = new File(repoPath + "/git/index");

        // the hash of the file + space + name of file
        String indexInfo = Utils.SHA1(Utils.readFile(filePath)) + " " + filePath.substring(filePath.indexOf("/") + 1);

        // add newline unless it's first
        if (indexFile.length() != 0)
            indexInfo = "\n" + indexInfo;

        // write it unless it already exists
        if (!Utils.readFile(indexFile.getPath()).contains(indexInfo)) {
            Utils.write(repoPath + "/git/index", indexInfo);
        }
    }

    public static void refresh(String repoPath) throws IOException {
        File mainRepo = new File(repoPath);
        File gitRepo = new File(repoPath + "/git");
        if (!gitRepo.exists())
            initializeRepo(repoPath);

        File[] projectFiles = mainRepo.listFiles();
        stageFiles(projectFiles);
    }

    public static void stageFiles(File[] files) throws IOException {
        for (File f : files) {
            if (f.getName().equals("git"))
                continue;
            if (f.isDirectory()) {
                File[] subFiles = f.listFiles();
                stageFiles(subFiles);
            } else {
                makeBlob(f.getPath(), f.getPath().substring(0, f.getPath().indexOf("/"))); // could this be erroring because pcs use \ and macbooks use /? --> changed "/" to "\\""
                indexFile(f.getPath(), f.getPath().substring(0, f.getPath().indexOf("/")));
            }
        }
    }

    // Description of makeInitialWorkingList(): This method reads through the index file and makes writes out all the blobs and paths to this working list 
    public static ArrayList<String> makeInitialWorkingList() throws IOException {
        String directoryPath = System.getProperty("user.dir"); // gets root folder 
        Utils.makeFile(directoryPath + "/workingFile.txt");
        String workingFileLOC = directoryPath + "/workingFile.txt";
        //  read through index file and look for path that has directoryPath in it 
        //int indexOfSlashBeforeFolderName = directoryPath.indexOf("/");
        String indexFileLoc = "ProjectFolder/git/index";
        String contentOfIndexFile = Utils.readFile(indexFileLoc);
        // System.out.println(contentOfIndexFile);
        String hashOFFile = "";
        String pathOfFile = "";
        String wordsInWorkingFile = "";
        int count = 0;
        int lastLineBreak = 0;
        ArrayList<String> hashNamesOfFile = new ArrayList<String>();
        for (int k = 0; k < contentOfIndexFile.length(); k++) {
            if (contentOfIndexFile.charAt(k) == 10) { // acii value of 10 equals a new line
                System.out.println(k);
                count++;
                if (count == 1) {
                    hashOFFile = contentOfIndexFile.substring(0, 40);
                    hashNamesOfFile.add(hashOFFile);
                    pathOfFile = contentOfIndexFile.substring(41, k);
                    //System.out.println("File 1: " + hashOFFile + " PATH: " + pathOfFile);
                    lastLineBreak = k;
                    wordsInWorkingFile = "blob " + hashOFFile + " " + pathOfFile;
                    Utils.write(workingFileLOC, wordsInWorkingFile + "\n");
                }
                if (count != 1) {
                    if (k != contentOfIndexFile.length() - 1) {
                        hashOFFile = contentOfIndexFile.substring(lastLineBreak + 1, lastLineBreak + 41);
                        if (hashNamesOfFile.contains(hashOFFile) == false) {
                            pathOfFile = contentOfIndexFile.substring(lastLineBreak + 41, k);
                            // System.out.println("file " + count + ":" + hashOFFile + " PATH: " + pathOfFile);
                            lastLineBreak = k;
                            wordsInWorkingFile = "blob " + hashOFFile + "" + pathOfFile;
                            Utils.write(workingFileLOC, wordsInWorkingFile + "\n");
                            hashNamesOfFile.add(hashOFFile);
                        } else {
                            lastLineBreak = k;
                        }
                    } else {
                        pathOfFile = contentOfIndexFile.substring(lastLineBreak + 41, k);
                        hashOFFile = contentOfIndexFile.substring(lastLineBreak + 1, lastLineBreak + 41);
                        if (hashNamesOfFile.get(0).equals(hashOFFile) == false) {
                            // System.out.println("file " + count + ":" + hashOFFile + " PATH: " + pathOfFile);
                            wordsInWorkingFile = "blob " + hashOFFile + "" + pathOfFile;
                            Utils.write(workingFileLOC, wordsInWorkingFile);
                            hashNamesOfFile.add(hashOFFile);
                        }
                    }
                }
            }

        }
        // creates arraylist of paths in workingFile and sets it equal to instance variable 
        String contentOfWorkinglist = Utils.readFile(workingFileLOC);
        ArrayList<String> workingListcontentAL = new ArrayList<String>();
        String word = "";
        int j = 0;
        for (int i = 0; i < contentOfWorkinglist.length(); i++) {
            if (contentOfWorkinglist.charAt(i) != 10) {
                j++;
                word = word + contentOfWorkinglist.charAt(i) + "";
            } else {
                // System.out.println(word);
                workingListcontentAL.add(word);
                word = "";
                j = 0;
            }
        }
        workingListAL = workingListcontentAL;
        return workingListcontentAL;
    }

    // reads through workingListcontentAL and counts how many slashes are in each element of that array. It then creates a new array called numberOfSlashesArrayList that stores the number of slashes that was found in the first sentence. If numberofSlashesArrayList.get(2) returns 3 then workingListcontentAL.get(2) has 3 slashes
    public static ArrayList<Integer> order() throws IOException {
        ArrayList<String> workingListPaths = workingListAL;
        ArrayList<Integer> numberOfSlashesArrayList = new ArrayList<Integer>();
        int numberOfSlashes = 0;
        int maxNumberOfSlashes = 0;
        int indexOfPathWithMaxNumberOfSlashes = 0;
        // int counterOfElementsThatAreNotZero = 0;
        // read through workingListcontentAL and count all the slashes in each word, add the number of slashes to a new arraylist 
        for (int j = 0; j < workingListPaths.size(); j++) {
            for (int k = 0; k < workingListPaths.get(j).length(); k++) {
                if (workingListPaths.get(j).charAt(k) == '/') {
                    numberOfSlashes++;
                }
            }
            numberOfSlashesArrayList.add(numberOfSlashes);
            numberOfSlashes = 0;
        }
        // determine the max number of slashes (not really useful tbh)
        for (int t = 0; t < numberOfSlashesArrayList.size(); t++) {
            if (maxNumberOfSlashes < numberOfSlashesArrayList.get(t)) {
                maxNumberOfSlashes = numberOfSlashesArrayList.get(t);
                indexOfPathWithMaxNumberOfSlashes = t;
            }
        }
        return numberOfSlashesArrayList;
    }

    //return number of most slashes by using arraylist from order() method 

    public static int getMaxSlashes() throws IOException {
        ArrayList<Integer> num = order();
        int maxValue = 0;
        for (int i = 0; i < num.size(); i++) {
            if (num.get(i) > maxValue) {
                maxValue = num.get(i);
            }
        }
        return maxValue;
    }

    // creates an array that holds each path that has the most slashes 
    // to-do: is row zero a possibility here, and should it be? 
    public static ArrayList<String> maxRows() throws IOException {
        ArrayList<String> maxSlashRows = new ArrayList<String>();

        for (int i = 0; i < order().size(); i++) {
            if (order().get(i) == getMaxSlashes()) {
                maxSlashRows.add(workingListAL.get(i));
            }
        }
        return maxSlashRows;
    }

    // given a string, it getParentPath() will return that string except everything that comes after the last \
    public static String getParentPath(String path) throws IOException {
        int lastSlash = path.lastIndexOf('/');
        if (lastSlash == -1) {
            return "";
        }
        return path.substring(0, lastSlash);
    }
    // returns a hash map in which the key is the parent folder and the value is all the files in that parent folder 
    public static HashMap<String, ArrayList<String>> groupByParentPath(ArrayList<String> rows) throws IOException {
        HashMap<String, ArrayList<String>> groups = new HashMap<>();
        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) { // rows array consists all paths with the max number of slashes
            String row = rows.get(rowIndex);
            int pathStartIndex = 46; // 46 bc sha1 hash is always 40 characters, blob is 4 characters, and there are 2 spaces
            String path = row.substring(pathStartIndex); // getting the path from the line that contains the hash 
            String parentPath = getParentPath(path); // uses getParentPath() to get only part of path that is after last \

            // adding parent folder to hash map if not already there 
            if (groups.containsKey(parentPath) == false) {
                groups.put(parentPath, new ArrayList<String>());
            }
            // adding any blobs that are in the parent folder to to the value part of the key-value pair where the key is the parent folder 
            groups.get(parentPath).add(row);
        }
        return groups;
    }
    // hashes all the lines that have the same parent folder 
    public static String createTreeHash(ArrayList<String> rows, String parentPath) throws IOException {
        String treeFileContent = "";
        for (int i = 0; i < rows.size(); i++) {
            String currentRow = rows.get(i);
            treeFileContent += currentRow + "\n";
        }
        String treeHash = Utils.SHA1(treeFileContent);
        Utils.makeFile("ProjectFolder/git/objects/" + treeHash, treeFileContent);

        return treeHash;

    }

    public static boolean buildTree() throws IOException {
        if (workingListAL.size() == 1) { // 
            return false;
        }
        if (maxRows().isEmpty()) {
            return false;
        }
        HashMap<String, ArrayList<String>> folderGroups = groupByParentPath(maxRows());

        ArrayList<String> newWorkingList = new ArrayList<>();
        for (int i = 0; i < workingListAL.size(); i++) {
            String currentRow = workingListAL.get(i);
            boolean isDeepRow = false;

            int deepestRowSize = maxRows().size(); // row with the most number of slashes 
            for (int j = 0; j < deepestRowSize; j++) {
                if (currentRow.equals(maxRows().get(j))) { // if the current row is the max row it is a row with the most of number of slashes aka it is a deep row 
                    isDeepRow = true;
                    j = deepestRowSize; // used to stop forloop 
                }
            }
            if (!isDeepRow) {
                newWorkingList.add(currentRow); // non deep rows added to working list bc they are not being used to make a tree
            }
        }

        ArrayList<String> parentFolders = new ArrayList<>(folderGroups.keySet()); // AL with all the keys from folderGroups as elements 
        int numGroups = parentFolders.size();
        for (int i = 0; i < numGroups; i++) {
            String parentFolder = parentFolders.get(i);
            ArrayList<String> filesInFolder = folderGroups.get(parentFolder); // gets all the values that go with the key value pair 
            String treeHash = createTreeHash(filesInFolder, parentFolder);
            String treeRow = "tree " + treeHash + " " + parentFolder;
            newWorkingList.add(treeRow); // creation of a tree file now added 
        }

        String directoryPath = System.getProperty("user.dir"); // gets root folder 
        //Utils.makeFile(directoryPath + "/workingFile.txt");
        String workingFileLOC = directoryPath + "/workingFile.txt";
        Utils.deleteFile(workingFileLOC);
        Utils.makeFile(workingFileLOC, ""); // makes a new blank working file 

        int newListSize = newWorkingList.size();
        for (int i = 0; i < newListSize; i++) {
            Utils.write(workingFileLOC, newWorkingList.get(i));
            if (i < newListSize - 1) {
                Utils.write(workingFileLOC, "\n"); // updating working File
            }
        }

        workingListAL = newWorkingList; // updating working list 
        return true;

    }

    public static void recursiveBuildTree() throws IOException {
        // has to call this top method (makeInitialWorkingLit) first before making tree using (recursiveBuildTreeHelper)
        makeInitialWorkingList();
        recursiveBuildTreeHelper();
    }

    public static void recursiveBuildTreeHelper() throws IOException {
        if (buildTree()) {
            recursiveBuildTreeHelper();
        }
    }

    public static void buildCommit(String author, String message) throws IOException {
        String treeHash = Utils.readFile("workingFile.txt").split(" ")[1];
        StringBuilder commitFileContents = new StringBuilder();
        commitFileContents.append("tree: " + treeHash);
        String headContents = Utils.readFile("HEAD");
        String parentHash = "";
        if (!headContents.equals("\n")) {
            parentHash = headContents.split(" ")[1];
            commitFileContents.append("\nparent: " + parentHash);
        }
        commitFileContents.append("\nauthor: " + author);
        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        commitFileContents.append("\ndate: " + timeStampMillis);
        commitFileContents.append("\nmessage: " + message);
        String commitHash = Utils.SHA1(commitFileContents.toString());
        Utils.makeFile("ProjectFolder/git/objects/" + commitHash, commitFileContents.toString());
        Utils.write("HEAD", commitHash);
    }
}





