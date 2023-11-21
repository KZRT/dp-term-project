package easyLearning.model;

import net.sf.javaml.clustering.*;
import net.sf.javaml.clustering.evaluation.*;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.distance.*;

import javax.xml.crypto.Data;

public class ClustingFacade {
    private Clusterer clusterer = null;
    private ClustererFactory clustererFactory;
    private ClusterEvaluation clusterEvaluation = null;
    private DistanceMeasureFactory distanceMeasureFactory;
    private DistanceMeasure distanceMeasure = null;
    private Dataset dataset = null;
    public ClustingFacade() {
        clustererFactory = ClustererFactory.getInstance();
        distanceMeasureFactory = DistanceMeasureFactory.getInstance();
    }

    public void setClusterer(String clustererType) throws NullPointerException {
        this.clusterer = clustererFactory.createClusterer(clustererType);
    }

    public void setClusterEvaluation(String clusterEvaluationType) throws NullPointerException {
        if(this.distanceMeasure != null) clustererFactory.setDistanceMeasure(this.distanceMeasure);
        this.clusterEvaluation = clustererFactory.createClusterEvaluation(clusterEvaluationType);
    }

    public void setDistanceMeasure(String distanceMeasureType) throws NullPointerException {
        if(this.dataset != null) distanceMeasureFactory.setData(this.dataset);
        this.distanceMeasure = distanceMeasureFactory.createDistanceMeasure(distanceMeasureType);
    }

    public void setClusterSize(int clusters) {
        this.clustererFactory.setClusterSize(clusters);
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public Dataset[] cluster(Dataset data) throws NullPointerException {
        if(this.clusterer == null) {
            throw new NullPointerException("Set clusterer first!");
        }
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
}
