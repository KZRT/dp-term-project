package easyLearning.model;

import net.sf.javaml.clustering.*;
import net.sf.javaml.clustering.evaluation.*;
import net.sf.javaml.clustering.mcl.MCL;
import net.sf.javaml.distance.DistanceMeasure;

public class ClustererFactory {
    private static ClustererFactory instance = null;
    private ClusterEvaluation ce = null;
    private DistanceMeasure dm = null;
    private int clusters = -1;

    private ClustererFactory() {
        ce = new SumOfSquaredErrors();
    }

    public static ClustererFactory getInstance() {
        if (instance == null) {
            instance = new ClustererFactory();
        }
        return instance;
    }

    public void setClusterSize(int clusters) {
        this.clusters = clusters;
    }

    public Clusterer createClusterer(String clustererType) throws NullPointerException {
        switch (clustererType) {
            case "KMeans":
                if(this.clusters == -1) {
                    System.out.println("Set cluster size first!");
                    throw new NullPointerException();
                }
                return new KMeans(this.clusters, 300);
            case "KMedoids":
                if(this.clusters == -1) {
                    System.out.println("Set cluster size first!");
                    throw new NullPointerException();
                } else if (this.dm == null) {
                    System.out.println("Set distance measure first!");
                    throw new NullPointerException();
                }
                return new KMedoids(this.clusters, 300, dm);
            case "FarthestFirst":
                if(this.clusters == -1) {
                    System.out.println("Set cluster size first!");
                    throw new NullPointerException();
                } else if (this.dm == null) {
                    System.out.println("Set distance measure first!");
                    throw new NullPointerException();
                }
                return new FarthestFirst(this.clusters, dm);
            case "Cobweb":
                return new Cobweb();
            case "AQBC":
                return new AQBC();
            case "DensityBasedSpatial":
                return new DensityBasedSpatialClustering();
            case "MCL":
                if (this.dm == null) {
                    System.out.println("Set distance measure first!");
                    throw new NullPointerException();
                }
                return new MCL(dm);
        }
        return null;
    }

    public ClusterEvaluation createClusterEvaluation(String clusterEvaluationType) throws NullPointerException {
        switch (clusterEvaluationType) {
            case "AICScore":
                this.ce = new AICScore();
                break;
            case "BICScore":
                this.ce = new BICScore();
                break;
            case "HybridCentroidSimilarity":
                this.ce = new HybridCentroidSimilarity();
                break;
            case "HybridPairwiseSimilarities":
                this.ce = new HybridPairwiseSimilarities();
                break;
            case "SumOfAveragePairwiseSimilarities":
                this.ce = new SumOfAveragePairwiseSimilarities();
                break;
            case "SumOfCentroidSimilarities":
                this.ce = new SumOfCentroidSimilarities();
                break;
            case "SumOfSquaredErrors":
                this.ce = new SumOfSquaredErrors();
                break;
            case "TraceScatterMatrix":
                this.ce = new TraceScatterMatrix();
                break;
            case "CIndex":
                if (this.dm == null) {
                    System.out.println("Set distance measure first!");
                    throw new NullPointerException();
                }
                this.ce = new CIndex(dm);
                break;
            case "Gamma":
                if (this.dm == null) {
                    System.out.println("Set distance measure first!");
                    throw new NullPointerException();
                }
                this.ce = new Gamma(dm);
                break;
            case "GPlus":
                if (this.dm == null) {
                    System.out.println("Set distance measure first!");
                    throw new NullPointerException();
                }
                this.ce = new GPlus(dm);
                break;
            case "MinMaxCut":
                if (this.dm == null) {
                    System.out.println("Set distance measure first!");
                    throw new NullPointerException();
                }
                this.ce = new MinMaxCut(dm);
                break;
            case "PointBiserial":
                if (this.dm == null) {
                    System.out.println("Set distance measure first!");
                    throw new NullPointerException();
                }
                this.ce = new PointBiserial(dm);
                break;
            case "WB":
                if (this.dm == null) {
                    System.out.println("Set distance measure first!");
                    throw new NullPointerException();
                }
                this.ce = new WB(dm);
                break;
            case "Tau":
                if (this.dm == null) {
                    System.out.println("Set distance measure first!");
                    throw new NullPointerException();
                }
                this.ce = new Tau(dm);
                break;
            default:
                this.ce = new SumOfSquaredErrors();
        }
        return this.ce;
    }

    public void setDistanceMeasure(DistanceMeasure dm) {
        this.dm = dm;
    }
}
