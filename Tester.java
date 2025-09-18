import java.io.*;

public class Tester {
    public static void main(String[] args) throws IOException {
        Git.initializeRepo("asdfasdf");
        Git.deleteRepo("asdfasdf");
    }
}
