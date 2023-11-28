package easyLearning.model;

import net.sf.javaml.clustering.evaluation.*;
import net.sf.javaml.distance.DistanceMeasure;

public enum EnumClusterEvaluationFactory {
    AICScore {
        @Override
        protected ClusterEvaluation createClusterEvaluation(DistanceMeasure dm) {
            return new AICScore();
        }
    },
    BICScore {
        @Override
        protected ClusterEvaluation createClusterEvaluation(DistanceMeasure dm) {
            return new BICScore();
        }
    },
    HybridCentroidSimilarity {
        @Override
        protected ClusterEvaluation createClusterEvaluation(DistanceMeasure dm) {
            return new HybridCentroidSimilarity();
        }
    },
    HybridPairwiseSimilarities {
        @Override
        protected ClusterEvaluation createClusterEvaluation(DistanceMeasure dm) {
            return new HybridPairwiseSimilarities();
        }
    },
    SumOfAveragePairwiseSimilarities {
        @Override
        protected ClusterEvaluation createClusterEvaluation(DistanceMeasure dm) {
            return new SumOfAveragePairwiseSimilarities();
        }
    },
    SumOfCentroidSimilarities {
        @Override
        protected ClusterEvaluation createClusterEvaluation(DistanceMeasure dm) {
            return new SumOfCentroidSimilarities();
        }
    },
    SumOfSquaredErrors {
        @Override
        protected ClusterEvaluation createClusterEvaluation(DistanceMeasure dm) {
            return new SumOfSquaredErrors();
        }
    },
    TraceScatterMatrix {
        @Override
        protected ClusterEvaluation createClusterEvaluation(DistanceMeasure dm) {
            return new TraceScatterMatrix();
        }
    },
    CIndex {
        @Override
        protected ClusterEvaluation createClusterEvaluation(DistanceMeasure dm) {
            return new CIndex(dm);
        }
    },
    Gamma {
        @Override
        protected ClusterEvaluation createClusterEvaluation(DistanceMeasure dm) {
            return new Gamma(dm);
        }
    },
    GPlus {
        @Override
        protected ClusterEvaluation createClusterEvaluation(DistanceMeasure dm) {
            return new GPlus(dm);
        }
    },
    MinMaxCut {
        @Override
        protected ClusterEvaluation createClusterEvaluation(DistanceMeasure dm) {
            return new MinMaxCut(dm);
        }
    },
    PointBiserial {
        @Override
        protected ClusterEvaluation createClusterEvaluation(DistanceMeasure dm) {
            return new PointBiserial(dm);
        }
    },
    WB {
        @Override
        protected ClusterEvaluation createClusterEvaluation(DistanceMeasure dm) {
            return new WB(dm);
        }
    },
    Tau {
        @Override
        protected ClusterEvaluation createClusterEvaluation(DistanceMeasure dm) {
            return new Tau(dm);
        }
    };

    public ClusterEvaluation create(DistanceMeasure dm) {
        return createClusterEvaluation(dm);
    }

    abstract protected ClusterEvaluation createClusterEvaluation(DistanceMeasure dm);
}
