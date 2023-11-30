package easyLearning.model;

import net.sf.javaml.clustering.*;
import net.sf.javaml.clustering.mcl.MCL;
import net.sf.javaml.distance.DistanceMeasure;

public enum EnumClustererFactory {
    KMeans {
        @Override
        public Clusterer createClusterer(int clusterCount, DistanceMeasure dm) {
            return new KMeans(clusterCount, 300, dm);
        }
    },
    KMedoids {
        @Override
        public Clusterer createClusterer(int clusterCount, DistanceMeasure dm) {
            return new KMedoids(clusterCount, 300, dm);
        }
    },
    FarthestFirst {
        @Override
        public Clusterer createClusterer(int clusterCount, DistanceMeasure dm) {
            return new FarthestFirst(clusterCount, dm);
        }
    },
    Cobweb {
        @Override
        public Clusterer createClusterer(int clusterCount, DistanceMeasure dm) {
            return new Cobweb();
        }
    },
    AQBC {
        @Override
        public Clusterer createClusterer(int clusterCount, DistanceMeasure dm) {
            return new AQBC();
        }
    },
    DensityBasedSpatial {
        @Override
        public Clusterer createClusterer(int clusterCount, DistanceMeasure dm) {
            return new DensityBasedSpatialClustering();
        }
    },
    MCL {
        @Override
        public Clusterer createClusterer(int clusterCount, DistanceMeasure dm) {
            return new MCL(dm);
        }
    };

    public Clusterer create(int clusterCount, DistanceMeasure dm) {
        Clusterer clusterer = createClusterer(clusterCount, dm);
        return clusterer;
    }
    abstract protected Clusterer createClusterer(int clusterCount, DistanceMeasure dm);
}
