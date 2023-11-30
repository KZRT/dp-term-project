package easyLearning.controller;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public interface Controller {
    JTable importCSVToTable(File fileName) throws IOException;
    JTable dropColumn(String columnName) throws IOException;

    JTable dropNan() throws IOException;
}
