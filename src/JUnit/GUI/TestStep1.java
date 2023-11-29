package JUnit.GUI;

import easyLearning.model.GUI.userSelectFrame;
import org.junit.Test;


import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class TestStep1 {
    @Test
    public void testloadCSVIntoTable() {
//        userSelectFrame jUnitTestClass = new userSelectFrame();
//        try {
//            Method method = jUnitTestClass.getClass().getDeclaredMethod("loadCSVIntoTable", File.class);
//            method.setAccessible(true);
//            File ret = (File) method.invoke(jUnitTestClass, new File("C:\\train.csv"));
//            assertThat("PassengerID", is(ret));
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Error loading CSV into table: " + e.getMessage());
//        }
    }

    @Test
    public void testloadFIle(){
//        userSelectFrame jUnitTestClass = new userSelectFrame();
//        try {
//            Method method = jUnitTestClass.getClass().getDeclaredMethod("loadFile", String.class);
//            method.setAccessible(true);
//            File ret = (File) method.invoke(jUnitTestClass, new File("C:\\train.csv"));
//            assertEquals("train.csv", ret);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Error loading File: " + e.getMessage());
//        }
        userSelectFrame userSelectFrame = new userSelectFrame();
        try{
            userSelectFrame.loadFile("C:\\train.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

