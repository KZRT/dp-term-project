package easyLearning.model;

import net.sf.javaml.core.Dataset;

public class ClusteringResult implements Comparable<ClusteringResult>{
    private String method;
    private String evaluationMethod;
    private Dataset[] clusters;
    private double score;

    public ClusteringResult() {
        this.method = "";
        this.clusters = null;
        this.score = 0;
    }

    public ClusteringResult(String method, Dataset[] clusters) {
        this.method = method;
        this.clusters = clusters;
        this.score = 0;
    }

    public ClusteringResult(String method, Dataset[] clusters, double score) {
        this.method = method;
        this.clusters = clusters;
        this.score = score;
    }

    public ClusteringResult(String method, String evaluationMethod, Dataset[] clusters, double score) {
        this.method = method;
        this.evaluationMethod = evaluationMethod;
        this.clusters = clusters;
        this.score = score;
    }

    public String getMethod() {
        return method;
    }

    public Dataset[] getClusters() {
        return clusters;
    }

    public String getEvaluationMethod() {
        return evaluationMethod;
    }

    public double getScore() {
        return score;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setClusters(Dataset[] clusters) {
        this.clusters = clusters;
    }

    public void setEvaluationMethod(String evaluationMethod) {
        this.evaluationMethod = evaluationMethod;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String toString() {
        return "Method: " + this.method + "\n" +
                "Evaluation method: " + this.evaluationMethod + "\n" +
                "Score: " + this.score + "\n" +
                "Clusters: " + this.clusters;
    }

    @Override
    public int compareTo(ClusteringResult clusteringResult) {
        if(this.score > clusteringResult.getScore()) return 1;
        else if(this.score < clusteringResult.getScore()) return -1;
        else return 0;
    }
}
