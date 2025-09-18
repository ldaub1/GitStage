import java.io.*;
import java.io.File;

public class Utils {
    ///
    /// READS FILE FROM PATH, RETURNS STRING
    ///
    public static String readFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String content = "";

        // store all lines of target file
        while (reader.ready()) {
            content += reader.readLine() + "\n";
        }

        reader.close();
        return content;
    }

    ///
    /// MAKES FILE BASED ON PATH
    /// RETURNS FALSE IF ALREADY EXISTS
    ///
    public static boolean makeFile(String path) throws IOException {
        // create new file, if it doesn't already exist
        File newFile = new File(path);
        if (!newFile.exists())
            newFile.createNewFile();
        else
            return false;
        return true;
    }

    ///
    /// MAKES FILE BASED ON PATH, THEN WRITES CONTENT TO IT
    /// RETURNS FALSE IF ALREADY EXISTS
    ///
    public static boolean makeFile(String path, String content) throws IOException {
        // create new , if it doesn't already exist
        File newFile = new File(path);
        if (!newFile.exists())
            newFile.createNewFile();
        else
            return false;

        // write content into the new file
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(content);

        writer.close();
        return true;
    }

    ///
    /// WRITES CONTENT INTO FILE/PATH
    ///
    public static void write(String path, String content) throws IOException {
        // write all content into the file
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(content);

        writer.close();
    }

    ///
    /// MAKES DIRECTORY INTO BASED ON PATH
    /// RETURNS FALSE IF ALREADY EXISTS
    ///
    public static boolean makeDir(String path) throws IOException {
        // make a new directory, if it doesn't already exist
        File newDir = new File(path);
        if (!newDir.exists())
            newDir.mkdir();
        else
            return false;
        return true;
    }

    ///
    /// DELETES THE FILE AT THE GIVEN PATH
    /// RETURNS FALSE IF FILE NOT FOUND
    ///
    public static boolean delete(String path) throws IOException {
        File file = new File(path);
        if (!file.exists())
            return false;
        file.delete();
        return true;
    }
}