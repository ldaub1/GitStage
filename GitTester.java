import java.io.IOException;

public class GitTester {

    public static void main(String args[]) {

        clearFiles();

        GitWrapper gw = new GitWrapper();

        testInit(gw);
        testFileAddition(gw);
        testBuildCommit(gw);

    }

    public static void testBuildCommit(GitWrapper gw) {
        System.out.println("\n--TEST COMMIT--");
        try {
            System.out.println("[1st commit]");
            String commitHash = gw.commit("author", "message");
            System.out.println("sha1 | " + commitHash);
            System.out.println("hash contents | " + Utils.readFile("ProjectFolder/git/objects/" + commitHash));

            System.out.println("\n[2nd commit]");
            String commitHash2 = gw.commit("author", "message");
            System.out.println("sha1 | " + commitHash2);
            System.out.println("hash contents | " + Utils.readFile("ProjectFolder/git/objects/" + commitHash2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void testFileAddition(GitWrapper gw) {
        try {
            Utils.makeDir("ProjectFolder/testFiles");
            Utils.makeFile("ProjectFolder/testFiles/testFile.txt", "hello");
            Utils.makeFile("ProjectFolder/testFile.txt", "hello.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n--TEST FILE ADDITION--");
        try {
            System.out.println("[adding dir]");
            gw.add("testFiles");
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            System.out.println("\n[adding file that dne]");
            gw.add("testFileDNE.txt");
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            System.out.println("\n[adding file (base) + more than once]");
            gw.add("ProjectFolder/testFile.txt");
            gw.add("ProjectFolder/testFile.txt");
            System.out.println("index contents | " + Utils.readFile("ProjectFolder/git/index"));
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            System.out.println("\n[adding file in folder");
            gw.add("ProjectFolder/testFiles/testFile.txt");
            System.out.println("index contents | " + Utils.readFile("ProjectFolder/git/index"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void testInit(GitWrapper gw) {
        System.out.println("--TEST INIT--\n[test init]");
        gw.init();
        System.out.println("[already exists case]");
        gw.init();
    }

    public static void clearFiles() {
        try {
            Utils.deleteDirectory("ProjectFolder");
            System.out.println("[files cleared]\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}