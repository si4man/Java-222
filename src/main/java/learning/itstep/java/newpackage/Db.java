package learning.itstep.java.newpackage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.io.File;

public class Db {

    private Map<String, String> loadConfig(String iniFileName) throws Exception {
        Map<String, String> config = new HashMap<>();
        try(InputStream inputStream = Objects.requireNonNull(
                this
                    .getClass()
                    .getClassLoader()
                    .getResourceAsStream(iniFileName));
            Scanner scanner = new Scanner(inputStream)
        ) {
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                String uncommentedPart = parts[0];
                parts = uncommentedPart.split("=", 2);
                if(parts.length != 2) continue;
                config.put(parts[0].trim(), parts[1].trim());
            }
        }
        catch (IOException ex) {
            throw new Exception(ex);
        }
        catch (NullPointerException ex) {
            throw new Exception("Resource not found");
        }
        return config;
    }

    private Map<String, String> loadConfigs() throws Exception {
        Map<String, String> all = new HashMap<>();
        try {
            var cl = this.getClass().getClassLoader();
            var rootUrl = Objects.requireNonNull(cl.getResource(""));
            File root = new File(rootUrl.toURI());
            File[] iniFiles = root.listFiles((dir, name) -> name.toLowerCase().endsWith(".ini"));

            if (iniFiles == null || iniFiles.length == 0) {
                all.putAll(loadConfig("db.ini"));
                return all;
            }

            java.util.Arrays.sort(iniFiles, java.util.Comparator.comparing(File::getName));

            for (File f : iniFiles) {
                all.putAll(loadConfig(f.getName()));
            }
        } catch (Exception ex) {
            throw new Exception(ex);
        }
        return all;
    }

    public void demo() {
        Driver mysqlDriver;
        Connection connection;
        String connectionString;
        try {
            Map<String, String> config = loadConfigs();      

            connectionString = String.format(
                    "%s:%s://%s:%s/%s?user=%s&password=%s&%s",
                    Objects.requireNonNull(config.get("protocol"), "ini miss protocol"),
                    Objects.requireNonNull(config.get("dbms"), "ini miss dbms"),
                    Objects.requireNonNull(config.get("host"), "ini miss host"),
                    Objects.requireNonNull(config.get("port"), "ini miss port"),
                    Objects.requireNonNull(config.get("scheme"), "ini miss scheme"),
                    Objects.requireNonNull(config.get("user"), "ini miss user"),
                    Objects.requireNonNull(config.get("password"), "ini miss password"),
                    Objects.requireNonNull(config.get("params"), "ini miss params")
            );
            System.out.println(connectionString);
        } catch (Exception ex) {
            System.err.println("Config error: " + ex.getMessage());
            return;
        }

        try {
            mysqlDriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(mysqlDriver);
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException ex) {
            System.err.println("Start error: " + ex.getMessage());
            return;
        }

        System.out.println("Connection Ok");

        try {
            connection.close();
            DriverManager.deregisterDriver(mysqlDriver);
        } catch (SQLException ex) {
            System.err.println("Finish error: " + ex.getMessage());
        }
    }
}
