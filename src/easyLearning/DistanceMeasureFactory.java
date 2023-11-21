package easyLearning;

import net.sf.javaml.distance.*;
import net.sf.javaml.distance.dtw.DTWSimilarity;
import net.sf.javaml.distance.fastdtw.Abstraction;
import net.sf.javaml.distance.fastdtw.Band;
import net.sf.javaml.distance.fastdtw.FastDTW;

public class DistanceMeasureFactory {

    public DistanceMeasure createDistanceMeasure(String distanceMeasureType) {
        switch (distanceMeasureType) {
            case "AngularDistance":
                return new AngularDistance();
            case "CachedDistance":
                return new CachedDistance();
            case "ChebychevDistance":
                return new ChebychevDistance();
            case "ConsistencyIndex":
                return new ConsistencyIndex();
            case "CosineDistance":
                return new CosineDistance();
            case "CosineSimilarity":
                return new CosineSimilarity();
            case "DistanceMeasure":
                return new DistanceMeasure();
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
            case "NormalizedEuclideanDistance":
                return new NormalizedEuclideanDistance();
            case "NormalizedEuclideanSimilarity":
                return new NormalizedEuclideanSimilarity();
            case "NormDistance":
                return new NormDistance();
            case "PearsonCorrelationCoefficient":
                return new PearsonCorrelationCoefficient();
            case "PolynomialKernel":
                return new PolynomialKernel();
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
            case "Abstraction":
                return new Abstraction();
            case "Band":
                return new Band();
            case "FastDTW":
                return new FastDTW();
            default:
                return new EuclideanDistance();
        }
    }
}
