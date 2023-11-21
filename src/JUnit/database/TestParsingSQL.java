package JUnit.database;

import com.holub.database.*;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TestParsingSQL {
    @Test
    public void testParsingSQL() {
        Database theDatabase = new Database();

        // Read a sequence of SQL statements in from the file
        // Database.test.sql and execute them.

        BufferedReader sql = null;
        try {
            sql = new BufferedReader(new FileReader("Database.test.sql"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String test;
        while (true) {
            try {
                if ((test = sql.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            test = test.trim();
            if (test.length() == 0) continue;

            while (test.endsWith("\\")) {
                test = test.substring(0, test.length() - 1);
                try {
                    test += sql.readLine().trim();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println("Parsing: " + test);
            Table result = null;
        }
    }
}
