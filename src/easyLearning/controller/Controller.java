package easyLearning.controller;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public interface Controller {
    JTable importCSVToTable(File fileName) throws IOException;

    JTable dropColumn(String columnName) throws IOException;

    JTable dropNan() throws IOException;

    public void addMethod(String method);

    public void removeMethod(String method);

    public void addEvaluation(String evaluation);

    public void removeEvaluation(String evaluation);
}
