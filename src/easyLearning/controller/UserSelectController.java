package easyLearning.controller;

import easyLearning.model.ClusteringFacade;
import easyLearning.view.GUI.UserSelectFrame;
import com.holub.database.*;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class UserSelectController implements Controller {
    private static UserSelectController userSelectController;
    private final ClusteringFacade model;
    private final UserSelectFrame view;
    private Database database;
    private final ArrayList<String> selectedMethods;
    private final ArrayList<String> selectedEvaluations;
    private File file;
    private UserSelectController(ClusteringFacade model, UserSelectFrame view) {
        this.model = model;
        this.view = view;
        database = new Database();

        selectedMethods = new ArrayList<>();
        selectedEvaluations = new ArrayList<>();
    }

    public static UserSelectController getInstance(ClusteringFacade model, UserSelectFrame view) {
        if (userSelectController == null) {
            userSelectController = new UserSelectController(model, view);
        }
        return userSelectController;
    }

    public JTable importCSVToTable(File file) throws IOException {
        this.file = file;
        Table table = TableFactory.create(new KaggleCSVImporter(new FileReader(file)));
        System.out.println(table);
        JTableExporter jTableExporter = new JTableExporter();
        table.export(jTableExporter);
        return jTableExporter.getJTable();
    }

    public void exportTableToCSV(File file) throws IOException {
    }

    public JTable dropColumn(String columnName) throws IOException {
        File droppedFile = database.dropColumn(file, columnName);
        System.out.println(droppedFile);
        return importCSVToTable(droppedFile);
    }

    public JTable dropNan() throws IOException {
        File droppedFile = database.dropNaN(file);
        System.out.println(droppedFile);
        return importCSVToTable(droppedFile);
    }

    public void addMethod(String method) {
        selectedMethods.add(method);
    }

    public void removeMethod(String method) {
        selectedMethods.remove(method);
    }

    public void addEvaluation(String evaluation) {
        selectedEvaluations.add(evaluation);
    }

    public void removeEvaluation(String evaluation) {
        selectedEvaluations.remove(evaluation);
    }
}
