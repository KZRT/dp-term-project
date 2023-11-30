package easyLearning.controller;

import easyLearning.model.*;
import easyLearning.view.GUI.UserSelectFrame;
import com.holub.database.*;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.tools.data.FileHandler;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class UserSelectController implements Controller {
    private static UserSelectController userSelectController;
    private final ClusteringFacade model;
    private final UserSelectFrame view;
    private Database database;
    private final ArrayList<String> selectedMethods;
    private final ArrayList<String> selectedEvaluations;
    private String distanceMeasure;
    private int iterations;

    private File file;

    private UserSelectController(ClusteringFacade model, UserSelectFrame view) {

        this.model = new ClusteringFacade();
        System.out.println("UserSelectController " + this.model);
        this.view = view;
        database = new Database();
        this.iterations = 100;

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

    public void startClustering() {
        Dataset dataset;
        File targetFile = null;
        try {
            Table table = TableFactory.create(new KaggleCSVImporter(new FileReader(file)));
            System.out.println(table);
            FileWriter writer = new FileWriter("targetDataset.csv");
            DatasetExporter datasetExporter = new DatasetExporter(writer);
            table.export(datasetExporter);
            writer.close();
            targetFile = new File("targetDataset.csv");
            dataset = FileHandler.loadDataset(targetFile, 0, ",");
            System.out.println(dataset.toString());
        } catch (IOException e) {
            throw new NullPointerException("Dataset is null");
        }
        ArrayList<ClusteringResult> results = new ArrayList<>();
        System.out.println(distanceMeasure);
        model.setDistanceMeasure(distanceMeasure);
        for (String method : selectedMethods) {
            if (method.equals("KMeans") || method.equals("KMedoids") || method.equals("FarthestFirst")) {
                System.out.println(method);
                for (int clusterCount = 2; clusterCount < 10; clusterCount++) {
                    model.setClusterSize(clusterCount);
                    for (String evaluation : selectedEvaluations) {
                        System.out.println(evaluation);
                        model.setClusterEvaluation(evaluation);
                        model.setClusterer(method);
                        System.out.println("Start Clustering");
                        ClusteringResult clusteringResult = model.getBestClustering(dataset, iterations);
                        clusteringResult.setMethod(clusteringResult.getMethod() + "-" + clusterCount);
                        results.add(clusteringResult);
                    }
                }
            } else {
                for (String evaluation : selectedEvaluations) {
                    model.setClusterEvaluation(evaluation);
                    model.setClusterer(method);
                    ClusteringResult clusteringResult = model.getBestClustering(dataset, iterations);
                    results.add(clusteringResult);
                }
            }
        }
        for (ClusteringResult result : results) {
            System.out.println(result);
        }
    }

    private <T extends Enum<T>> T getEnumValue(Class<T> type, String str) {
        return Enum.valueOf(type, str);
    }

    public void setDistanceMeasure(String distanceMeasure) {
        this.distanceMeasure = distanceMeasure;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }
}
