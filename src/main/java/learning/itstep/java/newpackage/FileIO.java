package learning.itstep.java.newpackage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class FileIO {

    private SimpleDateFormat dateFormatter
            = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss aaa");

    public void demo() {
        File current = new File("./");

        if (current.exists()) {
            System.out.println("Object exists.");
            System.out.println(current.getAbsolutePath());
        }
        if (current.isFile()) {
            System.out.println("Object is file.");
        }
        if (current.isDirectory()) {
            System.out.println("Object is directory:");
            File[] content = current.listFiles();
            for (File file : content) {
                System.out.print(dateFormatter.format(
                        new Date(file.lastModified()))
                        + " ");
                if (file.isDirectory()) {
                    System.out.print("<DIR>");
                } else {
                    System.out.print(file.length());
                }
                System.out.println(" " + file.getName());
            }
        }
        File sub = new File("./subdir");
        if (sub.exists()) {
            sub.delete();
            System.out.println("Sub deleted");
        } else {
            sub.mkdir();
            System.out.println("Sub created");
        }

        System.out.println("===========================================");
        try (FileWriter fw = new FileWriter("test.txt")) {
            fw.write("Line 1\nLine 2\nThe line 3.");
            fw.flush();
            System.out.println("File wrote.");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("-------------------------------------------");

        try (
                FileReader fr = new FileReader("test.txt"); Scanner scanner = new Scanner(fr)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
            System.out.println("FIle read.");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("-------------------------------------------");
        Map<String, String> config = new HashMap<>();
        try (InputStream inputStream = Objects.requireNonNull(this
                .getClass()
                .getClassLoader()
                .getResourceAsStream("db.ini")); Scanner scanner = new Scanner(inputStream)) {

            while (scanner.hasNextLine()) {
                String raw = scanner.nextLine();
                String noComment = raw.split("[#;]", 2)[0].trim();
                if (noComment.isEmpty()) continue;
                int eq = noComment.indexOf('=');
                if (eq < 0) continue;
                String key = noComment.substring(0, eq).trim();
                String value = noComment.substring(eq + 1).trim();
                if (!key.isEmpty()) config.put(key, value);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (NullPointerException ex) {
            System.err.println("Resource not found");
        }
        
        System.out.println("\n[CONFIG]");
        for (Map.Entry<String, String> entry : config.entrySet()) {
            System.out.printf(
                    "%s: %s\n",
                    entry.getKey(),
                    entry.getValue()
            );
        }
    }
}
