import java.io.*;
import java.io.File;

public class Utils {
    public static String readFile(String name) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(name));
        String content = "";
        while (reader.ready()) {
            content += reader.readLine() + "\n";
        }
        reader.close();
        return content;
    }

    public static void makeFile(String name, String content, boolean hidden) throws IOException {
        if (hidden)
            File newFile = new File(name).createNewFile();
        else
            File newFile = new File("." + name).createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(name));
        writer.write(content);
    }
}