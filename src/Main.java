import com.holub.database.Database;
import com.holub.database.JTableExporter;
import com.holub.database.Table;
import com.holub.database.TableFactory;
import com.holub.text.ParseFailure;
import com.holub.text.Scanner;
import com.holub.text.TokenSet;

import javax.swing.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        {
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
                    if (!((test = sql.readLine()) != null)) break;
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
                try {
                    result = theDatabase.execute(test);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ParseFailure e) {
                    throw new RuntimeException(e);
                }

                if (result != null)    // it was a SELECT of some sort
                    System.out.println(result.toString());
            }

            try {
                try {
                    theDatabase.execute("insert garbage SQL");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Database FAILED");
                System.exit(1);
            } catch (ParseFailure e) {
                System.out.println("Correctly found garbage SQL:\n" + e + "\n" + e.getErrorReport());
            }

            try {
                theDatabase.dump();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Database PASSED");
            System.exit(0);
        }
    }
}
