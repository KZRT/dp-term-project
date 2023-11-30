package easyLearning.model;

import net.sf.javaml.clustering.*;
import net.sf.javaml.clustering.evaluation.*;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.distance.*;

import java.util.ArrayList;
import java.util.Collections;

public class ClusteringFacade {
    private Clusterer clusterer = null;
    private ClustererFactory clustererFactory;
    private ClusterEvaluation clusterEvaluation = null;
    private DistanceMeasureFactory distanceMeasureFactory;
    private DistanceMeasure distanceMeasure = null;
    private Dataset dataset = null;
    private int clusterSize = 2;
    public ClusteringFacade() {
        clustererFactory = ClustererFactory.getInstance();
        distanceMeasureFactory = DistanceMeasureFactory.getInstance();
    }

    public void setClusterer(String clustererType) throws NullPointerException {
        this.clusterer = getEnumValue(EnumClustererFactory.class, clustererType).createClusterer(this.clusterSize, this.distanceMeasure);
    }

    public void setClusterEvaluation(String clusterEvaluationType) throws NullPointerException {
        this.clusterEvaluation = getEnumValue(EnumClusterEvaluationFactory.class, clusterEvaluationType).createClusterEvaluation(this.distanceMeasure);
    }

    public void setDistanceMeasure(String distanceMeasureType) throws NullPointerException {
        if(this.dataset != null) distanceMeasureFactory.setData(this.dataset);
        this.distanceMeasure = distanceMeasureFactory.createDistanceMeasure(distanceMeasureType);
    }

    public void setClusterSize(int clusters) {
        this.clusterSize = clusters;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public Dataset[] cluster(Dataset data) throws NullPointerException {
        if(this.clusterer == null) {
            throw new NullPointerException("Set clusterer first!");
        }
        System.out.println(this.clusterer);
        return this.clusterer.cluster(data);
    }

    public double score(Dataset[] clusters) throws NullPointerException {
        if(this.clusterEvaluation == null) {
            System.out.println("Set cluster evaluation first!");
            throw new NullPointerException();
        }
        return this.clusterEvaluation.score(clusters);
    }

    public double score(Dataset data) throws NullPointerException {
        if(this.clusterEvaluation == null) {
            throw new NullPointerException("Set cluster evaluation first!");
        }
        Dataset[] clusters = this.clusterer.cluster(data);
        return this.clusterEvaluation.score(clusters);
    }

    public Dataset[][] iterateCluster(Dataset data, int iterations) throws NullPointerException {
        if(this.clusterer == null) {
            throw new NullPointerException("Set clusterer first!");
        }
        Dataset[][] clusters = new Dataset[iterations][];
        for(int i = 0; i < iterations; i++) {
            clusters[i] = this.clusterer.cluster(data);
        }
        return clusters;
    }

    public double[] iterateScore(Dataset data, int iterations) throws NullPointerException {
        if(this.clusterEvaluation == null) {
            throw new NullPointerException("Set cluster evaluation first!");
        }
        Dataset[] clusters = this.clusterer.cluster(data);
        double[] scores = new double[iterations];
        for(int i = 0; i < iterations; i++) {
            scores[i] = this.clusterEvaluation.score(clusters);
        }
        return scores;
    }

    public double[] iterateScore(Dataset[][] clusters) throws NullPointerException {
        if(this.clusterEvaluation == null) {
            throw new NullPointerException("Set cluster evaluation first!");
        }
        double[] scores = new double[clusters.length];
        for(int i = 0; i < clusters.length; i++) {
            scores[i] = this.clusterEvaluation.score(clusters[i]);
        }
        return scores;
    }

    public double[] iterateScore(Dataset[][] clusters, int iterations) throws NullPointerException {
        if(this.clusterEvaluation == null) {
            throw new NullPointerException("Set cluster evaluation first!");
        }
        double[] scores = new double[iterations];
        for(int i = 0; i < clusters.length; i++) {
            scores[i] = this.clusterEvaluation.score(clusters[i]);
        }
        return scores;
    }

    public Dataset[][][] iterateKCluster(Dataset data, int iterations, int minClusters, int maxClusters) throws NullPointerException, IllegalArgumentException {
        if(minClusters < 1 || maxClusters < 1 || minClusters > maxClusters)
            throw new IllegalArgumentException("minClusters and maxClusters must be positive and minClusters <= maxClusters");
        if(this.clusterer == null) {
            throw new NullPointerException("Set clusterer first!");
        }
        Dataset[][][] clusters = new Dataset[maxClusters - minClusters + 1][iterations][];
        for(int i = minClusters; i < maxClusters + 1; i++) {
            this.clustererFactory.setClusterSize(i);
            for (int j = 0; j < iterations; j++) {
                clusters[i - minClusters][j] = this.clusterer.cluster(data);
            }
        }
        return clusters;
    }

    public double[][] iterateKClusterScore(Dataset data, int iterations, int minClusters,  int maxClusters) throws NullPointerException, IllegalArgumentException {
        if(minClusters < 1 || maxClusters < 1 || minClusters > maxClusters)
            throw new IllegalArgumentException("minClusters and maxClusters must be positive and minClusters <= maxClusters");
        if(this.clusterEvaluation == null) {
            throw new NullPointerException("Set cluster evaluation first!");
        }
        double[][] scores = new double[maxClusters - minClusters + 1][iterations];
        for(int i = minClusters; i < maxClusters + 1; i++) {
            this.clustererFactory.setClusterSize(i);
            for (int j = 0; j < iterations; j++) {
                Dataset[] clusters = this.clusterer.cluster(data);
                scores[i - minClusters][j] = this.clusterEvaluation.score(clusters);
            }
        }
        return scores;
    }

    public double[][] iterateKClusterScore(Dataset[][][] clusters) throws NullPointerException {
        if(this.clusterEvaluation == null) {
            throw new NullPointerException("Set cluster evaluation first!");
        }
        double[][] scores = new double[clusters.length][clusters[0].length];
        for(int i = 0; i < clusters.length; i++) {
            for(int j = 0; j < clusters[i].length; j++) {
                scores[i][j] = this.clusterEvaluation.score(clusters[i][j]);
            }
        }
        return scores;
    }

    public double[][] iterateKClusterScore(Dataset[][][] clusters, int iterations) throws NullPointerException {
        if(this.clusterEvaluation == null) {
            throw new NullPointerException("Set cluster evaluation first!");
        }
        double[][] scores = new double[clusters.length][iterations];
        for(int i = 0; i < clusters.length; i++) {
            for(int j = 0; j < iterations; j++) {
                scores[i][j] = this.clusterEvaluation.score(clusters[i][j]);
            }
        }
        return scores;
    }

    public double[][] iterateKClusterScore(Dataset[][][] clusters, int iterations, int minClusters, int maxClusters) throws NullPointerException, IllegalArgumentException {
        if(minClusters < 1 || maxClusters < 1 || minClusters > maxClusters)
            throw new IllegalArgumentException("minClusters and maxClusters must be positive and minClusters <= maxClusters");
        if(this.clusterEvaluation == null) {
            throw new NullPointerException("Set cluster evaluation first!");
        }
        double[][] scores = new double[maxClusters - minClusters + 1][iterations];
        for(int i = minClusters; i < maxClusters + 1; i++) {
            for(int j = 0; j < iterations; j++) {
                scores[i - minClusters][j] = this.clusterEvaluation.score(clusters[i - minClusters][j]);
            }
        }
        return scores;
    }

    public ClusteringResult getBestClustering(String method, String evaluation, String distanceMeasurement, Dataset data, int iterations) {

        ArrayList<ClusteringResult> results = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            this.dataset = data;
            this.setDistanceMeasure(distanceMeasurement);
            this.setClusterEvaluation(evaluation);
            this.setClusterer(method);
            System.out.println("Iteration " + i);
            Dataset[] clusters = this.cluster(data);
            double score = this.score(clusters);
            results.add(new ClusteringResult(this.clusterer.getClass().getSimpleName(), this.clusterEvaluation.getClass().getSimpleName(), clusters, score));
            System.out.println("Iteration " + i + "Score: " + score);
        }

        Collections.sort(results);
        System.out.println(results.get(0).toString());
        return results.get(0);
    }

    private <T extends Enum<T>> T getEnumValue(Class<T> type, String str) {
        return Enum.valueOf(type, str);
    }
}
