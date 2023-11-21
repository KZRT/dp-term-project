package easyLearning;

import net.sf.javaml.clustering.*;
import net.sf.javaml.clustering.evaluation.*;
import net.sf.javaml.distance.DistanceMeasure;

public class ClustererFactory {
    private static ClustererFactory instance = null;
    private ClusterEvaluation ce = null;
    private DistanceMeasure dm = null;

    private ClustererFactory() {
        ce = new SumOfSquaredErrors();
    }

    public static ClustererFactory getInstance() {
        if (instance == null) {
            instance = new ClustererFactory();
        }
        return instance;
    }

    public Clusterer createClusterer(String clustererType) {
        switch (clustererType) {
            case "KMeans":
                return new KMeans();
            case "KMedoids":
                return new KMedoids();
            case "FarthestFirst":
                return new FarthestFirst();
            case "Cobweb":
                return new Cobweb();
            case "AQBC":
                return new AQBC();
            case "DensityBasedSpatial":
                return new DensityBasedSpatialClustering();
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

    private void setDistanceMeasure(DistanceMeasure dm) {
        this.dm = dm;
    }
}
