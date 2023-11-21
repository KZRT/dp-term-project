package easyLearning;

import net.sf.javaml.distance.*;

public class DistanceMeasureFactory {

    public DistanceMeasure createDistanceMeasure(String distanceMeasureType) {
        switch (distanceMeasureType) {
            case "AngularDistance":
                return new AngularDistance();
            case "CachedDistance":
                return new CachedDistance();
            case "EuclideanDistance":
                return new EuclideanDistance();
            case "ManhattanDistance":
                return new ManhattanDistance();
            case "MinkowskiDistance":
                return new MinkowskiDistance();
            case "JaccardIndexDistance":
                return new JaccardIndexDistance();
            case "JaccardDistance":
            case "CosineSimilarity":
                return new CosineSimilarity();
            case "PearsonCorrelationCoefficient":
                return new PearsonCorrelationCoefficient();
            case "SpearmanFootruleDistance":
                return new SpearmanFootruleDistance();
            case "SpearmanRankCorrelation":
                return new SpearmanRankCorrelation();
            default:
                return new EuclideanDistance();
        }
    }
}
