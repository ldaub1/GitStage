import java.io.IOException;

public class GitTester {

    public static void main(String args[]) {

        clearFiles();

        GitWrapper gw = new GitWrapper();

        gw.init();
        gw.init(); // already exists case

        testFileAddition(gw);

        // gw.add("myProgram/hello.txt");
        // gw.add("myProgram/inner/world.txt");
        // gw.commit("John Doe", "Initial commit");
        // gw.checkout("1234567890");

    }
    
    public static void testFileAddition(GitWrapper gw) {
        try {
            gw.add("testFiles");
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            gw.add("testFileDNE.txt");
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            gw.add("testFile.txt");
            gw.add("testFile.txt");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void clearFiles() {
        try {
            Utils.deleteDirectory("ProjectFolder");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}