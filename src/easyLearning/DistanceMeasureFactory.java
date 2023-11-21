package easyLearning;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.distance.*;
import net.sf.javaml.distance.dtw.DTWSimilarity;
import net.sf.javaml.distance.fastdtw.Abstraction;
import net.sf.javaml.distance.fastdtw.Band;
import net.sf.javaml.distance.fastdtw.FastDTW;

public class DistanceMeasureFactory {
    private static DistanceMeasureFactory instance = null;
    private Dataset data = null;
    private int radius = -1;

    private DistanceMeasureFactory() {
    }

    public static DistanceMeasureFactory getInstance() {
        if (instance == null) {
            instance = new DistanceMeasureFactory();
        }
        return instance;
    }

    public DistanceMeasure createDistanceMeasure(String distanceMeasureType) {
        switch (distanceMeasureType) {
            case "AngularDistance":
                return new AngularDistance();
            case "ChebychevDistance":
            return new ChebychevDistance();
            case "CosineDistance":
                return new CosineDistance();
            case "CosineSimilarity":
                return new CosineSimilarity();
            case "EuclideanDistance":
                return new EuclideanDistance();
            case "JaccardIndexDistance":
                return new JaccardIndexDistance();
            case "JaccardIndexSimilarity":
                return new JaccardIndexSimilarity();
            case "LinearKernel":
                return new LinearKernel();
            case "MahalanobisDistance":
                return new MahalanobisDistance();
            case "ManhattanDistance":
                return new ManhattanDistance();
            case "MaxProductSimilarity":
                return new MaxProductSimilarity();
            case "MinkowskiDistance":
                return new MinkowskiDistance();
            case "NormDistance":
                return new NormDistance();
            case "PearsonCorrelationCoefficient":
                return new PearsonCorrelationCoefficient();
            case "RBFKernel":
                return new RBFKernel();
            case "RBFKernelDistance":
                return new RBFKernelDistance();
            case "SpearmanFootruleDistance":
                return new SpearmanFootruleDistance();
            case "SpearmanRankCorrelation":
                return new SpearmanRankCorrelation();
            case "DTWSimilarity":
                return new DTWSimilarity();
            case "NormalizedEuclideanDistance":
                if (this.data == null) {
                    System.out.println("Set data first!");
                    throw new NullPointerException();
                }
                return new NormalizedEuclideanDistance(this.data);
            case "CachedDistance":
                return new CachedDistance(new EuclideanDistance());
            case "NormalizedEuclideanSimilarity":
                if (this.data == null) {
                    System.out.println("Set data first!");
                    throw new NullPointerException();
                }
                return new NormalizedEuclideanSimilarity(this.data);
            case "Abstraction":
                if(this.radius == -1) {
                    System.out.println("Set radius first!");
                    throw new NullPointerException();
                }
                return new Abstraction(this.radius);
            case "Band":
                if(this.radius == -1) {
                    System.out.println("Set radius first!");
                    throw new NullPointerException();
                }
                return new Band(this.radius);
            case "FastDTW":
                if(this.radius == -1) {
                    System.out.println("Set radius first!");
                    throw new NullPointerException();
                }
                return new FastDTW(this.radius);
            default:
                return new EuclideanDistance();
        }
    }

    public void setData(Dataset data) {
        this.data = data;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
