import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

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
        BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
        writer.write(content);

        writer.close();
        return true;
    }

    ///
    /// WRITES CONTENT INTO FILE/PATH
    ///
    public static void write(String path, String content) throws IOException {
        // write all content into the file
        BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
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
    public static boolean deleteFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists())
            return false;
        file.delete();
        return true;
    }

    ///
    /// DELETES THE GIVEN FILE
    /// RETURNS FALSE IF FILE NOT FOUND
    ///
    public static boolean deleteFile(File file) throws IOException {
        if (!file.exists())
            return false;
        file.delete();
        return true;
    }

    ///
    /// DELETES ALL FILES IN AND INCLUDING GIVEN DIRECTORY AT PATH
    /// RETURNS FALSE IF DIRECTORY NOT FOUND
    ///
    public static boolean deleteDirectory(String path) throws IOException {
        File file = new File(path);
        if (!file.exists())
            return false;

        File[] allFiles = file.listFiles();
        for (File f : allFiles) {
            deleteDirectory(f);
        }
        return file.delete();
    }

    ///
    /// DELETES ALL FILES IN AND INCLUDING GIVEN FILE
    /// RETURNS FALSE IF FILE NOT FOUND
    ///
    public static boolean deleteDirectory(File file) throws IOException {
        File[] allFiles = file.listFiles();
        if (allFiles != null) {
            for (File f : allFiles) {
                deleteDirectory(f);
            }
        }
        return file.delete();
    }

    ///
    /// RETURNS THE SHA1 OF THE STRING
    ///
    public static String SHA1(String input) {
        try {
            // getInstance() method is called with algorithm SHA-1
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 40 digits long
            while (hashtext.length() < 40) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    ///
    /// COMPRESSES THE INPUT AND RETURNS IT
    ///
    public static String compress(String input) {
        Deflater deflater = new Deflater();
        deflater.setInput(input.getBytes());
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        while (!deflater.finished()) {
            int compressedSize = deflater.deflate(buffer);
            outputStream.write(buffer, 0, compressedSize);
        }

        return outputStream.toString();
    }

    ///
    /// DECOMPRESSES THE INPUT AND RETURNS IT
    ///
    public static String decompress(String input) throws DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        while (!inflater.finished()) {
            int decompressedSize = inflater.inflate(buffer);
            outputStream.write(buffer, 0, decompressedSize);
        }

        return outputStream.toString();
    }
}