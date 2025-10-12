import java.io.IOException;

public class GitTester {

    public static void main(String args[]) {

        clearFiles();

        GitWrapper gw = new GitWrapper();

        testInit(gw);
        testFileAddition(gw);
        testBuildCommit(gw);

        // gw.add("myProgram/hello.txt");
        // gw.add("myProgram/inner/world.txt");
        // gw.commit("John Doe", "Initial commit");
        // gw.checkout("1234567890");

    }

    public static void testBuildCommit(GitWrapper gw) {
        System.out.println("\n--TEST COMMIT--");
        try {
            String commitHash = gw.commit("author", "message");
            System.out.println("sha1 | " + commitHash);
            System.out.println("hash contents | " + Utils.readFile("ProjectFolder/git/objects/" + commitHash));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void testFileAddition(GitWrapper gw) {
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
            gw.add("testFile.txt");
            gw.add("testFile.txt");
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