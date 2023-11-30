package easyLearning.controller;

import easyLearning.model.*;
import easyLearning.view.GUI.ResultFrame;
import easyLearning.view.GUI.ResultFrameTwo;
import easyLearning.view.GUI.UserSelectFrame;
import com.holub.database.*;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
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
    private DistanceMeasureFactory distanceMeasureFactory;

    private File file;

    private UserSelectController(ClusteringFacade model, UserSelectFrame view) {

        this.model = new ClusteringFacade();
        this.view = view;
        database = new Database();
        this.iterations = 100;

        selectedMethods = new ArrayList<>();
        selectedEvaluations = new ArrayList<>();
        this.distanceMeasure = "EuclideanDistance";
        distanceMeasureFactory = DistanceMeasureFactory.getInstance();
    }

    public static UserSelectController getInstance(ClusteringFacade model, UserSelectFrame view) {
        if (userSelectController == null) {
            userSelectController = new UserSelectController(model, view);
        }
        return userSelectController;
    }

    @Override
    public JTable importCSVToTable(File file) throws IOException {
        this.file = file;
        Table table = TableFactory.create(new KaggleCSVImporter(new FileReader(file)));
        System.out.println(table);
        JTableExporter jTableExporter = new JTableExporter();
        table.export(jTableExporter);
        return jTableExporter.getJTable();
    }

    @Override
    public JTable dropColumn(String columnName) throws IOException {
        File droppedFile = database.dropColumn(file, columnName);
        System.out.println(droppedFile);
        return importCSVToTable(droppedFile);
    }

    @Override
    public JTable dropNan() throws IOException {
        File droppedFile = database.dropNaN(file);
        System.out.println(droppedFile);
        return importCSVToTable(droppedFile);
    }

    @Override
    public void addMethod(String method) {
        selectedMethods.add(method);
    }

    @Override
    public void removeMethod(String method) {
        selectedMethods.remove(method);
    }

    @Override
    public void addEvaluation(String evaluation) {
        selectedEvaluations.add(evaluation);
    }

    @Override
    public void removeEvaluation(String evaluation) {
        selectedEvaluations.remove(evaluation);
    }

    @Override
    public void startClustering(String targetColumnName) {
        ArrayList<String> activeMethods = new ArrayList<>();
        Dataset dataset;
        File targetFile = null;
        int targetColumnIndex = 0;
        try {
            targetColumnIndex = database.getColumnIndex(file, targetColumnName);
            System.out.println(targetColumnIndex);
            Table table = TableFactory.create(new KaggleCSVImporter(new FileReader(file)));
            System.out.println(table);
            FileWriter writer = new FileWriter("targetDataset.csv");
            DatasetExporter datasetExporter = new DatasetExporter(writer);
            table.export(datasetExporter);
            writer.close();
            targetFile = new File("targetDataset.csv");
            dataset = FileHandler.loadDataset(targetFile, targetColumnIndex, ",");
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
                    activeMethods.add(method + "-" + clusterCount);
                    model.setClusterSize(clusterCount);
                    for (String evaluation : selectedEvaluations) {
                        System.out.println("Start Clustering");
                        ClusteringResult clusteringResult = model.getBestClustering(method, evaluation, distanceMeasure, dataset, iterations);
                        clusteringResult.setMethod(clusteringResult.getMethod() + "-" + clusterCount);
                        results.add(clusteringResult);
                    }
                }
            } else {
                activeMethods.add(method);
                for (String evaluation : selectedEvaluations) {
                    model.setClusterEvaluation(evaluation);
                    model.setClusterer(method);
                    ClusteringResult clusteringResult = model.getBestClustering(method, evaluation, distanceMeasure, dataset, iterations);
                    results.add(clusteringResult);
                }
            }
        }
//        for (ClusteringResult result : results) {
//            System.out.println(result);
//            ResultFrame frame = new ResultFrame();
//            frame.setClusteringMethod(result.getMethod());
//            frame.setEvaluationMethod(result.getEvaluationMethod());
//            frame.setScore(String.valueOf(result.getScore()));
//            frame.setClusterCount(String.valueOf(result.getClusters().length));
//        }

        // sort with method and evaluation with double[][] scores
        // show result in ResultFrame
        double[][] scores = new double[activeMethods.size()][selectedEvaluations.size()];

        for (int i = 0; i < activeMethods.size(); i++) {
            for (int j = 0; j < selectedEvaluations.size(); j++) {
                for (ClusteringResult result : results) {
                    if (Objects.equals(result.getMethod(), activeMethods.get(i)) && Objects.equals(result.getEvaluationMethod(), selectedEvaluations.get(j))) {
                        scores[i][j] = result.getScore();
                    }
                }
            }
        }

        String[] methods = new String[activeMethods.size()];
        String[] evaluations = new String[selectedEvaluations.size()];
        for (int i = 0; i < activeMethods.size(); i++) {
            methods[i] = activeMethods.get(i);
        }
        for (int i = 0; i < selectedEvaluations.size(); i++) {
            evaluations[i] = selectedEvaluations.get(i);
        }

        ResultFrameTwo frame = new ResultFrameTwo(methods, evaluations, scores);
    }

    @Override
    public void setDistanceMeasure(String distanceMeasure) {
        this.distanceMeasure = distanceMeasure;
    }

    @Override
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }
}
