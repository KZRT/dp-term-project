package easyLearning.controller;

import easyLearning.model.ClusteringFacade;
import easyLearning.model.DistanceMeasureFactory;
import easyLearning.model.EnumClusterEvaluationFactory;
import easyLearning.model.EnumClustererFactory;
import easyLearning.view.GUI.UserSelectFrame;
import com.holub.database.*;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.tools.data.FileHandler;

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
    private String distanceMeasure;

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

    public void startClustering() throws IOException {
        Dataset[][] clusteringResults = new Dataset[selectedMethods.size()][];
        Dataset dataset = FileHandler.loadDataset(file, 0, ",");
        double[][] scores = new double[selectedMethods.size()][selectedEvaluations.size()];
        model.setDistanceMeasure(distanceMeasure.toString());
        for (String method : selectedMethods) {
            for (String evaluation : selectedEvaluations) {
                model.setClusterer(method);
                model.setClusterEvaluation(evaluation);
                clusteringResults[selectedMethods.indexOf(method)] = model.cluster(dataset);
                scores[selectedMethods.indexOf(method)][selectedEvaluations.indexOf(evaluation)] = model.score(clusteringResults[selectedMethods.indexOf(method)]);
            }
        }
        System.out.println("Scores: " + scores);
        for (int i = 0; i < scores.length; i++) {
            System.out.println("Method: " + selectedMethods.get(i));
            for (int j = 0; j < scores[i].length; j++) {
                System.out.println("Evaluation: " + selectedEvaluations.get(j));
                System.out.println("Score: " + scores[i][j]);
            }
        }
    }

    private <T extends Enum<T>> T getEnumValue(Class<T> type, String str) {
        return Enum.valueOf(type, str);
    }
}
