package easyLearning.controller;

import com.holub.database.*;
import easyLearning.model.ClusteringFacade;
import easyLearning.model.ClusteringResult;
import easyLearning.view.GUI.UserSelectFrame;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class save implements Controller {
    private static save userSelectController;
    private final ClusteringFacade model;
    private final UserSelectFrame view;
    private Database database;
    private final ArrayList<String> selectedMethods;
    private final ArrayList<String> selectedEvaluations;
    private String distanceMeasure;
    private int iterations;
    private ArrayList<ClusteringResult> results;
    private ArrayList<String> columnNames;
    private Table table;

    private File file;

    private save(ClusteringFacade model, UserSelectFrame view) {

        this.model = new ClusteringFacade();
        System.out.println("UserSelectController " + this.model);
        this.view = view;
        database = new Database();
        this.iterations = 100;

        selectedMethods = new ArrayList<>();
        selectedEvaluations = new ArrayList<>();
    }

    public static save getInstance(ClusteringFacade model, UserSelectFrame view) {
        if (userSelectController == null) {
            userSelectController = new save(model, view);
        }
        return userSelectController;
    }

    public JTable importCSVToTable(File file) throws IOException {
        this.file = file;
        this.table = TableFactory.create(new KaggleCSVImporter(new FileReader(file)));
        JTableExporter jTableExporter = new JTableExporter();
        table.export(jTableExporter);
        return jTableExporter.getJTable();
    }

    public void exportTableToCSV(File file) throws IOException {
    }

    public JTable dropColumn(String columnName) throws IOException {
        File droppedFile = database.dropColumn(file, columnName);
        return importCSVToTable(droppedFile);
    }

    public JTable dropNan() throws IOException {
        File droppedFile = database.dropNaN(file);
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

    public void startClustering(String selectedColumnName) {
        Dataset dataset;
        File targetFile = new File("targetDataset.csv");
        System.out.println("Start Clustering at " + file.getAbsolutePath());
        int columnNum;
        try {
            columnNum = readColumnNames(selectedColumnName);
            DatasetExporterTwo datasetExporter = new DatasetExporterTwo(new FileWriter(targetFile));
            System.out.println("Start Exporting table: " + table.toString() + " to targetDataset.csv");
            table.export(datasetExporter);
            System.out.println("Exporting finished");
            dataset = FileHandler.loadDataset(targetFile, columnNum, ",");
            System.out.println(dataset.toString());
        } catch (IOException e) {
            throw new NullPointerException("Dataset is null");
        }
        ArrayList<ClusteringResult> clusteringResults = new ArrayList<>();
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
                        clusteringResults.add(clusteringResult);
                    }
                }
            } else {
                for (String evaluation : selectedEvaluations) {
                    model.setClusterEvaluation(evaluation);
                    model.setClusterer(method);
                    ClusteringResult clusteringResult = model.getBestClustering(dataset, iterations);
                    clusteringResults.add(clusteringResult);
                }
            }
        }
        for (ClusteringResult result : clusteringResults) {
            System.out.println(result);
        }
        this.results = clusteringResults;
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

    private int readColumnNames(String selectedColumnName) throws IOException {
        this.table.export(new KaggleCSVExporter(new FileWriter(file)));
        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);
        KaggleCSVImporter builder = new KaggleCSVImporter(br);
        this.columnNames = new ArrayList<>();
        int i = 0;
        int columnNum = 0;

        Iterator iterator = builder.loadColumnNames();
        while (iterator.hasNext()) {
            String columnName = iterator.next().toString();
            System.out.println(columnName);
            columnNames.add(columnName);
            if (columnName.equals(selectedColumnName)) {
                columnNum = i;
            }
            i++;
        }
        System.out.println(columnNames + " " + columnNum);
        return columnNum;
    }
}
