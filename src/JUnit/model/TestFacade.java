package JUnit.model;

import easyLearning.model.ClusteringFacade;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestFacade {
    @Test
    public void testSimpleScore() throws IOException {
        Dataset dataset = FileHandler.loadDataset(new File("devtools/data/test.lst"), ";");
        ClusteringFacade clusteringFacade = new ClusteringFacade();
        try{
            clusteringFacade.setDataset(dataset);
            clusteringFacade.setDistanceMeasure("MaxProductSimilarity");
            clusteringFacade.setClusterEvaluation("SumOfAveragePairwiseSimilarities");
            clusteringFacade.setClusterer("MCL");
        } catch (Exception e){
            e.printStackTrace();
        }
        double score = 0;
        try{
           score = clusteringFacade.score(dataset);
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(score);
    }

    @Test
    public void testKMeansWithIteration() throws IOException {
        Dataset dataset = FileHandler.loadDataset(new File("devtools/data/iris.data"), 4, ",");
        ClusteringFacade clusteringFacade = new ClusteringFacade();

        try{
            clusteringFacade.setDataset(dataset);
            clusteringFacade.setDistanceMeasure("EuclideanDistance");
            clusteringFacade.setClusterEvaluation("SumOfAveragePairwiseSimilarities");
            clusteringFacade.setClusterSize(3);
            clusteringFacade.setClusterer("KMeans");
        } catch (Exception e){
            e.printStackTrace();
        }

        Dataset[] clusters = null;
        try{
            clusters = clusteringFacade.cluster(dataset);
        } catch (Exception e){
            e.printStackTrace();
        }
        assert clusters != null;
        System.out.println(clusters.length);

        double score = 0;
        try{
            score = clusteringFacade.score(clusters);
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(score);

        double[] scores = new double[100];
        try {
            scores = clusteringFacade.iterateScore(dataset, 100);
        } catch (Exception e){
            e.printStackTrace();
        }

        for (double s : scores) {
            System.out.println(s);
        }

        double[][] scores2 = new double[8][100];
        try {
            scores2 = clusteringFacade.iterateKClusterScore(dataset, 100, 2, 9);
        } catch (Exception e){
            e.printStackTrace();
        }

        for (double[] s : scores2) {
            for (double ss : s) {
                System.out.print(ss + " ");
            }
            System.out.println();
        }
    }
}
