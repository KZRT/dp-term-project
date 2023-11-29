package JUnit.database;

import com.holub.database.Database;
import org.junit.Test;

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
