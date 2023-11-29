package JUnit.database;

import com.holub.database.Database;
import com.holub.database.Table;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TestDrop {
    @Test
    public void testDropColumn() {
        Database theDatabase = new Database();

        try {
            theDatabase.dropColumn("water_potability.csv", "Potability");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDropNAN() {
        Database theDatabase = new Database();

        try {
            theDatabase.dropNaN("water_potability.csv");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
